package com.elementtimes.tutorial.other.pipeline;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * 记录管道信息
 * INBTSerializable：预计可能使用 NBT 数据区分不同等级的管道
 * @author luqin2007
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PLInfo implements INBTSerializable<NBTTagCompound> {

    private static final String NBT_BIND_PIPELINE = "_pipeline_";
    private static final String NBT_BIND_PIPELINE_TYPE = "_pl_type_";
    private static final String NBT_BIND_PIPELINE_TICK = "_pl_tick_";
    private static final String NBT_BIND_PIPELINE_POS = "_pl_pos_";

    private PLNetwork mNetwork = null;
    private PLType mType;
    private int mKeepTick;
    private BlockPos mPos;

    /**
     * 创建管道信息
     * @param pos 管道位置
     * @param keepTick 速度指标
     * @param type 管道类型
     */
    public PLInfo(BlockPos pos, int keepTick, PLType type) {
        mType = type;
        mKeepTick = keepTick;
        mPos = pos;
    }

    public PLInfo() {
        this(new BlockPos(0, 0, 0), 20, PLType.Item);
    }

    /**
     * 获取管道所在网路
     * @return 网络
     */
    public PLNetwork getNetwork() {
        return mNetwork;
    }

    /**
     * 设置管道所在网络
     * 用于网络发生变化时
     * @param network 管道所在网络
     */
    public void setNetwork(PLNetwork network) {
        mNetwork = network;
    }

    /**
     * 设置某一类管道元素经过/充满管道所需时间（tick）
     * 作为管道的速度指标，值越大越慢
     * @param keepTick 元素经过/充满管道所需时间（tick）
     */
    public void setKeepTick(int keepTick) {
        mKeepTick = keepTick;
    }

    /**
     * 获取元素经过/充满管道所需时间（tick）
     * @return 元素经过/充满管道所需时间（tick）
     */
    public int getKeepTick() {
        return mKeepTick;
    }

    /**
     * 设置管道位置
     * 不确定要不要保存管道所在世界（维度），感觉把维度归于 PLNetwork 中比较好
     * @param pos 管道位置
     */
    public void setPos(BlockPos pos) {
        mPos = pos;
    }

    /**
     * 获取管道位置
     * @return 管道位置
     */
    public BlockPos getPos() {
        return mPos;
    }

    /**
     * 设置管道类型
     * @param type 管道类型
     */
    public void setType(PLType type) {
        mType = type;
    }

    /**
     * 获取管道类型
     * @return 管道类型
     */
    public PLType getType() {
        return mType;
    }

    /**
     * 标记管道已经从网络中移除
     */
    public void remove(World world) {
        mNetwork.remove(world, this);
        mNetwork = null;
        mPos = null;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound pl = new NBTTagCompound();
        pl.setInteger(NBT_BIND_PIPELINE_TYPE, mType.toInt());
        pl.setInteger(NBT_BIND_PIPELINE_TICK, mKeepTick);
        pl.setIntArray(NBT_BIND_PIPELINE_POS, new int[] {mPos.getX(), mPos.getY(), mPos.getZ()});
        nbt.setTag(NBT_BIND_PIPELINE, pl);
        return nbt;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtPipeline = nbt.getCompoundTag(NBT_BIND_PIPELINE);
        mType = PLType.get(nbtPipeline.getInteger(NBT_BIND_PIPELINE_TYPE));
        mKeepTick = nbtPipeline.getInteger(NBT_BIND_PIPELINE_TICK);
        int[] pos = nbtPipeline.getIntArray(NBT_BIND_PIPELINE_POS);
        mPos = new BlockPos(pos[0], pos[1], pos[2]);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof PLInfo && this.getPos().equals(((PLInfo) obj).getPos());
    }

    @Override
    public int hashCode() {
        return Objects.hash(mKeepTick, mNetwork.getKey(), mPos, mType.toInt());
    }
}
