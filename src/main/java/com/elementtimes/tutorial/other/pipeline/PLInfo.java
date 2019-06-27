package com.elementtimes.tutorial.other.pipeline;

import com.elementtimes.tutorial.common.block.Pipeline;
import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.server.FMLServerHandler;

import javax.annotation.Nonnull;

/**
 * 记录管道信息
 * INBTSerializable：预计可能使用 NBT 数据区分不同等级的管道
 * @author luqin2007
 */
public class PLInfo implements INBTSerializable<NBTTagCompound> {
    private PLNetwork mNetwork;
    private PLType mType;
    private int mKeepTick;
    private BlockPos mPos;

    /**
     * 创建管道信息
     * @param pos 管道位置
     * @param keepTick 速度指标
     * @param type
     */
    public PLInfo(World world, BlockPos pos, int keepTick, PLType type) {
        mType = type;
        mKeepTick = keepTick;
        mPos = pos;
        mNetwork = PLNetworkManager.join(world,this);
    }

    /**
     * 获取管道所在网路
     * @return 网络
     */
    @Nonnull
    public PLNetwork getNetwork() {
        return mNetwork;
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
     * 更新管道信息
     * @return 对应位置管道是否被移除
     */
    public boolean update() {
        int dim = mNetwork.getDim();
        FMLCommonHandler handler = FMLCommonHandler.instance();
        if (handler.getSide() == Side.SERVER) {
            // server
            WorldServer world = FMLServerHandler.instance().getServer().getWorld(dim);
            IBlockState state = world.getBlockState(mPos);
            TileEntity te = world.getTileEntity(mPos);
            if (state.getBlock() instanceof Pipeline && te instanceof TilePipeline) {
                mType = ((TilePipeline) te).getType();
                return true;
            }
            mNetwork.remove(this);
            return false;
        }
        return true;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt.setTag("_pipeline_", serializeNBT());
        return nbt;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("_type_", mType.toInt());
        nbt.setInteger("_keep_", mKeepTick);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtPipeline;
        if (nbt.hasKey("_pipeline_")) {
            nbtPipeline = nbt.getCompoundTag("_pipeline_");
        } else {
            nbtPipeline = nbt;
        }

        if (nbtPipeline.hasKey("_type_")) {
            mType = PLType.get(nbtPipeline.getInteger("_type_"));
        }

        if (nbtPipeline.hasKey("_keep_")) {
            mKeepTick = nbtPipeline.getInteger("_keep_");
        }
    }
}
