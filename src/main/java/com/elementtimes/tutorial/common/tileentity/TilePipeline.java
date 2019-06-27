package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.block.Pipeline;
import com.elementtimes.tutorial.other.pipeline.*;
import com.elementtimes.tutorial.util.MathUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.tuple.MutablePair;

import java.util.HashSet;
import java.util.LinkedList;

/**
 * 管道 TileEntity 类
 * @author luqin2007
 */
public class TilePipeline extends TileEntity {

    private int mConnected = 0b000000;
    private int mDisconnected = 0b000000;
    private boolean mHasElement = false;
    private PLInfo mInfo;

    public TilePipeline() {
        mInfo = new PLInfo(world, pos, 20, getType());
    }

    /**
     * 更新管道连接信息
     */
    public void update() {
        boolean isChange = false;
        // 管道已删除
        if (world.getTileEntity(pos) != this || world.getBlockState(pos).getBlock() instanceof Pipeline) {
            mInfo.getNetwork().remove(mInfo);
            return;
        }
        PLType type = getWorld().getBlockState(pos).getValue(Pipeline.PL_TYPE);
        for (EnumFacing facing : EnumFacing.values()) {
            if (!isDisconnected(facing)) {
                TileEntity te = world.getTileEntity(pos.offset(facing));
                if (te instanceof TilePipeline) {
                    TilePipeline tp = (TilePipeline) te;
                    if (type.type().equals(tp.getType().type()) && !tp.isDisconnected(facing.getOpposite())) {
                        setConnected(facing, true);
                    } else {
                        setConnected(facing, false);
                    }
                } else if (te == null) {
                    setConnected(facing, false);
                } else {
                    if (type.type() == PLElementType.Fluid) {
                        IFluidHandler capability = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing.getOpposite());
                        setConnected(facing, capability != null);
                    } else if (type.type() == PLElementType.Item) {
                        IItemHandler capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing.getOpposite());
                        setConnected(facing, capability != null);
                    }
                }
            }
        }
    }

    public PLType getType() {
        return world.getBlockState(pos).getValue(Pipeline.PL_TYPE);
    }

    public void setDisconnected(int disconnected) {
        mDisconnected = disconnected;
    }

    public void setDisconnected(EnumFacing facing, boolean disconnect) {
        mDisconnected = MathUtil.setByte(mDisconnected, facing.getIndex(), disconnect);
    }

    public boolean isDisconnected(EnumFacing facing) {
        return MathUtil.fromByte(mDisconnected, facing.getIndex());
    }

    public void setConnected(int connected) {
        mConnected = connected;
    }

    public void setConnected(EnumFacing facing, boolean connected) {
        mConnected = MathUtil.setByte(mConnected, facing.getIndex(), connected);
    }

    public boolean isConnected(EnumFacing facing) {
        return MathUtil.fromByte(mConnected, facing.getIndex());
    }

    public IBlockState getActualState(IBlockState state) {
        return state.withProperty(Pipeline.PL_DISCONNECTED, mDisconnected)
                .withProperty(Pipeline.PL_CONNECTED, mConnected)
                .withProperty(Pipeline.PL_HAS_ELEM, mHasElement);
    }

    public static void notifyUpdate(TileEntity te) {
        if (te instanceof TilePipeline) {
            ((TilePipeline) te).update();
        }
    }
}
