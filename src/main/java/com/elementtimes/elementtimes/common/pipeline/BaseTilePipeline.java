package com.elementtimes.elementtimes.common.pipeline;

import com.elementtimes.elementcore.api.utils.BlockUtils;
import com.elementtimes.elementcore.api.utils.MathUtils;
import com.elementtimes.elementcore.api.utils.path.Path;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.state.IProperty;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import java.util.*;

import static com.elementtimes.elementtimes.common.block.pipeline.Pipeline.PROPERTIES;

/**
 * 管道 TileEntity 类

 */
@SuppressWarnings({"DuplicatedCode", "UnusedReturnValue", "WeakerAccess", "NullableProblems"})
public abstract class BaseTilePipeline extends TileEntity implements ITickableTileEntity, IPipeline {

    private static final String NBT = "_ec_pl_";

    private final List<BaseElement<?>> elements = new LinkedList<>();
    private final List<BaseElement<?>> elementAdd = new LinkedList<>();
    private final List<BaseElement<?>> elementRemove = new LinkedList<>();

    private BlockState newBlockState = null;

    public BaseTilePipeline(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Override
    public void onNeighborChanged(BlockPos neighbor) {
        if (getConnectState(neighbor) == ConnectType.BAN) {
            return;
        }
        setConnectState(neighbor, ConnectType.CONNECTED, ConnectType.INPUT, ConnectType.OUTPUT, ConnectType.NONE);
    }

    @Override
    public void onRemoved(BlockState state) {
        if (world == null || world.isRemote) {
            return;
        }
        updateElement();
        for (BaseElement<?> element : elements) {
            dropElement(element);
        }
    }

    @Override
    public void onPlace(ItemStack stack, LivingEntity placer) {
        for (Direction direction : Direction.values()) {
            setConnectState(pos.offset(direction), ConnectType.CONNECTED, ConnectType.INPUT, ConnectType.OUTPUT, ConnectType.NONE);
        }
    }

    @Override
    public List<BlockPos> allReachablePipelines() {
        BlockState state = getBlockState();
        List<BlockPos> list = new ArrayList<>();
        PROPERTIES.forEach((direction, property) -> {
            if (state.get(property) == ConnectType.CONNECTED) {
                list.add(pos.offset(direction));
            }
        });
        return list;
    }

    @Override
    public List<BlockPos> allReachableOutputs() {
        BlockState state = getBlockState();
        List<BlockPos> list = new ArrayList<>();
        PROPERTIES.forEach((direction, property) -> {
            if (state.get(property) == ConnectType.OUTPUT) {
                list.add(pos.offset(direction));
            }
        });
        return list;
    }

    @Override
    public List<BlockPos> allReachableInputs() {
        BlockState state = getBlockState();
        List<BlockPos> list = new ArrayList<>();
        PROPERTIES.forEach((direction, property) -> {
            if (state.get(property) == ConnectType.INPUT) {
                list.add(pos.offset(direction));
            }
        });
        return list;
    }

    @Override
    public void setConnectState(BlockPos target, IConnectType... types) {
        if (types.length == 0 || world == null || world.isRemote) {
            return;
        }
        Direction direction = BlockUtils.getPosFacing(pos, target);
        if (direction == null) {
            return;
        }
        for (IConnectType type : types) {
            if (!(type instanceof ConnectType)) {
                continue;
            }
            ConnectType ct = (ConnectType) type;
            TileEntity tileEntity = world.getTileEntity(target);
            if (tileEntity instanceof IPipeline) {
                if (((IPipeline) tileEntity).connectedBy(this, ct, false)) {
                    setBlockState(PROPERTIES.get(direction), ct);
                    return;
                }
            } else {
                boolean canSet;
                switch (ct) {
                    case CONNECTED:
                        canSet = false;
                        break;
                    case INPUT:
                        canSet = canInputElementFrom(target);
                        break;
                    case OUTPUT:
                        canSet = canOutputElementTo(target);
                        break;
                    default:
                        canSet = true;
                        break;
                }
                if (!canSet) {
                    continue;
                }
                setBlockState(PROPERTIES.get(direction), ct);
                return;
            }
        }
    }

    @Override
    public ConnectType getConnectState(BlockPos target) {
        Direction direction = BlockUtils.getPosFacing(pos, target);
        if (direction == null) {
            return ConnectType.BAN;
        }
        return getBlockState().get(PROPERTIES.get(direction));
    }

    @Override
    public boolean connectedBy(IPipeline from, IConnectType type, boolean simulate) {
        if (world == null || world.isRemote) {
            return false;
        }
        if (!(type instanceof ConnectType)) {
            return false;
        }
        Direction direction = BlockUtils.getPosFacing(pos, from.getPipelinePos());
        ConnectType connectState = getConnectState(from.getPipelinePos());
        if (connectState == ConnectType.BAN) {
            return !type.isConnected();
        }
        if (!type.isConnected()) {
            setBlockState(PROPERTIES.get(direction), ConnectType.NONE);
            return true;
        }
        if (type == ConnectType.CONNECTED) {
            setBlockState(PROPERTIES.get(direction), ConnectType.CONNECTED);
            return true;
        }
        setBlockState(PROPERTIES.get(direction), ConnectType.NONE);
        return false;
    }

    @Override
    public boolean canInputElementFrom(BlockPos from) {
        return false;
    }

    @Override
    public boolean canOutputElementTo(BlockPos target) {
        return false;
    }

    @Override
    public <T> BaseElement<T> outputElement(BlockPos target, BaseElement<T> element, boolean simulate) {
        return element;
    }

    @Override
    public <T> BaseElement<T> sendElementTo(IPipeline target, BaseElement<T> element, boolean simulate) {
        if (world != null && !world.isRemote) {
            return target.receiveElement(this, element, simulate);
        }
        return element;
    }

    @Override
    @Nonnull
    public void extractElements(boolean simulate) { }

    @Override
    public void removeElement(BaseElement<?> element) {
        elementRemove.add(element);
    }

    @Override
    public void addElement(BaseElement<?> element) {
        elementAdd.add(element);
    }

    @Override
    public void dropElement(BaseElement<?> element) {
        element.drop(world, pos);
        removeElement(element);
    }

    @Override
    public BlockPos getPipelinePos() {
        return pos;
    }

    @Override
    public World getPipelineWorld() {
        return world;
    }

    @Override
    public void tick() {
        if (world == null || world.isRemote) {
            return;
        }
        updateElement();
        // tick
        elements.forEach(BaseElement::tickIncrease);
        // output
        List<BlockPos> outputs = allReachableOutputs();
        for (BaseElement<?> element : elements) {
            if (!element.isFinalPos() || element.tick < getKeepTime(element)) {
                continue;
            }
            if (element.isEmpty()) {
                removeElement(element);
                continue;
            }
            BaseElement<?> outputElement = element.copy();
            for (BlockPos output : outputs) {
                outputElement = outputElement(output, outputElement, false);
                if (outputElement.isEmpty()) {
                    break;
                }
            }
            if (element.isElementEqual(outputElement.element)) {
                continue;
            }
            removeElement(element);
            if (!outputElement.isEmpty()) {
                backElement(outputElement);
            }
        }
        updateElement();
        // post
        for (BaseElement<?> element : elements) {
            if (element.isEmpty()) {
                removeElement(element);
                continue;
            }
            if (element.tick < getKeepTime(element)) {
                continue;
            }
            BlockPos nextPos = element.moveToNextPos();
            if (nextPos == null) {
                dropElement(element);
                continue;
            }
            TileEntity te = world.getTileEntity(nextPos);
            if (te instanceof IPipeline) {
                BaseElement<?> receiveElement = sendElementTo((IPipeline) te, element, false);
                removeElement(element);
                if (!receiveElement.isEmpty()) {
                    backElement(receiveElement);
                }
            }
        }
        // input
        extractElements(false);
        updateElement();

        markDirty();
        if (newBlockState != null) {
            BlockState state = world.getBlockState(pos);
            if (state != newBlockState) {
                BlockUtils.setBlockState(world, pos, newBlockState);
            }
        }
        newBlockState = null;
    }

    @Override
    public CompoundNBT write(CompoundNBT compound) {
        updateElement();
        if (!elements.isEmpty()) {
            ListNBT list = new ListNBT();
            for (BaseElement<?> element : elements) {
                CompoundNBT e = new CompoundNBT();
                e.putString("type", element.type);
                e.put("element", element.serializeNBT());
                list.add(e);
            }
            compound.put(NBT, list);
        }
        return super.write(compound);
    }

    @Override
    public void read(CompoundNBT compound) {
        super.read(compound);
        if (compound.contains(NBT)) {
            elements.clear();
            ListNBT list = compound.getList(NBT, Constants.NBT.TAG_COMPOUND);
            for (INBT iNbt : list) {
                CompoundNBT nbt = (CompoundNBT) iNbt;
                String type = nbt.getString("type");
                ElementType<?> typeObj = ElementType.TYPES.get(type);
                if (typeObj != null) {
                    BaseElement<?> e = typeObj.newInstance(null);
                    e.deserializeNBT(nbt.getCompound("element"));
                    elements.add(e);
                }
            }
        }
    }

    protected <T extends Comparable<T>> void setBlockState(IProperty<T> property, T value) {
        if (newBlockState == null) {
            newBlockState = getBlockState();
        }
        if (!Objects.equals(newBlockState.get(property), value)) {
            newBlockState = newBlockState.with(property, value);
        }
    }

    protected void updateElement() {
        if (!elementRemove.isEmpty()) {
            elements.removeAll(elementRemove);
            elementRemove.clear();
        }
        if (!elementAdd.isEmpty()) {
            elements.addAll(elementAdd);
            elementAdd.clear();
        }
    }

    protected boolean hasHandler(BlockPos pos, Capability<?> handler) {
        if (world == null || world.isRemote) {
            return false;
        }
        TileEntity tileEntity = world.getTileEntity(pos);
        return tileEntity != null && tileEntity.getCapability(handler).isPresent();
    }

    protected <T> BaseElement<T> sendElement(BaseElement<T> element, BlockPos container, boolean simulate, IPipelinePathHelper<T> helper) {
        List<Path<BlockPos, NodeInfo, PathInfo<T>>> paths = MathUtils.findWays(element, container, helper);
        ElementList<T> pathResult = helper.getPathResult(element, paths);
        if (!simulate) {
            pathResult.forEach(path -> addElement(element.copy(path.extra.stack, path.path, 1)));
        }
        return pathResult.surplusElement;
    }
}
