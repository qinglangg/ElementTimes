package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.util.RedStoneEnergy;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;

/**
 * @author KSGFK create in 2019/3/9
 */
public abstract class TileMachine extends TileEntity implements ITickable {
    RedStoneEnergy storage;
    protected ItemStackHandler items;
    protected EntityPlayerMP player;
    boolean isOpenGui;

    TileMachine(RedStoneEnergy storage, ItemStackHandler items) {
        this.storage = storage;
        this.items = items;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        storage.readFromNBT(nbt);
        items.deserializeNBT(nbt.getCompoundTag("items"));
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        storage.writeToNBT(nbt);
        nbt.setTag("items", items.serializeNBT());
        super.writeToNBT(nbt);
        return nbt;
    }

    @Override
    public abstract void update();

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability.equals(CapabilityEnergy.ENERGY);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        return CapabilityEnergy.ENERGY.cast(storage);
    }

    public void setPlayer(EntityPlayerMP player) {
        this.player = player;
    }

    public void setOpenGui(boolean openGui) {
        isOpenGui = openGui;
    }

    public int getMaxEnergyStored(EnumFacing facing) {
        return storage.getMaxEnergyStored();
    }
}
