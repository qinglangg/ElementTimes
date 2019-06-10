package com.elementtimes.tutorial.common.capability.impl;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

/**
 * 自定义实现的 EnergyStorage
 * 主要自定义实现了 NBT 序列化/反序列化，添加一个代理类
 *
 * @author KSGFK create in 2019/3/9
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public class RfEnergy extends EnergyStorage implements INBTSerializable<NBTTagCompound> {

    private int transfer = Integer.MAX_VALUE;

    public RfEnergy(int capacity) {
        super(capacity);
    }

    public RfEnergy(int capacity, int maxTransfer) {
        super(capacity, maxTransfer);
    }

    public RfEnergy(int capacity, int maxReceive, int maxExtract) {
        super(capacity, maxReceive, maxExtract);
    }

    public RfEnergy(int capacity, int maxReceive, int maxExtract, int energy) {
        super(capacity, maxReceive, maxExtract, energy);
    }

    public void setTransfer(int transfer) {
        this.transfer = transfer;
    }

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = new NBTTagCompound();
        nbt.setInteger("energy", energy);
        nbt.setInteger("transfer", transfer);
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

        if (nbt.hasKey("transfer")) {
            transfer = nbt.getInteger("transfer");
        }
    }

    public class EnergyProxy implements IEnergyStorage {

        private int maxExtract;
        private int maxReceive;

        public EnergyProxy(int maxReceive, int maxExtract) {
            this.maxReceive = Math.min(Math.min(maxReceive, transfer), RfEnergy.this.maxReceive);
            this.maxExtract = Math.min(Math.min(maxExtract, transfer), RfEnergy.this.maxExtract);
        }

        public EnergyProxy(boolean canReceive, boolean canExtract) {
            this(canReceive ? RfEnergy.this.maxReceive : 0, canExtract ? RfEnergy.this.maxExtract : 0);
        }

        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            if (!canReceive() || maxReceive == 0) {
                return 0;
            }
            int r = RfEnergy.this.receiveEnergy(Math.min(maxReceive, this.maxReceive), simulate);
            return r == 0 ? 0 : maxReceive;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            if (!canExtract() || maxExtract == 0) {
                return 0;
            }
            int r = RfEnergy.this.extractEnergy(Math.min(maxExtract, this.maxExtract), simulate);
            return r == 0 ? 0 : maxExtract;
        }

        @Override
        public int getEnergyStored() {
            return RfEnergy.this.getEnergyStored();
        }

        @Override
        public int getMaxEnergyStored() {
            return RfEnergy.this.getMaxEnergyStored();
        }

        @Override
        public boolean canExtract() {
            return maxExtract > 0;
        }

        @Override
        public boolean canReceive() {
            return maxReceive > 0;
        }

        public void setTransfer(int transfer) {
            maxReceive = transfer;
            maxExtract = transfer;
        }
    }
}
