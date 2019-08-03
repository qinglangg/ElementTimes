package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.block.Pipeline;
import com.elementtimes.tutorial.other.pipeline.PLConnType;
import com.elementtimes.tutorial.other.pipeline.PLInfo;
import com.elementtimes.tutorial.util.BlockUtil;
import com.elementtimes.tutorial.util.MathUtil;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Consumer;

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
        mInfo = new PLInfo.PLInfoBuilder()
                .setKeepTick(20)
                .setAction(PLInfo.PathActionItem.instance().name(), "")
                .setType(TYPE_ITEM)
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

    private void setDisconnected(int disconnected) {
        if (mDisconnected != disconnected) {
            mDisconnected = disconnected;
            if (!tryConnect(EnumFacing.values())) {
                markDirty();
            }
        }
    }

    public void setDisconnected(EnumFacing facing, boolean disconnect) {
        setDisconnected(MathUtil.setByte(mDisconnected, facing.getIndex(), disconnect));
    }

    @SuppressWarnings({"BooleanMethodIsAlwaysInverted", "WeakerAccess"})
    public boolean isDisconnected(EnumFacing facing) {
        return MathUtil.fromByte(mDisconnected, facing.getIndex());
    }

    @SuppressWarnings("WeakerAccess")
    public boolean setConnected(int connected) {
        if (mConnected != connected) {
            mConnected = connected;
            return true;
        }
        return false;
    }

    @SuppressWarnings("WeakerAccess")
    public boolean setConnected(EnumFacing facing, boolean connected) {
        return setConnected(MathUtil.setByte(mConnected, facing.getIndex(), connected));
    }

    public boolean isConnected(EnumFacing facing) {
        return MathUtil.fromByte(mConnected, facing.getIndex());
    }

    public IBlockState bindActualState(IBlockState state) {
        IBlockState newState = state;
        PLInfo info = getInfo();
        boolean connectedUp = isConnected(EnumFacing.UP);
        if (newState.getValue(Pipeline.PL_CONNECTED_UP) != connectedUp) {
            newState = BlockUtil.checkAndSetState(newState, Pipeline.PL_CONNECTED_UP, connectedUp);
        }
        boolean connectedDown = isConnected(EnumFacing.DOWN);
        if (newState.getValue(Pipeline.PL_CONNECTED_DOWN) != connectedDown) {
            newState = BlockUtil.checkAndSetState(newState, Pipeline.PL_CONNECTED_DOWN, connectedDown);
        }
        boolean connectedEast = isConnected(EnumFacing.EAST);
        if (newState.getValue(Pipeline.PL_CONNECTED_EAST) != connectedEast) {
            newState = BlockUtil.checkAndSetState(newState, Pipeline.PL_CONNECTED_EAST, connectedEast);
        }
        boolean connectedWest = isConnected(EnumFacing.WEST);
        if (newState.getValue(Pipeline.PL_CONNECTED_WEST) != connectedWest) {
            newState = BlockUtil.checkAndSetState(newState, Pipeline.PL_CONNECTED_WEST, connectedWest);
        }
        boolean connectedNorth = isConnected(EnumFacing.NORTH);
        if (newState.getValue(Pipeline.PL_CONNECTED_NORTH) != connectedNorth) {
            newState = BlockUtil.checkAndSetState(newState, Pipeline.PL_CONNECTED_NORTH, connectedNorth);
        }
        boolean connectedSouth = isConnected(EnumFacing.SOUTH);
        if (newState.getValue(Pipeline.PL_CONNECTED_SOUTH) != connectedSouth) {
            newState = BlockUtil.checkAndSetState(newState, Pipeline.PL_CONNECTED_SOUTH, connectedSouth);
        }
        return newState;
    }

    public boolean tryConnect(EnumFacing... facings) {
        boolean changed = false;
        for (EnumFacing facing : facings) {
            TileEntity te = world.getTileEntity(pos.offset(facing));
            PLInfo info = getInfo();
            if (!isDisconnected(facing)) {
                // 未阻断
                PLConnType type = info.action.connectType(world, pos, facing, null);
                switch (type) {
                    case PIPELINE:
                        // 连接管道
                        changed = connectPipeline(te, facing, pos, info) || changed;
                        break;
                    case OUT:
                        // 连接输出
                        changed = connectOut(te, facing, pos, info) || changed;
                        break;
                    case IN:
                        // 连接输入
                        changed = connectIn(te, facing, pos, info) || changed;
                        break;
                    case INOUT:
                        changed = connectInOut(te, facing, pos, info) || changed;
                        break;
                    default:
                        // 无连接
                        changed = connectNull(te, facing, pos, info) || changed;
                }
            } else {
                changed = connectNull(te, facing, pos, info) || changed;
            }
        }
        if (changed) {
            markDirty();
        }
        return changed;
    }

    private boolean connectNull(TileEntity te, EnumFacing facing, BlockPos pos, PLInfo info) {
        if (te instanceof TilePipeline) {
            TilePipeline tp = (TilePipeline) te;
            EnumFacing opposite = facing.getOpposite();
            if (((TilePipeline) te).isConnected(opposite) && ((TilePipeline) te).setConnected(opposite, false)) {
                te.markDirty();
            }
        }
        boolean changed = setConnected(facing, false);
        boolean changed2 = removeFromInfo(info, pos);
        return changed || changed2;
    }

    private boolean connectPipeline(TileEntity te, EnumFacing facing, BlockPos pos, PLInfo info) {
        if (te instanceof TilePipeline) {
            boolean changed;
            TilePipeline tp = (TilePipeline) te;
            EnumFacing opposite = facing.getOpposite();
            if (tp.getInfo().action.connectType(world, this.pos, facing, null) == PLConnType.PIPELINE
                    && !tp.isDisconnected(opposite)) {
                // 其他管道可连接
                changed = setConnected(facing, true);
                if (tp.setConnected(opposite, true)) {
                    tp.markDirty();
                }
            } else {
                // 其他管道不可连接
                changed = setConnected(facing, false);
                if (tp.setConnected(opposite, false)) {
                    tp.markDirty();
                }
            }
            boolean changed2 = removeFromInfo(info, pos);
            return changed || changed2;
        } else {
            return connectNull(te, facing, pos, info);
        }
    }

    private boolean connectOut(TileEntity te, EnumFacing facing, BlockPos pos, PLInfo info) {
        boolean changed = setConnected(facing, true);
        if (!info.listOut.contains(pos)) {
            info.listOut.add(pos);
            changed = true;
        }
        if (info.listIn.contains(pos)) {
            info.listIn.remove(pos);
            changed = true;
        }
        return changed;
    }

    private boolean connectIn(TileEntity te, EnumFacing facing, BlockPos pos, PLInfo info) {
        boolean changed = setConnected(facing, true);
        if (!info.listIn.contains(pos)) {
            info.listIn.add(pos);
            changed = true;
        }
        if (info.listOut.contains(pos)) {
            info.listOut.remove(pos);
            changed = true;
        }
        return changed;
    }

    private boolean connectInOut(TileEntity te, EnumFacing facing, BlockPos pos, PLInfo info) {
        boolean changed = setConnected(facing, true);
        if (!info.listIn.contains(pos)) {
            info.listIn.add(pos);
            changed = true;
        }
        if (!info.listOut.contains(pos)) {
            info.listOut.add(pos);
            changed = true;
        }
        return changed;
    }

    private boolean removeFromInfo(PLInfo info, BlockPos pos) {
        boolean changed = false;
        if (info.listIn.contains(pos)) {
            changed = true;
            info.listIn.remove(pos);
        }
        if (info.listOut.contains(pos)) {
            changed = true;
            info.listOut.remove(pos);
        }
        return changed;
    }

    @Override
    public void markDirty() {
        if (world != null && world.isBlockLoaded(pos) && world.getBlockState(pos).getBlock() == getBlockType()) {
            IBlockState bs;
            if (!isInvalid()) {
                bs = blockType.getDefaultState();
            } else {
                bs = world.getBlockState(pos);
            }
            IBlockState state = bindActualState(bs);
            if (bs != state) {
                world.notifyBlockUpdate(pos, bs, state, 3);
                BlockUtil.setBlockState(world, pos, state, this);
            }
        }
        super.markDirty();
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 0, writeToNBT(new NBTTagCompound()));
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        readFromNBT(pkt.getNbtCompound());
    }

    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        super.writeToNBT(compound);
        NBTTagCompound pipeline = new NBTTagCompound();
        pipeline.setInteger(NBT_BIND_TP_CONNECTED, mConnected);
        pipeline.setInteger(NBT_BIND_TP_DISCONNECTED, mDisconnected);
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
                mConnected = pipeline.getInteger(NBT_BIND_TP_CONNECTED);
            }
        }
    }

    @Override
    public void onLoad() {
        super.onLoad();
        markDirty();
    }

    public static class Tickable extends TilePipeline implements ITickable {

        private Consumer<TilePipeline> mTickable;

        public Tickable(TilePipeline tp, Consumer<TilePipeline> tickable) {
            readFromNBT(tp.writeToNBT(new NBTTagCompound()));
            mTickable = tickable;
        }

        @Override
        public void update() {
            if (world != null) {
                mTickable.accept(this);
            }
        }
    }
}
