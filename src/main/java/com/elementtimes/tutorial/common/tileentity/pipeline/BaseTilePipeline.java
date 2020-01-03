package com.elementtimes.tutorial.common.tileentity.pipeline;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.tutorial.common.pipeline.BaseElement;
import com.elementtimes.tutorial.common.pipeline.ElementType;
import com.elementtimes.tutorial.common.pipeline.test.PLTestNetwork;
import com.elementtimes.tutorial.interfaces.IByteData;
import com.elementtimes.tutorial.interfaces.ITilePipeline;
import com.elementtimes.tutorial.interfaces.ITilePipelineInput;
import com.elementtimes.tutorial.interfaces.ITilePipelineOutput;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
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
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

import static com.elementtimes.tutorial.common.block.pipeline.Pipeline.CONNECTED_PROPERTIES;

/**
 * 管道 TileEntity 类
 * @author luqin2007
 */
@SuppressWarnings({"DuplicatedCode", "UnusedReturnValue", "WeakerAccess"})
public abstract class BaseTilePipeline extends TileEntity implements ITilePipeline, ITickable, IByteData {

    private static final String NBT = "_pipeline_elements_";
    private static final String DATA = "_pipeline_data_";

    private List<BaseElement> elements = new LinkedList<>();
    private List<BaseElement> elementAdd = new LinkedList<>();
    private List<BaseElement> elementRemove = new LinkedList<>();

    /**
     *  0-5 ：connected
     *  6-11：interrupted
     * 12-15：io(PipelineIO)
     */
    private int data = 0;
    private IBlockState newBlockState = null;

    @Override
    public void onNeighborChanged(BlockPos neighbor) {
        if (world != null && !world.isRemote) {
            EnumFacing direction = ECUtils.block.getPosFacing(pos, neighbor);
            if (isConnected(neighbor, direction)) {
                if (!canConnectTo(neighbor, direction)) {
                    disconnect(neighbor, direction);
                }
            } else {
                connect(neighbor, direction);
            }
        }
    }

    @Override
    public void onPlace(ItemStack stack, EntityLivingBase placer) {
        if (world != null && !world.isRemote) {
            for (EnumFacing direction : EnumFacing.VALUES) {
                BlockPos pos = this.pos.offset(direction);
                if (!isConnected(pos, direction) && canConnectTo(pos, direction)) {
                    if (writeByteValue(direction.getIndex())) {
                        setBlockState(state -> state.withProperty(CONNECTED_PROPERTIES[direction.getIndex()], true));
                    }
                }
            }
        }
    }

    @Override
    public void onRemoved(IBlockState state) {
        if (world != null && !world.isRemote) {
            updateElement();
            Iterator<BaseElement> iterator = elements.iterator();
            while (iterator.hasNext()) {
                iterator.next().drop(world, pos);
                iterator.remove();
            }
        }
    }

    @Override
    public boolean isConnected(BlockPos pos, EnumFacing direction) {
        return direction != null && readByteValue(direction.getIndex());
    }

