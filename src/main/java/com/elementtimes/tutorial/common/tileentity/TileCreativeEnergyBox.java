package com.elementtimes.tutorial.common.tileentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nullable;

public class TileCreativeEnergyBox extends TileEntity implements ITickable {

    private IEnergyStorage mStorage = new IEnergyStorage() {
        @Override
        public int receiveEnergy(int maxReceive, boolean simulate) {
            return maxReceive;
        }

        @Override
        public int extractEnergy(int maxExtract, boolean simulate) {
            return maxExtract;
        }

        @Override
        public int getEnergyStored() {
            return Integer.MAX_VALUE;
        }

        @Override
        public int getMaxEnergyStored() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean canExtract() {
            return true;
        }

        @Override
        public boolean canReceive() {
            return true;
        }
    };

    @Override
    public void update() {
        if (!world.isRemote) {
            output(pos.up());
            output(pos.down());
            output(pos.east());
            output(pos.west());
            output(pos.north());
            output(pos.south());
        }
    }

    private void output(BlockPos pos) {
        TileEntity tileEntity = world.getTileEntity(pos);
        if (tileEntity != null && tileEntity.hasCapability(CapabilityEnergy.ENERGY, null)) {
            IEnergyStorage capability = tileEntity.getCapability(CapabilityEnergy.ENERGY, null);
            assert capability != null;
            capability.receiveEnergy(Integer.MAX_VALUE, false);
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(mStorage);
        }
        return super.getCapability(capability, facing);
    }
}
