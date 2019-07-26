package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.block.Pipeline;
import com.elementtimes.tutorial.other.pipeline.PLInfo;
import com.elementtimes.tutorial.util.BlockUtil;
import com.elementtimes.tutorial.util.MathUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;

import static com.elementtimes.tutorial.common.block.Pipeline.TYPE_ITEM;

/**
 * 管道 TileEntity 类
 * @author luqin2007
 */
@SuppressWarnings("unused")
public class TilePipeline extends TileEntity {

    private static final String NBT_BIND_TP = "_pipeline_te_";
    private static final String NBT_BIND_TP_CONNECTED = "_pipeline_te_conn";
    private static final String NBT_BIND_TP_DISCONNECTED = "_pipeline_te_disconn";
    private static final String NBT_BIND_TP_INFO = "_pipeline_te_info";

    private int mConnected = 0b000000;
    private int mDisconnected = 0b000000;
    private PLInfo mInfo;

    public TilePipeline() {
        this(TYPE_ITEM, 20, PLInfo.PathActionItem.instance().name(), "");
    }

    public TilePipeline(String type, int tick, String actionName, String actionClass) {
        mInfo = new PLInfo.PLInfoBuilder()
                .setKeepTick(tick)
                .setAction(actionName, actionClass)
                .setType(type)
                .build();
    }

    public void setInfo(PLInfo info) {
        mInfo = info;
        mInfo.pos = pos;
        tryConnect(EnumFacing.values());
    }

    public PLInfo getInfo() {
        return mInfo;
    }

    public String getType() {
        return getInfo().type;
    }

    public void setDisconnected(int disconnected) {
        if (mDisconnected != disconnected) {
            mDisconnected = disconnected;
            if (!tryConnect(EnumFacing.values())) {
                markDirty();
            };
        }
    }

    public void setDisconnected(EnumFacing facing, boolean disconnect) {
        setDisconnected(MathUtil.setByte(mDisconnected, facing.getIndex(), disconnect));
    }

    public boolean isDisconnected(EnumFacing facing) {
        return MathUtil.fromByte(mDisconnected, facing.getIndex());
    }

    public void setConnected(int connected) {
        if (mConnected != connected) {
            mConnected = connected;
        }
    }

    public void setConnected(EnumFacing facing, boolean connected) {
        setConnected(MathUtil.setByte(mConnected, facing.getIndex(), connected));
    }

    public boolean isConnected(EnumFacing facing) {
        return MathUtil.fromByte(mConnected, facing.getIndex());
    }

    public IBlockState bindActualState(IBlockState state) {
        IBlockState newState = state;
        newState = BlockUtil.checkAndSetState(newState, Pipeline.PL_CONNECTED_UP, isConnected(EnumFacing.UP));
        newState = BlockUtil.checkAndSetState(newState, Pipeline.PL_CONNECTED_DOWN, isConnected(EnumFacing.DOWN));
        newState = BlockUtil.checkAndSetState(newState, Pipeline.PL_CONNECTED_EAST, isConnected(EnumFacing.EAST));
        newState = BlockUtil.checkAndSetState(newState, Pipeline.PL_CONNECTED_WEST, isConnected(EnumFacing.WEST));
        newState = BlockUtil.checkAndSetState(newState, Pipeline.PL_CONNECTED_NORTH, isConnected(EnumFacing.NORTH));
        newState = BlockUtil.checkAndSetState(newState, Pipeline.PL_CONNECTED_SOUTH, isConnected(EnumFacing.SOUTH));
        return newState;
    }

    public boolean tryConnect(EnumFacing... facings) {
        boolean changed = false;
        for (EnumFacing facing : facings) {
            if (facing != null) {
                BlockPos pos = this.pos.offset(facing);
                TileEntity te = world.getTileEntity(pos);
                if (te != null) {
                    if (getInfo().action.canConnect(world, pos, null) && !isConnected(facing)) {
                        setConnected(facing, true);
                        if (!(te instanceof TilePipeline)) {
                            getInfo().listOut.add(pos);
                        }
                        changed = true;
                    } else if (isConnected(facing)) {
                        setConnected(facing, false);
                        getInfo().listOut.remove(pos);
                        changed = true;
                    }
                }
            }
        }
        if (changed) {
            world.setBlockState(pos, bindActualState(world.getBlockState(pos)));
            world.markBlockRangeForRenderUpdate(pos, pos);
            markDirty();
        }
        return changed;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagCompound pipeline = new NBTTagCompound();
        pipeline.setInteger(NBT_BIND_TP_CONNECTED, mConnected);
        pipeline.setInteger(NBT_BIND_TP_DISCONNECTED, mConnected);
        pipeline.setTag(NBT_BIND_TP_INFO, mInfo.serializeNBT());
        compound.setTag(NBT_BIND_TP, pipeline);
        return compound;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey(NBT_BIND_TP)) {
            NBTTagCompound pipeline = compound.getCompoundTag(NBT_BIND_TP);
            if (pipeline.hasKey(NBT_BIND_TP_INFO)) {
                mInfo.deserializeNBT(pipeline.getCompoundTag(NBT_BIND_TP_INFO));
            }
            if (pipeline.hasKey(NBT_BIND_TP_DISCONNECTED)) {
                mDisconnected = pipeline.getInteger(NBT_BIND_TP_DISCONNECTED);
            }
            if (pipeline.hasKey(NBT_BIND_TP_CONNECTED)) {
                setConnected(pipeline.getInteger(NBT_BIND_TP_CONNECTED));
            }
            markDirty();
        }
    }
}
