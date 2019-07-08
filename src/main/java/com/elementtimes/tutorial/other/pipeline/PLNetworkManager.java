package com.elementtimes.tutorial.other.pipeline;

import com.elementtimes.tutorial.common.storage.PLNetworkStorage;
import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import com.google.common.graph.EndpointPair;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

/**
 * 管道网络管理
 * @author luqin2007
 */
public class PLNetworkManager {

    private static final String NBT_BIND_PIPELINE_NETWORK_MANAGER = "_pipeline_pl_network_manager_";
    private static final String NBT_BIND_PIPELINE_NETWORK_MANAGER_NETS = "_pipeline_pl_network_manager_network_";

    private static Set<PLNetwork> sNetworks = new HashSet<>();

    /**
     * 向世界添加一个管道
     * @param world 所在世界
     * @param pipeline 管道信息
     */
    public static void addPipeline(@Nullable EntityLivingBase placer, @Nonnull World world, @Nonnull PLInfo pipeline) {
        PLNetwork network = pipeline.getNetwork();
        TileEntity tileEntity = world.getTileEntity(pipeline.getPos());
        if (tileEntity instanceof TilePipeline) {
            TilePipeline tp = (TilePipeline) tileEntity;
            if (network == null) {
                network = findRoundNetwork(world, tp, pipeline);
                if (network == null) {
                    network = createNetwork(placer, world, pipeline.getType().type());
                    sNetworks.add(network);
                }
            }
            List<PLInfo> linked = new ArrayList<>(6);
            for (EnumFacing facing : EnumFacing.values()) {
                if (tp.isConnected(facing)) {
                    BlockPos offset = pipeline.getPos().offset(facing);
                    TileEntity teLink = world.getTileEntity(offset);
                    if (teLink instanceof TilePipeline) {
                        linked.add(((TilePipeline) teLink).getInfo());
                    }
                }
            }
            network.add(world, pipeline, linked);
            PLNetworkStorage.load(world).markDirty();
        }
    }

    private static PLNetwork findRoundNetwork(World world, TilePipeline tp, PLInfo pipeline) {
        for (EnumFacing facing : EnumFacing.values()) {
            if (tp.isConnected(facing)) {
                BlockPos offset = pipeline.getPos().offset(facing);
                TileEntity tileEntity1 = world.getTileEntity(offset);
                if (tileEntity1 instanceof TilePipeline) {
                    PLInfo info = ((TilePipeline) tileEntity1).getInfo();
                    return info.getNetwork();
                }
            }
        }
        return null;
    }

    private static PLNetwork createNetwork(EntityLivingBase placer, World world, PLType.PLElementType type) {
        UUID owner;
        if (placer != null) {
            owner = placer.getUniqueID();
        } else {
            owner = null;
        }
        long key = -1;
        boolean v = false;
        while (!v) {
            key = (owner == null ? 0 : owner.hashCode()) + world.rand.nextLong();
            v = true;
            for (PLNetwork n : sNetworks) {
                if (key == n.getKey()) {
                    v = false;
                    break;
                }
            }
        }
        return new PLNetwork(key, owner, type, world.provider.getDimension());
    }

    /**
     * 向网络系统添加一个管道网络
     * @param world 所在世界
     * @param networks 所在网络
     */
    public static void addNetworks(World world, Collection<PLNetwork> networks) {
        sNetworks.addAll(networks);
        PLNetworkStorage.load(world).markDirty();
    }

    /**
     * 移除网络
     * @param world 世界
     * @param network 网络
     */
    public static void removeNetwork(World world, PLNetwork network) {
        sNetworks.remove(network);
        network.getGraph().nodes().stream()
                .filter(plInfo -> plInfo.getNetwork() == network)
                .forEach(plInfo -> {
                    plInfo.getNetwork().remove(world, plInfo);
                    plInfo.setNetwork(null);
                });
        PLNetworkStorage.load(world).markDirty();
    }

    /**
     * 将当前网络添加到另一个网络中
     * 前请确保合并后向图中添加一条连接两个网络的通路，否则世界保存时只会保存一个网络
     * @param stay 合并后保留的网络
     * @param removes 合并后移除的网络
     * @param world 世界
     */
    public static void merge(World world, PLNetwork stay, PLNetwork... removes) {
        for (PLNetwork remove : removes) {
            for (PLInfo node : remove.getGraph().nodes()) {
                node.setNetwork(stay);
                stay.getGraph().addNode(node);
            }
            for (EndpointPair<PLInfo> edge: remove.getGraph().edges()) {
                stay.getGraph().putEdge(edge.nodeU(), edge.nodeV());
            }
            PLNetworkManager.removeNetwork(world, remove);
        }
    }

    /**
     * 查找网络
     * @param key 网络 id
     * @return 网络
     */
    public static Optional<PLNetwork> getNetwork(long key) {
        return sNetworks.stream()
                .filter(n -> key == n.getKey())
                .findFirst();
    }

    public static NBTTagCompound writeToNbt(NBTTagCompound nbt) {
        NBTTagCompound nbtNet = new NBTTagCompound();

        NBTTagList nbtList = new NBTTagList();
        for (PLNetwork network : sNetworks) {
            nbtList.appendTag(network.serializeNBT());
        }
        nbtNet.setTag(NBT_BIND_PIPELINE_NETWORK_MANAGER_NETS, nbtList);

        nbt.setTag(NBT_BIND_PIPELINE_NETWORK_MANAGER, nbtNet);
        return nbt;
    }

    public static NBTTagCompound serializeNbt() {
        return writeToNbt(new NBTTagCompound());
    }

    public static void deserializeNbt(NBTTagCompound nbt) {
        sNetworks.clear();

        NBTTagCompound nbtNet = nbt.getCompoundTag(NBT_BIND_PIPELINE_NETWORK_MANAGER);
        NBTTagList networks = (NBTTagList) nbtNet.getTag(NBT_BIND_PIPELINE_NETWORK_MANAGER_NETS);
        for (NBTBase network : networks) {
            sNetworks.add(PLNetwork.fromNbt((NBTTagCompound) network));
        }

        PLNetworkStorage.load(FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(0)).markDirty();
    }
}
