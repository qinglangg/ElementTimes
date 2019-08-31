package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.ECUtils;
import com.elementtimes.tutorial.common.block.Pipeline;
import com.elementtimes.tutorial.other.pipeline.PLConnType;
import com.elementtimes.tutorial.other.pipeline.PLElement;
import com.elementtimes.tutorial.other.pipeline.PLInfo;
import com.elementtimes.tutorial.other.pipeline.PLPath;
import com.elementtimes.tutorial.other.pipeline.interfaces.PathAction;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static com.elementtimes.tutorial.common.block.Pipeline.TYPE_ITEM;

/**
 * 管道 TileEntity 类
 * @author luqin2007
 */
@SuppressWarnings("unused")
public class TilePipeline extends TileEntity implements ITickable {

    private static final String NBT_BIND_TP = "_pipeline_te_";
    private static final String NBT_BIND_TP_CONNECTED = "_pipeline_te_conn";
    private static final String NBT_BIND_TP_DISCONNECTED = "_pipeline_te_disconn";
    private static final String NBT_BIND_TP_INFO = "_pipeline_te_info";
    private static final String NBT_BIND_TP_ELEMENTS = "_pipeline_te_elements";

    public static final Consumer<TilePipeline> LINK = t -> {
        t.elements.forEach(e -> e.onTick(t.world));
        t.elements.addAll(t.elementsAdd);
        t.elementsAdd.clear();
        t.elements.removeAll(t.elementsRemove);
        t.elementsRemove.clear();
    };
    public static final Consumer<TilePipeline> ITEM_IN_20 = t -> {
        t.tick++;
        if (t.tick % 20 != 0) {
            return;
        }

        PLInfo tInfo = t.getInfo();
        for (BlockPos blockPos : tInfo.listIn) {
            TileEntity te = t.getWorld().getTileEntity(blockPos);
            if (te != null) {
                EnumFacing facing = ECUtils.block.getPosFacing(t.getPos(), blockPos);
                if (te.hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing)) {
                    IItemHandler capability = te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, facing);
                    if (capability != null) {
                        for (int i = 0; i < capability.getSlots(); i++) {
                            ItemStack stackInSlot = capability.getStackInSlot(i);
                            ItemStack extractItem = capability.extractItem(i, stackInSlot.getCount(), true);
                            if (!extractItem.isEmpty()) {
                                PLElement element = PLElement.item(extractItem);
                                Map<BlockPos, PLPath> pathMap = t.getInfo().allValidOutput(t.getWorld(), element, blockPos);
                                if (pathMap.size() > 0) {
                                    element.path = pathMap.values().iterator().next();
                                    capability.extractItem(i, extractItem.getCount(), false);
                                    element.send(t);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    };
    public static final Consumer<TilePipeline> FLUID_IN_20 = t -> {
        t.tick++;
        if (t.tick % 20 != 0) {
            return;
        }

        PLInfo tInfo = t.getInfo();
        for (BlockPos blockPos : tInfo.listIn) {
            TileEntity te = t.getWorld().getTileEntity(blockPos);
            if (te != null) {
                EnumFacing facing = ECUtils.block.getPosFacing(t.getPos(), blockPos);
                if (te.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing)) {
                    IFluidHandler capability = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, facing);
                    if (capability != null) {
                        for (IFluidTankProperties property : capability.getTankProperties()) {
                            FluidStack fluidStack = property.getContents();
                            FluidStack drain = capability.drain(fluidStack, false);
                            if (drain != null && drain.amount > 0) {
                                PLElement element = PLElement.fluid(drain);
                                Map<BlockPos, PLPath> pathMap = t.getInfo().allValidOutput(t.getWorld(), element, blockPos);
                                if (pathMap.size() > 0) {
                                    element.path = pathMap.values().iterator().next();
                                    capability.drain(fluidStack, true);
                                    element.send(t);
                                    return;
                                }
                            }
                        }
                    }
                }
            }
        }
    };
    public static final Map<String, Consumer<TilePipeline>> TICKABLES = new LinkedHashMap<>();

    static {
        TICKABLES.put(Pipeline.TYPE_ITEM_IN, ITEM_IN_20);
        TICKABLES.put(Pipeline.TYPE_FLUID_IN, FLUID_IN_20);
    }

    private int mConnected = 0b000000;
    private int mDisconnected = 0b000000;
    private int tick = 0;
    private PLInfo mInfo;

    public final List<PLElement> elements = new LinkedList<>();
    public final List<PLElement> elementsAdd = new LinkedList<>();
    public final List<PLElement> elementsRemove = new LinkedList<>();

    public TilePipeline() {
        mInfo = new PLInfo.PLInfoBuilder()
                .setKeepTick(20)
                .setAction(PathAction.ItemLink.instance())
                .setType(TYPE_ITEM)
                .build();
    }

    // Info

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

    // Connect

    private void setDisconnected(int disconnected) {
        if (mDisconnected != disconnected) {
            mDisconnected = disconnected;
            if (!tryConnect(EnumFacing.values())) {
                markDirty();
            }
        }
    }

    public void setDisconnected(EnumFacing facing, boolean disconnect) {
        setDisconnected(ECUtils.math.setByte(mDisconnected, facing.getIndex(), disconnect));
    }

    @SuppressWarnings({"BooleanMethodIsAlwaysInverted", "WeakerAccess"})
    public boolean isDisconnected(EnumFacing facing) {
        return ECUtils.math.fromByte(mDisconnected, facing.getIndex());
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
        return setConnected(ECUtils.math.setByte(mConnected, facing.getIndex(), connected));
    }

    public boolean isConnected(EnumFacing facing) {
        return ECUtils.math.fromByte(mConnected, facing.getIndex());
    }

    public IBlockState bindActualState(IBlockState state) {
        IBlockState newState = state;
        PLInfo info = getInfo();
        boolean connectedUp = isConnected(EnumFacing.UP);
        if (newState.getValue(Pipeline.PL_CONNECTED_UP) != connectedUp) {
            newState = ECUtils.block.checkAndSetState(newState, Pipeline.PL_CONNECTED_UP, connectedUp);
        }
        boolean connectedDown = isConnected(EnumFacing.DOWN);
        if (newState.getValue(Pipeline.PL_CONNECTED_DOWN) != connectedDown) {
            newState = ECUtils.block.checkAndSetState(newState, Pipeline.PL_CONNECTED_DOWN, connectedDown);
        }
        boolean connectedEast = isConnected(EnumFacing.EAST);
        if (newState.getValue(Pipeline.PL_CONNECTED_EAST) != connectedEast) {
            newState = ECUtils.block.checkAndSetState(newState, Pipeline.PL_CONNECTED_EAST, connectedEast);
        }
        boolean connectedWest = isConnected(EnumFacing.WEST);
        if (newState.getValue(Pipeline.PL_CONNECTED_WEST) != connectedWest) {
            newState = ECUtils.block.checkAndSetState(newState, Pipeline.PL_CONNECTED_WEST, connectedWest);
        }
        boolean connectedNorth = isConnected(EnumFacing.NORTH);
        if (newState.getValue(Pipeline.PL_CONNECTED_NORTH) != connectedNorth) {
            newState = ECUtils.block.checkAndSetState(newState, Pipeline.PL_CONNECTED_NORTH, connectedNorth);
        }
        boolean connectedSouth = isConnected(EnumFacing.SOUTH);
        if (newState.getValue(Pipeline.PL_CONNECTED_SOUTH) != connectedSouth) {
            newState = ECUtils.block.checkAndSetState(newState, Pipeline.PL_CONNECTED_SOUTH, connectedSouth);
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
                        changed = connectPipeline(te, facing, info) || changed;
                        break;
                    case OUT:
                        // 连接输出
                        changed = connectOut(te, facing, info) || changed;
                        break;
                    case IN:
                        // 连接输入
                        changed = connectIn(te, facing, info) || changed;
                        break;
                    case INOUT:
                        changed = connectInOut(te, facing, info) || changed;
                        break;
                    default:
                        // 无连接
                        changed = connectNull(te, facing, info) || changed;
                }
            } else {
                changed = connectNull(te, facing, info) || changed;
            }
        }
        if (changed) {
            markDirty();
        }
        return changed;
    }

    private boolean connectNull(TileEntity te, EnumFacing facing, PLInfo info) {
        if (te instanceof TilePipeline) {
            TilePipeline tp = (TilePipeline) te;
            EnumFacing opposite = facing.getOpposite();
            if (((TilePipeline) te).isConnected(opposite) && ((TilePipeline) te).setConnected(opposite, false)) {
                te.markDirty();
            }
        }
        BlockPos connectPos = pos.offset(facing);
        boolean changed = setConnected(facing, false);
        changed = info.listIn.remove(connectPos) || changed;
        changed = info.listOut.remove(connectPos) || changed;
        return changed;
    }

    private boolean connectPipeline(TileEntity te, EnumFacing facing, PLInfo info) {
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
            BlockPos connectPos = pos.offset(facing);
            changed = info.listIn.remove(connectPos) || changed;
            changed = info.listOut.remove(connectPos) || changed;
            return changed;
        } else {
            return connectNull(te, facing, info);
        }
    }

