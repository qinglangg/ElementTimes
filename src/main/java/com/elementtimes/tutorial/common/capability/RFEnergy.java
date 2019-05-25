package com.elementtimes.tutorial.common.capability;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * @author KSGFK create in 2019/3/9
 */
public class RFEnergy extends EnergyStorage implements INBTSerializable<NBTTagCompound> {

    public RFEnergy(int capacity) {
        super(capacity);
    }

    public RFEnergy(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public RFEnergy(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public RFEnergy(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("energy", energy);
        nbt.setInteger("capacity", capacity);
        nbt.setInteger("maxReceive", maxReceive);
        nbt.setInteger("maxExtract", maxExtract);
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("capacity")) {
            capacity = nbt.getInteger("capacity");
        }

        if (nbt.hasKey("energy")) {
            energy = nbt.getInteger("energy");
        }
        energy = Math.min(capacity, energy);

        if (nbt.hasKey("full") && nbt.getBoolean("full")) {
            energy = capacity;
        }

        if (nbt.hasKey("maxReceive")) {
            maxReceive = nbt.getInteger("maxReceive");
        }

        if (nbt.hasKey("maxExtract")) {
            maxExtract = nbt.getInteger("maxExtract");
        }
    }

    public class EnergyProxy implements IEnergyStorage {

        private int maxExtract;
        private int maxReceive;

        public EnergyProxy(int maxReceive, int maxExtract) {
            this.maxReceive = Math.min(maxReceive, RFEnergy.this.maxReceive);
            this.maxExtract = Math.min(maxReceive, RFEnergy.this.maxExtract);
        }

        public EnergyProxy(boolean canReceive, boolean canExtract) {
            this(canReceive ? RFEnergy.this.maxReceive : 0, canExtract ? RFEnergy.this.maxExtract : 0);
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            if (!canReceive() || maxReceive == 0) return 0;
            int r = RFEnergy.this.receiveEnergy(Math.min(maxReceive, this.maxReceive), simulate);
            return r == 0 ? 0 : maxReceive;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            if (!canExtract() || maxExtract == 0) return 0;
            int r = RFEnergy.this.extractEnergy(Math.min(maxExtract, this.maxExtract), simulate);
            return r == 0 ? 0 : maxExtract;
        }

        @Override
        public int getEnergyStored() {
            return RFEnergy.this.getEnergyStored();
        }

        @Override
        public int getMaxEnergyStored() {
            return RFEnergy.this.getMaxEnergyStored();
        }

        @Override
        public boolean canExtract() {
            return maxExtract > 0;
        }

        @Override
        public boolean canReceive() {
            return maxReceive > 0;
        }
    }
}
