package com.elementtimes.tutorial.other.pipeline;

import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

@SuppressWarnings({"UnstableApiUsage", "WeakerAccess"})
public class PLNetwork implements INBTSerializable<NBTTagCompound> {
    private static final String NBT_BIND_PIPELINE_NETWORK = "_pipeline_network_";
    private static final String NBT_BIND_PIPELINE_NETWORK_KEY = "_pipeline_network_key_";
    private static final String NBT_BIND_PIPELINE_NETWORK_DIM = "_pipeline_network_dim_";
    private static final String NBT_BIND_PIPELINE_NETWORK_TYPE = "_pipeline_network_type_";
    private static final String NBT_BIND_PIPELINE_NETWORK_OWNER = "_pipeline_network_owner_";
    private static final String NBT_BIND_PIPELINE_NETWORK_GRAPH_NODE_U = "_pipeline_network_graph_path_u_";
    private static final String NBT_BIND_PIPELINE_NETWORK_GRAPH_NODE_V = "_pipeline_network_graph_path_v_";

    private long mKey;
    private UUID mOwner;
    private MutableGraph<PLInfo> mGraph;
    private Set<PLPath> mPaths = new HashSet<>();
    private int mDim = 0;
    private PLType.PLElementType mType;

    public PLNetwork(long key, @Nullable UUID owner, @Nonnull PLType.PLElementType type, int dim) {
        mKey = key;
        mDim = dim;
        mType = type;
        mOwner = owner;
        mGraph = GraphBuilder.undirected().build();
    }

    public PLNetwork(long key, @Nullable UUID owner, PLNetwork network, MutableGraph<PLInfo> graph) {
        mKey = key;
        mDim = network.getDim();
        mType = network.getType();
        mOwner = owner;
        mGraph = graph;
    }

    /**
     * 通过 NBT 创建网络
     * 在初始化时使用，因此如果存在相同网络 key，会跳过创建。
     * @param nbt NBT
     * @return 网络
     */
    public static PLNetwork fromNbt(NBTTagCompound nbt) {
        NBTTagCompound nbtNetwork = nbt.getCompoundTag(NBT_BIND_PIPELINE_NETWORK);
        long key = nbtNetwork.getLong(NBT_BIND_PIPELINE_NETWORK_KEY);
        PLNetwork network = PLNetworkManager.getNetwork(key)
                .orElse(new PLNetwork(0, null, PLType.PLElementType.Item, 0));
        network.deserializeNBT(nbt);
        return network;
    }

    public long getKey() {
        return mKey;
    }

    /**
     * 查询元素需要经过的管道路径
     * @param start 起始管道
     * @param end 终止管道
     * @return 最短路径，若无法达到或没有缓存当前路径则返回 null
     */
    @Nullable
    public PLPath queryPath(@Nonnull PLInfo start, @Nonnull PLInfo end) {
        PLPath path = queryPathNotCalc(start, end);
        if (path == null) {
            calcPath(start);
        }
        return queryPathNotCalc(start, end);
    }

    @Nullable
    public PLPath queryPath(@Nonnull BlockPos start, @Nonnull BlockPos end) {
        Set<PLInfo> nodes = mGraph.nodes();
        PLInfo plStart = null;
        PLInfo plEnd = null;
        for (PLInfo node : nodes) {
            if (plStart == null && node.getPos().equals(start)) {
                plStart = node;
            }

            if (plEnd == null && node.getPos().equals(end)) {
                plEnd = node;
            }

            if (plStart != null && plEnd != null) {
                break;
            }
        }
        if (plStart != null && plEnd != null) {
            return queryPath(plStart, plEnd);
        }
        return null;
    }

    private PLPath queryPathNotCalc(@Nonnull PLInfo start, @Nonnull PLInfo end) {
        return getPaths().stream()
                .filter(p -> p.getStart().equals(start) && p.getEnd().equals(end))
                .findFirst()
                .orElse(null);
    }

    /**
     * 为网络添加管道
     * @param world 世界
     * @param pipeline 管道
     * @param linkedLines 连接网络，可为 null
     */
    public void add(World world, PLInfo pipeline, @Nullable Collection<PLInfo> linkedLines) {
        pipeline.setNetwork(this);
        Set<PLInfo> nodes = mGraph.nodes();
        if (!nodes.contains(pipeline)) {
            mGraph.addNode(pipeline);
        }
        if (linkedLines != null) {
            for (PLInfo plInfo : linkedLines) {
                link(world, pipeline, plInfo);
            }
        }
    }

