package com.elementtimes.tutorial.common.tileentity;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.FluidTankProperties;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;

public class TileCreativeFluidTank extends TileEntity implements IFluidHandler {

    private Fluid mFluid = FluidRegistry.WATER;

    public Fluid getFluid() {
        if (mFluid == null) {
            setFluid(FluidRegistry.WATER);
        }
        return mFluid;
    }

    public void setFluid(Fluid fluid) {
        if (fluid == null) {
            fluid = FluidRegistry.WATER;
        }
        if (mFluid != fluid) {
            mFluid = fluid;
            markDirty();
        }
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[] {
                new FluidTankProperties(new FluidStack(getFluid(), Integer.MAX_VALUE), Integer.MAX_VALUE, true, true)
        };
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        setFluid(resource.getFluid());
        return resource.amount;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        return resource.copy();
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        return new FluidStack(getFluid(), maxDrain);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        setFluid(FluidRegistry.getFluid(compound.getString("fluid")));
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setString("fluid", getFluid().getName());
        return super.writeToNBT(compound);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return true;
        }
        return super.hasCapability(capability, facing);
    }
}
