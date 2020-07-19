package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Getter2;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 能量盒

 */
@ModTileEntity.Ter(@Getter2("com.elementtimes.elementtimes.client.block.EnergyBoxRender"))
@ModTileEntity(blocks = @Getter(value = Industry.class, name = "energyBox"))
public class TileEnergyBox extends TileEntity implements IEnergyStorage {

    private int mEnergyStored = 0;

    public TileEnergyBox(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

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
        return Config.storageEnergy.get();
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
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return LazyOptional.of(() -> (T) this);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void read(CompoundNBT compound) {
        mEnergyStored = compound.getInt("energy");
        super.read(compound);
    }

    @Override
    @Nonnull
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        CompoundNBT nbt = super.write(compound);
        nbt.putInt("energy", mEnergyStored);
        return nbt;
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    @Nonnull
    public CompoundNBT getUpdateTag() {
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("e", mEnergyStored);
        return compound;
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        mEnergyStored = tag.getInt("e");
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (world != null && !world.isRemote) {
            BlockState state = getBlockState();
            world.notifyBlockUpdate(pos, state, state, 1);
        }
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }
}
