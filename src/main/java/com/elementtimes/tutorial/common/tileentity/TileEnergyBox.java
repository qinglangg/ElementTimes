package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.config.ETConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

/**
 * 能量盒
 * @author luqin2007
 */
public class TileEnergyBox extends TileEntity implements IEnergyStorage {

    private int mEnergyStored = 0;

    @Override
    public int receiveEnergy(int maxReceive, boolean simulate) {
        if (maxReceive == 0 || !canReceive()) {
            return 0;
        }
        int space = getMaxEnergyStored() - getEnergyStored();
        if (space == 0) {
            return 0;
        }
        int received = Math.min(space, maxReceive);
        if (!simulate) {
            addEnergy(received);
        }
        return received;
    }

    @Override
    public int extractEnergy(int maxExtract, boolean simulate) {
        int energy = getEnergyStored();
        if (maxExtract == 0 || !canExtract() || energy == 0) {
            return 0;
        }
        int extracted = Math.min(maxExtract, energy);
        if (!simulate) {
            reduceEnergy(extracted);
        }
        return extracted;
    }

    @Override
    public int getEnergyStored() {
        if (mEnergyStored < 0) {
            setEnergy(0);
            markDirty();
        }
        int capacity = getMaxEnergyStored();
        if (mEnergyStored > capacity) {
            setEnergy(capacity);
            markDirty();
        }
        return mEnergyStored;
    }

    @Override
    public int getMaxEnergyStored() {
        return ETConfig.STORAGE.energy;
    }

    @Override
    public boolean canExtract() {
        return true;
    }

    @Override
    public boolean canReceive() {
        return true;
    }

    private void setEnergy(int energy) {
        if (mEnergyStored != energy) {
            mEnergyStored = energy;
            markDirty();
        }
    }

    private void addEnergy(int energy) {
        if (energy != 0) {
            mEnergyStored += energy;
            markDirty();
        }
    }

    private void reduceEnergy(int energy) {
        if (energy != 0) {
            mEnergyStored -= energy;
            markDirty();
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(this);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        mEnergyStored = compound.getInteger("energy");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("energy", mEnergyStored);
        return super.writeToNBT(compound);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, write());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        read(pkt.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    private NBTTagCompound write() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("e", mEnergyStored);
        return compound;
    }

    private void read(NBTTagCompound compound) {
        mEnergyStored = compound.getInteger("e");
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (world != null && !world.isRemote) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 1);
        }
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }
}
