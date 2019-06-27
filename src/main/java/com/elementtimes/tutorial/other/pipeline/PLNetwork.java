package com.elementtimes.tutorial.other.pipeline;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table;
import com.google.common.graph.EndpointPair;
import com.google.common.graph.GraphBuilder;
import com.google.common.graph.MutableGraph;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

public class PLNetwork {
    private MutableGraph<BlockPos> mGraph = GraphBuilder.undirected().build();
    private Map<BlockPos, PLInfo> mInfos = new HashMap<>();
    // row: in, column: out
    private Table<BlockPos, BlockPos, ImmutableList<BlockPos>> mPaths = HashBasedTable.create();
    private boolean mValid = true;
    private PLNetwork mNextNetwork = this;
    private int mDim;
    private final PLElementType mType;

    public PLNetwork(@Nonnull PLElementType type, int dim) {
        mDim = dim;
        mType = type;
    }

    /**
     * 获取某位置的管道信息
     * 如果所在位置不存在管道或管道不在该网络，返回 null
     * @param pos 管道位置
     * @return 管道信息
     */
    @Nullable
    public PLInfo get(@Nonnull BlockPos pos) {
        return mInfos.get(pos);
    }

    /**
     * 判断某节管道是否可以作为管道起点（元素入口）
     * @param pos 管道位置
     * @return 是否可以作为入口
     */
    public boolean isStartNode(@Nonnull BlockPos pos) {
        PLInfo plInfo = get(pos);
        return plInfo != null && plInfo.getType().in();
    }

    /**
     * 判断某节管道是否可以作为管道终点（元素出口）
     * @param pos 管道位置
     * @return 是否可以作为出口
     */
    public boolean isEndNode(@Nonnull BlockPos pos) {
        PLInfo plInfo = get(pos);
        return plInfo != null && plInfo.getType().out();
    }

    /**
     * TODO
     * 查询元素需要经过的管道路径
     * @param start 起始管道
     * @param end 终止管道
     * @return 最短路径
     */
    public ImmutableList<BlockPos> queryPath(@Nonnull BlockPos start, @Nonnull BlockPos end) {
        if (isStartNode(start) && isEndNode(end)) {
            return mPaths.get(start, end);
        }
        return ImmutableList.of();
    }

    /**
     * 当前网络是否有效
     * 当网络发生变动时（如使用管道连接两个网络），可能使网络无效
     * @return 是否有效
     */
    public boolean isValid() {
        return mValid;
    }

    /**
     * 获取有效管道
     * 当网络被替换（如两个网络合并）导致其中一个网络失效，使用此方法获取新的网络
     * 返回 null 则表示该网络被删除，并没有与其他网络合并
     * @return 有效管道
     */
    public PLNetwork getValidNetwork() {
        PLNetwork ptr = this;
        while (!ptr.isValid() && ptr != ptr.mNextNetwork) {
            ptr = ptr.mNextNetwork;
        }
        return ptr.isValid() ? ptr : null;
    }

    /**
     * TODO: 添加管道，检查网络
     * 为网络添加管道
     * @param pipeline 管道
     * @return 是否成功添加
     */
    public boolean add(World world, PLInfo pipeline) {
        BlockPos pos = pipeline.getPos();
        return false;
    }

    /**
     * TODO: 移除管道，检查网络
     * 从网络中删除管道
     * @param pipeline 管道
     */
    public void remove(PLInfo pipeline) {
        // TODO REMOVE
    }

    /**
     * 当前网络中是否为空
     * @return 是否没有管道
     */
    public boolean isEmpty() {
        return mGraph.nodes().isEmpty();
    }

    /**
     * 当前网络所在维度
     * @return 维度
     */
    public int getDim() {
        return mDim;
    }

    /**
     * 管道传输元素类型
     * @return 传输元素类型
     */
    public PLElementType getType() {
        return mType;
    }

    /**
     * 分裂网络
     * 当管道被删除时
     * @return 派生子网络
     */
    public PLNetwork[] childNet(World world) {
        if (isValid() && mGraph.nodes().size() > 1) {
            Set<BlockPos> unlinkNodes = new HashSet<>(mGraph.nodes());
            BlockPos pos = unlinkNodes.stream().findFirst().get();
            findAndRemove(unlinkNodes, pos);
            if (unlinkNodes.size() == 1) {
                PLNetwork net = new PLNetwork(mType, mDim);
                PLInfo plInfo = get(unlinkNodes.stream().findFirst().get());
                net.add(world, plInfo);
                return new PLNetwork[] {net};
            } else if (unlinkNodes.size() > 1) {

            }
        }
        return new PLNetwork[0];
    }

    private void findAndRemove(Set<BlockPos> all, BlockPos start) {
        all.remove(start);
        for (BlockPos node : mGraph.adjacentNodes(start)) {
            findAndRemove(all, node);
        }
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(mGraph, mInfos, mDim, mType);
    }
}
