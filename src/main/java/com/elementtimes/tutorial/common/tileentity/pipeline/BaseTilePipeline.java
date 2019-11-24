package com.elementtimes.tutorial.common.tileentity.pipeline;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.tutorial.common.block.pipeline.BaseElement;
import com.elementtimes.tutorial.common.block.pipeline.ElementType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.texture.ITickable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nullable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static com.elementtimes.tutorial.common.block.pipeline.Pipeline.CONNECTED_PROPERTIES;

/**
 * 管道 TileEntity 类
 * @author luqin2007
 */
@SuppressWarnings({"DuplicatedCode", "UnusedReturnValue", "WeakerAccess"})
public abstract class BaseTilePipeline extends TileEntity implements ITickable {

    private static final String NBT = "_pipeline_elements_";
    private static final String DATA = "_pipeline_data_";

    private List<BaseElement> elements = new LinkedList<>();
    private List<BaseElement> elementAdd = new LinkedList<>();

    private int data = 0b000000000000;

    /**
     * 当某一方向方块发生变化时调用
     * @param neighbor 位置
     */
    public void onNeighborChanged(BlockPos neighbor) {
        if (world != null) {
            EnumFacing direction = ECUtils.block.getPosFacing(this.pos, neighbor);
            if (isConnected(direction)) {
                if (!canConnectTo(direction)) {
                    disconnectInternal(direction);
                }
            } else {
                connect(direction);
            }
        }
    }

    /**
     * 管道被移除时调用
     */
    public void onRemoved(IBlockState state) {
        Iterator<BaseElement> iterator = elements.iterator();
        while (iterator.hasNext()) {
            iterator.next().drop(world, pos);
            iterator.remove();
        }
    }

    public void onPlace(ItemStack stack, EntityLivingBase placer) {
        for (EnumFacing facing : EnumFacing.values()) {
            connect(facing);
        }
    }

    /**
     * 某一方向是否可以连接
     * @param direction 方向
     * @return 可连接
     */
    public abstract boolean canConnectTo(EnumFacing direction);

    /**
     * 某一方向是否可以连接
     * 该方法主要是用于需要验证相邻管道是否可以连接，防止双方互相调用
     * @param pos 另一位置
     * @param direction 方向
     * @return 可连接
     */
    protected abstract boolean canConnectBy(BlockPos pos, EnumFacing direction);

    /**
     * 尝试连接某一方向
     * @param direction 方向
     * @return 是否成功连接
     */
    public boolean connect(EnumFacing direction) {
        if (world != null && !isConnected(direction) && canConnectTo(direction)) {
            connectInternal(direction);
            return true;
        }
        return false;
    }

    /**
     * 某位置是否已连接
     * @param pos 位置
     * @return 连接
     */
    public boolean isConnected(BlockPos pos) {
        if (world != null) {
            EnumFacing facing = ECUtils.block.getPosFacing(this.pos, pos);
            if (facing != null) {
                return isConnected(facing);
            }
        }
        return false;
    }

    /**
     * 从某一位置断开连接
     * @param pos 位置
     */
    public void disconnect(BlockPos pos) {
        EnumFacing direction = ECUtils.block.getPosFacing(this.pos, pos);
        if (world != null && direction != null && isConnected(direction)) {
            disconnectInternal(direction);
        }
    }

    /**
     * 某一方向是否可以输出物品
     * @param pos 位置
     * @param element 物品
     * @return 是否可以输出
     */
    public abstract boolean canOutput(BlockPos pos, BaseElement element);

    /**
     * 是否可向某方向发送物品
     * @param pos 位置
     * @param element 物品
     * @return 是否可发送
     */
    public boolean canSend(BlockPos pos, BaseElement element) {
        if (world != null && isConnected(pos)) {
            TileEntity te = world.getTileEntity(pos);
            if (te instanceof BaseTilePipeline) {
                return ((BaseTilePipeline) te).canReceive(pos, element);
            }
        }
        return false;
    }

    /**
     * 是否可接收某方向的物品
     * @param pos 位置
     * @param element 物品
     * @return 某方向物品
     */
    public abstract boolean canReceive(BlockPos pos, BaseElement element);

    /**
     * 向某方向发送物品
     * @param pos 发送的位置
     * @param element 物品
     * @return 如果不能完全发送，则返回未能发送的物品
     */
    public BaseElement send(BlockPos pos, BaseElement element) {
        if (world != null) {
            if (canSend(pos, element)) {
                element.remove(pos);
                BaseTilePipeline pipeline = (BaseTilePipeline) world.getTileEntity(pos);
                if (pipeline != null) {
                    return pipeline.receive(pos, element);
                }
            }
        }
        return element;
    }