    /**
     * 设置两个管道联通
     * @param p1 管道1
     * @param p2 管道2
     */
    public void link(World world, PLInfo p1, PLInfo p2) {
        if (this != p1.getNetwork()) {
            PLNetworkManager.merge(world, this, p1.getNetwork());
        }
        if (this != p2.getNetwork()) {
            PLNetworkManager.merge(world, this, p2.getNetwork());
        }
        mGraph.putEdge(p1, p2);
    }

    /**
     * 从网络中删除管道，删除管道后，网络可能不变，可能分裂为多个网络，也可能消失
     * TODO: 优化：删除管道后，更新网络策略
     * @param pipeline 管道
     */
    public void remove(World world, PLInfo pipeline) {
        removeNotRebuild(world, pipeline);
        rebuild(world);
    }

    public void removeNotRebuild(World world, PLInfo pipeline) {
        Set<PLInfo> nodes = mGraph.nodes();
        if (nodes.contains(pipeline)) {
            if (nodes.size() == 1) {
                PLNetworkManager.removeNetwork(world, this);
                return;
            } else {
                Set<PLInfo> adjacentNodes = mGraph.adjacentNodes(pipeline.getPos());
                for (PLInfo node : adjacentNodes) {
                    mGraph.removeEdge(pipeline, node);
                }
                mGraph.removeNode(pipeline);
            }
            pipeline.remove(world);
        }
    }

    /**
     * 当前网络所在维度
     * @return 维度
     */
    public int getDim() {
        return mDim;
    }

    /**
     * 获取所有路径
     * @return 缓存的最短路径
     */
    @Nonnull
    public Set<PLPath> getPaths() {
        return mPaths;
    }

    /**
     * 管道传输元素类型
     * @return 传输元素类型
     */
    @Nonnull
    public PLType.PLElementType getType() {
        return mType;
    }

    public MutableGraph<PLInfo> getGraph() {
        return mGraph;
    }

    /**
     * 重新计算所有最短路径
     * TODO: 可优化：当管道变动时，判断是否只需要部分更新
     */
    public void calcPath(PLInfo start) {
        Set<PLInfo> nodes = mGraph.nodes();
        mPaths = new HashSet<>(nodes.size());
        long mark = System.currentTimeMillis();
        List<PLInfo> findNodes = new ArrayList<>(nodes);
        findNodes.remove(start);
        for (int i = 0; i < findNodes.size(); i++) {
            PLPath nearestPath = getNearestPath(findNodes, start, mark);
            if (nearestPath != null) {
                updatePath(nearestPath, mark, start);
            }
        }

    }

    @Nullable
    private PLPath getNearestPath(List<PLInfo> findNodes, PLInfo start, long mark) {
        long tick = Long.MAX_VALUE;
        PLPath select = null;
        for (PLInfo temp : findNodes) {
            PLPath path = queryPathNotCalc(start, temp);
            if (path == null) {
                for (PLInfo adjNode : mGraph.adjacentNodes(temp)) {
                    PLPath adjPath = queryPathNotCalc(start, adjNode);
                    if (adjPath != null) {
                        PLPath rPath = new PLPath(adjPath.getPaths(), temp);
                        if (rPath.getTotalTick() < tick) {
                            tick = rPath.getTotalTick();
                            select = rPath;
                        }
                    }
                }
            } else if (path.findFlag != mark) {
                if (path.getTotalTick() < tick) {
                    tick = path.getTotalTick();
                    select = path;
                }
            }
        }
        return select;
    }

    private void updatePath(PLPath nearestPath, long mark, PLInfo start) {
        nearestPath.findFlag = mark;
        mPaths.add(nearestPath);

        PLInfo endNode = nearestPath.getEnd();
        for (PLInfo node : mGraph.adjacentNodes(endNode)) {
            PLPath path = new PLPath(nearestPath, node);
            PLPath plPath = queryPath(start, node);
            if (plPath == null) {
                mPaths.add(path);
            } else {
                plPath.update(path);
            }
        }
    }

    /**
     * 重建网络
     * 当网络增加管道时，使用该方法生成新的网络，进行网络分裂操作
     * TODO: 可优化：当删除管道时，判断是否真正需要分裂网络，不需要的话可以继续使用原网络
     * @return 新网络
     */
    public List<PLNetwork> rebuild(World world) {
        Set<PLInfo> nodes = mGraph.nodes();
        List<PLInfo> nodeCheck = new ArrayList<>(nodes);
        List<PLNetwork> networks;
        if (nodeCheck.isEmpty()) {
            networks = Collections.emptyList();
        } else {
            networks = new ArrayList<>(1);
            while (!nodeCheck.isEmpty()) {
                networks.add(getSubNetwork(nodeCheck.get(0), nodeCheck));
            }
        }
        PLNetworkManager.removeNetwork(world, this);
        PLNetworkManager.addNetworks(world, networks);
        return networks;
    }

