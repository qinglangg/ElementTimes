package com.elementtimes.tutorial.util;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.energy.EnergyStorage;

/**
 * @author KSGFK create in 2019/3/9
 */
public class RedStoneEnergy extends EnergyStorage {
    public RedStoneEnergy(int capacity) {
        super(capacity);
    }

    public RedStoneEnergy(int capacity, int maxTransfer) {
        this(capacity, maxTransfer, maxTransfer);
    }

    public RedStoneEnergy(int capacity, int maxReceive, int maxExtract) {
        super(capacity);
        this.capacity = capacity;
        this.maxReceive = maxReceive;
        this.maxExtract = maxExtract;
    }

    public EnergyStorage readFromNBT(NBTTagCompound nbt) {
        this.energy = nbt.getInteger("Energy");
        if (energy > capacity) {
            energy = capacity;
        }
        return this;
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        if (energy < 0) {
            energy = 0;
        }
        nbt.setInteger("Energy", energy);
        return nbt;
    }

    public EnergyStorage setCapacity(int capacity) {
        this.capacity = capacity;
        if (energy > capacity) {
            energy = capacity;
        }
        return this;
    }

    public EnergyStorage setMaxTransfer(int maxTransfer) {
        setMaxReceive(maxTransfer);
        setMaxExtract(maxTransfer);
        return this;
    }

    public EnergyStorage setMaxReceive(int maxReceive) {
        this.maxReceive = maxReceive;
        return this;
    }

    public EnergyStorage setMaxExtract(int maxExtract) {
        this.maxExtract = maxExtract;
        return this;
    }

    public int getMaxReceive() {
        return maxReceive;
    }

    public int getMaxExtract() {
        return maxExtract;
    }

    public void setEnergyStored(int energy) {

        this.energy = energy;

        if (this.energy > capacity) {
            this.energy = capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
    }

    public void modifyEnergyStored(int energy) {
        this.energy += energy;
        if (this.energy > capacity) {
            this.energy = capacity;
        } else if (this.energy < 0) {
            this.energy = 0;
        }
    }
}