    /**
     * 从某方向接收物品
     * @param pos 位置
     * @param element 物品
     * @return 如果不能完全接收，则返回未能接受的物品
     */
    protected BaseElement receive(BlockPos pos, BaseElement element) {
        if (canReceive(pos, element)) {
            element.remove(pos);
            addElement(element);
            return null;
        } else {
            return element;
        }
    }

    /**
     * 将物品输出到某个方向
     * @param pos 位置
     * @param element 物品
     * @return 返回剩余方向未能完全接收的物品
     */
    public abstract BaseElement output(BlockPos pos, BaseElement element);

    /**
     * 获取某物品在当前管道中停留的时间
     * @param element 传输物品
     * @return 停留时间(tick)
     */
    public abstract int getKeepTime(BaseElement element);

    public void addElement(BaseElement element) {
        elementAdd.add(element);
    }

    @Override
    public void tick() {
        boolean needSave = !(elementAdd.isEmpty() && elements.isEmpty());
        this.elements.addAll(elementAdd);
        this.elementAdd.clear();
        Iterator<BaseElement> iterator = elements.iterator();
        while (iterator.hasNext()) {
            BaseElement element = iterator.next();
            element.onTick(world);
            if (element.isRemoved(pos)) {
                iterator.remove();
            }
        }
        if (needSave) {
            markDirty();
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        elements.addAll(elementAdd);
        elementAdd.clear();
        if (!elements.isEmpty()) {
            NBTTagList list = new NBTTagList();
            for (BaseElement element : elements) {
                if (!element.isRemoved(pos)) {
                    NBTTagCompound e = new NBTTagCompound();
                    e.setString("type", element.type);
                    e.setTag("element", element.serializeNBT());
                    list.appendTag(e);
                } else {
                    element.remove(pos);
                }
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

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        this.data = pkt.getNbtCompound().getInteger("data");
    }

    public boolean isConnected(EnumFacing direction) {
        return ECUtils.math.fromByte(data, direction.getIndex());
    }

    public boolean isInterrupted(EnumFacing direction) {
        return ECUtils.math.fromByte(data, direction.getIndex() + 6);
    }

    public void connectInternal(EnumFacing direction) {
        IBlockState state = world.getBlockState(pos);
        if (!world.isRemote && !isConnected(direction) && !isInterrupted(direction)) {
            data = ECUtils.math.setByte(data, direction.getIndex(), true);
            IBlockState newState = state.withProperty(CONNECTED_PROPERTIES[direction.getIndex()], true);
            ECUtils.block.setBlockState(world, pos, newState, world.getTileEntity(pos));
            markDirtyAndRender(state, newState);
        }
    }

    public void disconnectInternal(EnumFacing direction) {
        IBlockState state = world.getBlockState(pos);
        if (isConnected(direction)) {
            data = ECUtils.math.setByte(data, direction.getIndex(), false);
            IBlockState newState = state.withProperty(CONNECTED_PROPERTIES[direction.getIndex()], false);
            ECUtils.block.setBlockState(world, pos, newState, world.getTileEntity(pos));
            markDirtyAndRender(state, newState);
        }
    }

    public void interruptInternal(EnumFacing direction) {
        IBlockState state = world.getBlockState(pos);
        if (!isInterrupted(direction)) {
            data = ECUtils.math.setByte(data, direction.getIndex() + 6, true);
            if (isConnected(direction)) {
                data = ECUtils.math.setByte(data, direction.getIndex(), false);
                IBlockState newState = state.withProperty(CONNECTED_PROPERTIES[direction.getIndex()], false);
                ECUtils.block.setBlockState(world, pos, newState, world.getTileEntity(pos));
                markDirtyAndRender(state, newState);
            }
        }
    }

    public void clearInterruptInternal(EnumFacing direction) {
        if (isInterrupted(direction)) {
            data = ECUtils.math.setByte(data, direction.getIndex() + 6, false);
            connect(direction);
        }
        markDirty();
    }

    public void markDirtyAndRender(IBlockState oldState, IBlockState newState) {
        markDirty();
        world.notifyBlockUpdate(pos, oldState, newState, 3);
        world.markBlockRangeForRenderUpdate(pos, pos);
    }
}
