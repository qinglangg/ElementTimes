package com.elementtimes.tutorial.common.tileentity.base;

import com.elementtimes.tutorial.interface_.tileentity.ISlotProvider;
import com.elementtimes.tutorial.util.RedStoneEnergy;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author KSGFK create in 2019/3/9
 */
public abstract class TileMachine extends TileEntity implements ITickable, ISlotProvider {
    protected RedStoneEnergy storage;
    protected Map<ItemHandlerType, ItemStackHandler> mHandlers;
    protected Map<EnumFacing, ItemHandlerType> mHandlerType;
    protected EntityPlayerMP player;
    boolean isOpenGui;

    TileMachine(RedStoneEnergy storage, ItemStackHandler input, ItemStackHandler output) {
        this.storage = storage;
        mHandlers = new HashMap<>();
        mHandlerType = new HashMap<>();

        mHandlers.put(ItemHandlerType.INPUT, input);
        mHandlers.put(ItemHandlerType.OUTPUT, output);
        mHandlers.put(ItemHandlerType.NONE, new ItemStackHandler(0));

        for (EnumFacing facing : EnumFacing.values()) {
            if (facing == EnumFacing.DOWN) mHandlerType.put(facing, ItemHandlerType.OUTPUT);
            else mHandlerType.put(facing, ItemHandlerType.INPUT);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
        if (nbt.hasKey("items")) {
            // 旧版本兼容
            ItemStackHandler older = new ItemStackHandler();
            older.deserializeNBT(nbt.getCompoundTag("items"));
            if (mHandlers.get(ItemHandlerType.INPUT).getSlots() > 0)
                mHandlers.get(ItemHandlerType.INPUT).setStackInSlot(0, older.getStackInSlot(0));
            if (older.getSlots() > 1 && mHandlers.get(ItemHandlerType.OUTPUT).getSlots() > 0)
                mHandlers.get(ItemHandlerType.OUTPUT).setStackInSlot(1, older.getStackInSlot(1));
            nbt.removeTag("items");
        } else {
            if (nbt.hasKey("inputs"))
                mHandlers.get(ItemHandlerType.INPUT).deserializeNBT(nbt.getCompoundTag("input"));
            if (nbt.hasKey("outputs"))
                mHandlers.get(ItemHandlerType.OUTPUT).deserializeNBT(nbt.getCompoundTag("output"));
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        storage.writeToNBT(nbt);
        nbt.setTag("inputs", mHandlers.get(ItemHandlerType.INPUT).serializeNBT());
        nbt.setTag("outputs", mHandlers.get(ItemHandlerType.OUTPUT).serializeNBT());
        return super.writeToNBT(nbt);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 0, this.serializeNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.deserializeNBT(pkt.getNbtCompound());
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            markDirty();
            logic();
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 2);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY
                || (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && hasItemHandler(facing))
                || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return capability.cast((T) mHandlers.get(mHandlerType.get(facing)));
        return CapabilityEnergy.ENERGY.cast(storage);
    }

    @Deprecated
    public void setPlayer(EntityPlayerMP player) {
        this.player = player;
    }

    @Deprecated
    public void setOpenGui(boolean openGui) {
        isOpenGui = openGui;
    }

    private boolean hasItemHandler(EnumFacing facing) {
        ItemHandlerType type = mHandlerType.get(facing);
        return mHandlers.get(type).getSlots() > 0;
    }

    /**
     * 会在update阶段调用
     */
    abstract void logic();

    public static enum ItemHandlerType {
        INPUT, OUTPUT, NONE
    }
}