    @Override
    public boolean canPost(BlockPos pos, EnumFacing direction, BaseElement element) {
        if (world != null && isConnected(pos, direction)) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof ITilePipeline) {
                return ((ITilePipeline) te).canReceive(pos, element);
            }
        }
        return false;
    }

    @Override
    public BaseElement post(BlockPos pos, EnumFacing direction, BaseElement element) {
        if (world != null && !world.isRemote) {
            removeElement(element);
            if (canPost(pos, direction, element)) {
                ITilePipeline pipeline = (ITilePipeline) world.getTileEntity(pos);
                if (pipeline != null) {
                    PLTestNetwork.sendMessage(PLTestNetwork.TestElementType.post, element, pos);
                    return pipeline.onReceive(pos, element);
                }
            }
            return element;
        }
        return null;
    }

    @Override
    public BaseElement onReceive(BlockPos pos, BaseElement element) {
        if (canReceive(pos, element)) {
            addElement(element);
            PLTestNetwork.sendMessage(PLTestNetwork.TestElementType.receive, element, pos);
            return null;
        } else {
            return element;
        }
    }

    @Override
    public void removeElement(BaseElement element) {
        elementRemove.add(element);
    }

    @Override
    public void addElement(BaseElement element) {
        elementAdd.add(element);
    }

    @Override
    public void dropElement(BaseElement element) {
        if (world != null && !world.isRemote) {
            element.drop(world, pos);
            removeElement(element);
            PLTestNetwork.sendMessage(PLTestNetwork.TestElementType.drop, element, pos);
        }
    }

    @Override
    public boolean isInterrupted(BlockPos pos, EnumFacing direction) {
        return direction != null && readByteValue(direction.getIndex() + 6);
    }

    @Override
    public void setInterrupt(BlockPos pos, EnumFacing direction) {
        writeByteValue(direction.getIndex() + 6);
        if (isConnected(pos, direction) && clearByteValue(direction.getIndex())) {
            data = ECUtils.math.setByte(data, direction.getIndex(), false);
            setBlockState(state -> state.withProperty(CONNECTED_PROPERTIES[direction.getIndex()], false));
        }
    }

    @Override
    public void update() {
        if (world != null && !world.isRemote) {
            boolean needSave = !(elementRemove.isEmpty() && elementAdd.isEmpty() && elements.isEmpty());
            updateElement();
            // tick
            elements.forEach(BaseElement::tickIncrease);
            // output
            if (this instanceof ITilePipelineOutput) {
                for (BaseElement element : elements) {
                    if (element.isFinalPos() && element.tick >= getKeepTime(element)) {
                        removeElement(element);
                        BaseElement back = ((ITilePipelineOutput) this).output(element);
                        if (back != null && !back.isEmpty()) {
                            if (!back.send(world, this, element.path.get(0))) {
                                dropElement(back);
                            }
                        }
                    }
                }
                updateElement();
            }
            // post
            for (BaseElement element : elements) {
                if (!element.isEmpty() && element.tick >= getKeepTime(element)) {
                    BlockPos nextPos = element.nextPos();
                    if (nextPos == null) {
                        element.drop(world, pos);
                    } else {
                        TileEntity te = world.getTileEntity(nextPos);
                        if (te instanceof ITilePipeline && ((ITilePipeline) te).canReceive(pos, element)) {
                            removeElement(element);
                            BaseElement back = post(nextPos, ECUtils.block.getPosFacing(pos, nextPos), element);
                            if (back != null && !back.isEmpty()) {
                                if (!back.send(world, this, element.path.get(0))) {
                                    dropElement(back);
                                }
                            }
                        }
                    }
                }
            }
            // input
            if (this instanceof ITilePipelineInput) {
                ((ITilePipelineInput) this).input();
            }
            updateElement();
            // save
            if (needSave) {
                markDirty();
            }
            // state
            if (newBlockState != null) {
                IBlockState state = world.getBlockState(pos);
                if (state != newBlockState) {
                    world.notifyBlockUpdate(pos, state, newBlockState, 3);
                    ECUtils.block.setBlockState(world, pos, newBlockState, this);
                    world.markBlockRangeForRenderUpdate(pos, pos);
                }
            }
            newBlockState = null;
        }
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        updateElement();
        if (!elements.isEmpty()) {
            NBTTagList list = new NBTTagList();
            for (BaseElement element : elements) {
                NBTTagCompound e = new NBTTagCompound();
                e.setString("type", element.type);
                e.setTag("element", element.serializeNBT());
                list.appendTag(e);
            }
            compound.setTag(NBT, list);
        }
        compound.setInteger(DATA, data);
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        if (compound.hasKey(NBT)) {
            NBTTagList list = compound.getTagList(NBT, Constants.NBT.TAG_COMPOUND);
            elements.clear();
            for (NBTBase inbt : list) {
                NBTTagCompound nbt = (NBTTagCompound) inbt;
                String type = nbt.getString("type");
                ElementType typeObj = ElementType.TYPES.get(type);
                if (typeObj != null) {
                    BaseElement e = typeObj.newInstance();
                    e.deserializeNBT(nbt.getCompoundTag("element"));
                    elements.add(e);
                }
            }
        }
        data = compound.getInteger(DATA);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("data", data);
        return new SPacketUpdateTileEntity(pos, 1, nbt);
    }

    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.data = pkt.getNbtCompound().getInteger("data");
    }

    @Override
    public int getByteData() {
        return data;
    }

    @Override
    public void setByteData(int data) {
        this.data = data;
        markDirty();
    }

    protected void setBlockState(Function<IBlockState, IBlockState> blockStateEditor) {
        if (newBlockState == null) {
            newBlockState = world.getBlockState(pos);
        }
        newBlockState = blockStateEditor.apply(newBlockState);
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
}
