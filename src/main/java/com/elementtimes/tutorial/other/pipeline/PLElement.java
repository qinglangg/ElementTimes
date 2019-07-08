package com.elementtimes.tutorial.other.pipeline;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.INBTSerializable;

/**
 * 管道传输内容信息
 * @author luqin2007
 */
@SuppressWarnings("unused")
public class PLElement implements INBTSerializable<NBTTagCompound> {

    private static final String NBT_BIND_PIPELINE_ELEMENT = "_pipeline_element_";
    private static final String NBT_BIND_PIPELINE_ELEMENT_ELEMENT = "_pipeline_element_element_";
    private static final String NBT_BIND_PIPELINE_ELEMENT_START = "_pipeline_element_start_";
    private static final String NBT_BIND_PIPELINE_ELEMENT_END = "_pipeline_element_end_";
    private static final String NBT_BIND_PIPELINE_ELEMENT_NETWORK = "_pipeline_element_network_";
    private static final String NBT_BIND_PIPELINE_ELEMENT_TICK = "_pipeline_element_tick_";

    private NBTBase mElement;
    private PLPath mPath;
    private long mTick = 0;
    private long mTotalTick = 0;
    private PLNetwork mNetwork;
    private PLInfo mPipeline;

    /**
     * 创建一个 NBT 网络物品
     * @param from 传输起点
     * @param to 传输终点
     * @param element 传输数据
     */
    public PLElement(PLInfo from, PLInfo to, NBTBase element) {
        if (from.getNetwork() != to.getNetwork()) {
            throw new RuntimeException("Two pipelines are not in the same network");
        }
        mElement = element;
        mNetwork = from.getNetwork();
        mPath = mNetwork.queryPath(from, to);
        mPipeline = from;
    }

    public PLElement(NBTTagCompound nbt) {
        deserializeNBT(nbt);
    }

    /**
     * 计算下一 tick
     */
    public void nextTick() {
        if (mTick < mPipeline.getKeepTick()) {
            mTick++;
            mTotalTick++;
        } else if (mPipeline != mPath.getEnd()) {
            mPipeline = mPath.getNextPipeline(mPipeline);
            mTick = 0;
            mTotalTick++;
        }
    }

    /**
     * 获取 NBT 元素在当前路径中停留的 tick 数
     * @return 停留时间
     */
    public long getTick() {
        return mTick;
    }

    /**
     * 获取 NBT 元素在网络中存在的总 tick 数
     * @return 停留时间
     */
    public long getTotalTick() {
        return mTotalTick;
    }

    /**
     * 获取 NBT 元素停留的当前路径
     * @return 停留管道
     */
    public PLInfo getPipeline() {
        return mPipeline;
    }

    /**
     * 获取完整路径
     * @return 路径
     */
    public PLPath getPath() {
        return mPath;
    }

    /**
     * 获取传输的元素
     * @return 传输元素
     */
    public NBTBase getElement() {
        return mElement;
    }

    /**
     * 获取 NBT 元素所在网络
     * @return 网络
     */
    public PLNetwork getNetwork() {
        return mNetwork;
    }

    /**
     * 更新 NBT 元素在网络中停留的总时间
     * @param totalTick 总时间
     */
    public void setTotalTick(long totalTick) {
        if (totalTick != mTotalTick) {
            long maxTick = mPath.getTotalTick();
            if (totalTick >= maxTick) {
                mTotalTick = maxTick;
                mPipeline = mPath.getEnd();
                mTick = mPipeline.getKeepTick();
            } else if (totalTick == 0) {
                mTotalTick = 0;
                mTick = 0;
                mPipeline = mPath.getStart();
            } else {
                mTotalTick = totalTick;
                long tt = totalTick;
                for (PLInfo path : mPath.getPaths()) {
                    mTick = tt;
                    tt -= path.getKeepTick();
                    if (tt < 0) {
                        mPipeline = path;
                        break;
                    }
                }
            }
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtPipeline = new NBTTagCompound();

        BlockPos start = mPath.getStart().getPos();
        BlockPos end = mPath.getEnd().getPos();
        nbtPipeline.setIntArray(NBT_BIND_PIPELINE_ELEMENT_START, new int[] {start.getX(), start.getY(), start.getZ()});
        nbtPipeline.setIntArray(NBT_BIND_PIPELINE_ELEMENT_END, new int[] {end.getX(), end.getY(), end.getZ()});
        nbtPipeline.setTag(NBT_BIND_PIPELINE_ELEMENT_ELEMENT, mElement);
        nbtPipeline.setLong(NBT_BIND_PIPELINE_ELEMENT_TICK, mTotalTick);
        nbtPipeline.setLong(NBT_BIND_PIPELINE_ELEMENT_NETWORK, mNetwork.getKey());

        nbt.setTag(NBT_BIND_PIPELINE_ELEMENT, nbtPipeline);
        return nbt;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtPipeline = nbt.getCompoundTag(NBT_BIND_PIPELINE_ELEMENT);
        mElement = nbtPipeline.getTag(NBT_BIND_PIPELINE_ELEMENT_ELEMENT);
        long total = nbtPipeline.getLong(NBT_BIND_PIPELINE_ELEMENT_TICK);
        long key = nbtPipeline.getLong(NBT_BIND_PIPELINE_ELEMENT_NETWORK);
        int[] startPos = nbtPipeline.getIntArray(NBT_BIND_PIPELINE_ELEMENT_START);
        int[] endPos = nbtPipeline.getIntArray(NBT_BIND_PIPELINE_ELEMENT_END);

        PLNetworkManager.getNetwork(key).ifPresent(network -> {
            BlockPos start = new BlockPos(startPos[0], startPos[1], startPos[2]);
            BlockPos end = new BlockPos(endPos[0], endPos[1], endPos[2]);
            assert mNetwork != null;
            mPath = mNetwork.queryPath(start, end);
            setTotalTick(total);
        });
    }
}