    private boolean connectOut(TileEntity te, EnumFacing facing, PLInfo info) {
        boolean changed = setConnected(facing, true);
        BlockPos connectPos = pos.offset(facing);
        if (!info.listOut.contains(connectPos)) {
            info.listOut.add(connectPos);
            changed = true;
        }
        changed = info.listIn.remove(connectPos) || changed;
        return changed;
    }

    private boolean connectIn(TileEntity te, EnumFacing facing, PLInfo info) {
        boolean changed = setConnected(facing, true);
        BlockPos connectPos = pos.offset(facing);
        if (!info.listIn.contains(connectPos)) {
            info.listIn.add(connectPos);
            changed = true;
        }
        changed = info.listOut.remove(connectPos) || changed;
        return changed;
    }

    private boolean connectInOut(TileEntity te, EnumFacing facing, PLInfo info) {
        boolean changed = setConnected(facing, true);
        BlockPos connectPos = pos.offset(facing);
        if (!info.listIn.contains(connectPos)) {
            info.listIn.add(connectPos);
            changed = true;
        }
        if (!info.listOut.contains(connectPos)) {
            info.listOut.add(connectPos);
            changed = true;
        }
        return changed;
    }

    // NBT && 同步

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
                ECUtils.block.setBlockState(world, pos, state, this);
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
        NBTTagList listElements = new NBTTagList();
        elements.addAll(elementsAdd);
        elements.removeAll(elementsRemove);
        for (PLElement plElement : elements) {
            listElements.appendTag(plElement.serializeNBT());
        }
        pipeline.setTag(NBT_BIND_TP_ELEMENTS, listElements);
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
                setTickable();
            }
            if (pipeline.hasKey(NBT_BIND_TP_DISCONNECTED)) {
                mDisconnected = pipeline.getInteger(NBT_BIND_TP_DISCONNECTED);
            }
            if (pipeline.hasKey(NBT_BIND_TP_CONNECTED)) {
                mConnected = pipeline.getInteger(NBT_BIND_TP_CONNECTED);
            }
            if (pipeline.hasKey(NBT_BIND_TP_ELEMENTS)) {
                elements.clear();
                NBTTagList list = (NBTTagList) pipeline.getTag(NBT_BIND_TP_ELEMENTS);
                for (NBTBase nbtBase : list) {
                    NBTTagCompound plElement = (NBTTagCompound) nbtBase;
                    PLElement e = new PLElement();
                    e.deserializeNBT(plElement);
                    e.tp = this;
                    elements.add(e);
                }
            }
        }
    }

    // Tickable

    private Consumer<TilePipeline> mTickable = LINK;

    @Override
    public void update() {
        if (world != null) {
            mTickable.accept(this);
        }
    }

    public void setTickable() {
        if (mInfo != null) {
            mTickable = TICKABLES.getOrDefault(mInfo.type, LINK);
        }
    }
}