    private PLNetwork getSubNetwork(PLInfo node, List<PLInfo> check) {
        MutableGraph<PLInfo> subGraph = GraphBuilder.undirected().build();
        PLNetwork subNetwork = new PLNetwork(mKey, mOwner, this, subGraph);
        List<PLInfo[]> edges = new LinkedList<>();
        resetAdjNode(subNetwork, node, check, edges);
        for (PLInfo[] edge : edges) {
            subGraph.putEdge(edge[0], edge[1]);
        }
        return subNetwork;
    }

    private void resetAdjNode(PLNetwork network, PLInfo node, List<PLInfo> removeFrom, List<PLInfo[]> edgeCollect) {
        node.setNetwork(network);
        removeFrom.remove(node);
        for (PLInfo adjacentNode : mGraph.adjacentNodes(node)) {
            edgeCollect.add(new PLInfo[]{node, adjacentNode});
            resetAdjNode(network, adjacentNode, removeFrom, edgeCollect);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PLNetwork
                && ((PLNetwork) obj).mDim == mDim
                && ((PLNetwork) obj).mKey == mKey
                && ((PLNetwork) obj).mType == mType
                && ((PLNetwork) obj).mOwner.equals(mOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mDim, mKey, mType, mOwner);
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtNetwork = new NBTTagCompound();

        nbtNetwork.setLong(NBT_BIND_PIPELINE_NETWORK_KEY, mKey);
        nbtNetwork.setInteger(NBT_BIND_PIPELINE_NETWORK_DIM, mDim);
        nbtNetwork.setInteger(NBT_BIND_PIPELINE_NETWORK_TYPE, mType.toInt());
        Set<EndpointPair<PLInfo>> edges = mGraph.edges();
        NBTTagList listU = new NBTTagList();
        NBTTagList listV = new NBTTagList();
        for (EndpointPair<PLInfo> edge : edges) {
            listU.appendTag(edge.nodeU().serializeNBT());
            listV.appendTag(edge.nodeV().serializeNBT());
        }
        nbtNetwork.setTag(NBT_BIND_PIPELINE_NETWORK_GRAPH_NODE_U, listU);
        nbtNetwork.setTag(NBT_BIND_PIPELINE_NETWORK_GRAPH_NODE_V, listV);
        if (mOwner != null) {
            nbtNetwork.setUniqueId(NBT_BIND_PIPELINE_NETWORK_OWNER, mOwner);
        }

        nbt.setTag(NBT_BIND_PIPELINE_NETWORK, nbtNetwork);
        return nbt;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtNetwork = nbt.getCompoundTag(NBT_BIND_PIPELINE_NETWORK);

        mKey = nbtNetwork.getLong(NBT_BIND_PIPELINE_NETWORK_KEY);
        mDim = nbtNetwork.getInteger(NBT_BIND_PIPELINE_NETWORK_DIM);
        int type = nbtNetwork.getInteger(NBT_BIND_PIPELINE_NETWORK_TYPE);
        mType = PLType.PLElementType.fromInt(type);
        if (nbtNetwork.hasKey(NBT_BIND_PIPELINE_NETWORK_OWNER)) {
            mOwner = nbtNetwork.getUniqueId(NBT_BIND_PIPELINE_NETWORK_OWNER);
        }
        NBTTagList listU = (NBTTagList) nbtNetwork.getTag(NBT_BIND_PIPELINE_NETWORK_GRAPH_NODE_U);
        NBTTagList listV = (NBTTagList) nbtNetwork.getTag(NBT_BIND_PIPELINE_NETWORK_GRAPH_NODE_V);
        int pathCount = Math.min(listU.tagCount(), listV.tagCount());
        for (int i = 0; i < pathCount; i++) {
            PLInfo nodeU = new PLInfo();
            nodeU.deserializeNBT(listU.getCompoundTagAt(i));
            PLInfo nodeV = new PLInfo();
            nodeV.deserializeNBT(listV.getCompoundTagAt(i));

            mGraph.addNode(nodeU);
            mGraph.addNode(nodeV);
            mGraph.putEdge(nodeU, nodeV);
        }
    }
}
