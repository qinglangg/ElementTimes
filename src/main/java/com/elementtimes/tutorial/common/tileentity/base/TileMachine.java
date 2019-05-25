package com.elementtimes.tutorial.common.tileentity.base;

import com.elementtimes.tutorial.common.capability.RFEnergy;
import com.elementtimes.tutorial.interface_.tileentity.ISlotProvider;
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
    protected RFEnergy mEnergyHandler;
    protected Map<SideHandlerType, ItemStackHandler> mItemHandlers = new HashMap<>();
    protected Map<EnumFacing, SideHandlerType> mItemHandlerTypes = new HashMap<>();
    protected Map<EnumFacing, SideHandlerType> mEnergyHandlerTypes = new HashMap<>();
    protected EntityPlayerMP player;
    protected boolean isOpenGui;

    TileMachine(int energyCapacity, int energyReceiver, int energyExtract, ItemStackHandler input, ItemStackHandler output) {
        mEnergyHandler = new RFEnergy(energyCapacity, energyReceiver, energyExtract);

        mItemHandlers.put(SideHandlerType.INPUT, input);
        mItemHandlers.put(SideHandlerType.OUTPUT, output);
        mItemHandlers.put(SideHandlerType.NONE, new ItemStackHandler(0));

        for (EnumFacing facing : EnumFacing.values()) {
            if (facing == EnumFacing.DOWN) mItemHandlerTypes.put(facing, SideHandlerType.OUTPUT);
            else mItemHandlerTypes.put(facing, SideHandlerType.INPUT);
            mEnergyHandlerTypes.put(facing, SideHandlerType.INPUT);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("items")) {
            // 旧版本兼容
            ItemStackHandler older = new ItemStackHandler();
            older.deserializeNBT(nbt.getCompoundTag("items"));
            if (mItemHandlers.get(SideHandlerType.INPUT).getSlots() > 0)
                mItemHandlers.get(SideHandlerType.INPUT).setStackInSlot(0, older.getStackInSlot(0));
            if (older.getSlots() > 1 && mItemHandlers.get(SideHandlerType.OUTPUT).getSlots() > 0)
                mItemHandlers.get(SideHandlerType.OUTPUT).setStackInSlot(1, older.getStackInSlot(1));
            nbt.removeTag("items");
        }
        if (nbt.hasKey("inputs"))
            mItemHandlers.get(SideHandlerType.INPUT).deserializeNBT(nbt.getCompoundTag("inputs"));
        if (nbt.hasKey("outputs"))
            mItemHandlers.get(SideHandlerType.OUTPUT).deserializeNBT(nbt.getCompoundTag("outputs"));
        if (nbt.hasKey("energy"))
            mEnergyHandler.deserializeNBT(nbt.getCompoundTag("energy"));
        super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag("inputs", mItemHandlers.get(SideHandlerType.INPUT).serializeNBT());
        nbt.setTag("outputs", mItemHandlers.get(SideHandlerType.OUTPUT).serializeNBT());
        nbt.setTag("energy", mEnergyHandler.serializeNBT());
        return nbt;
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
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            logic();
            markDirty(); // 咱们这么滥用 markDirty 真的没问题吗
            IBlockState state = world.getBlockState(pos);
            IBlockState newState = updateState(state);
            world.notifyBlockUpdate(pos, state, newState, 2);
            if (state != newState) {
                world.markBlockRangeForRenderUpdate(pos, pos);
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return (capability == CapabilityEnergy.ENERGY && mEnergyHandlerTypes.get(facing) != SideHandlerType.NONE)
                || (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && hasItemHandler(facing))
                || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return capability.cast((T) mItemHandlers.get(mItemHandlerTypes.get(facing)));
        if (capability == CapabilityEnergy.ENERGY)
            return capability.cast((T) getEnergyProxy(facing));
        return super.getCapability(capability, facing);
    }

    protected RFEnergy.EnergyProxy getEnergyProxy(EnumFacing facing) {
        SideHandlerType type = mEnergyHandlerTypes.get(facing);
        switch (type) {
            case NONE: mEnergyHandler.new EnergyProxy(false, false);
            case IN_OUT: return mEnergyHandler.new EnergyProxy(true, true);
            case OUTPUT: return mEnergyHandler.new EnergyProxy(false, true);
            default: return mEnergyHandler.new EnergyProxy(true, false);
        }
    }

    public RFEnergy.EnergyProxy getReadonlyEnergyProxy() {
        return mEnergyHandler.new EnergyProxy(false, false);
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
        SideHandlerType type = mItemHandlerTypes.get(facing);
        return mItemHandlers.get(type).getSlots() > 0;
    }

    /**
     * 会在update阶段调用
     */
    abstract void logic();

    protected IBlockState updateState(IBlockState old) { return old; }

    public enum SideHandlerType {
        INPUT, // 物品：输入槽；能量：可输入
        OUTPUT, // 物品：输出槽；能量：可输出
        NONE, // 物品&能量：不可输入/输出
        IN_OUT; // 物品&能量：可输入/输出
    }
}
