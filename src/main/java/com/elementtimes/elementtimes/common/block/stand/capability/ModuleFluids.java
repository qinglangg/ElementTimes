package com.elementtimes.elementtimes.common.block.stand.capability;

import com.elementtimes.elementtimes.common.block.stand.te.TileSupportStand;
import net.minecraft.util.Direction;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import java.util.List;



public class ModuleFluids implements IFluidHandler {

    protected final TileSupportStand mTileEntity;
    protected final Direction mFacing;
    protected final List<ModuleCap<IFluidHandler>> mCapabilities;

    public ModuleFluids(TileSupportStand tss, Direction facing) {
        mTileEntity = tss;
        mFacing = facing;
        mCapabilities = mTileEntity.getCapabilities(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, mFacing);
    }

    @Override
    public int getTanks() {
        return mCapabilities.stream().map(c -> c.capability).mapToInt(c -> c.getTanks()).sum();
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        for (ModuleCap<IFluidHandler> mc : mCapabilities) {
            IFluidHandler capability = mc.capability;
            int tanks = capability.getTanks();
            if (tanks <= tank) {
                tank -= tanks;
            } else {
                return capability.getFluidInTank(tank);
            }
        }
        return FluidStack.EMPTY;
    }

    @Override
    public int getTankCapacity(int tank) {
        for (ModuleCap<IFluidHandler> mc : mCapabilities) {
            IFluidHandler capability = mc.capability;
            int tanks = capability.getTanks();
            if (tanks <= tank) {
                tank -= tanks;
            } else {
                return capability.getTankCapacity(tank);
            }
        }
        return 0;
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        for (ModuleCap<IFluidHandler> mc : mCapabilities) {
            IFluidHandler capability = mc.capability;
            int tanks = capability.getTanks();
            if (tanks <= tank) {
                tank -= tanks;
            } else {
                return capability.isFluidValid(tank, stack);
            }
        }
        return false;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource == null || resource.getAmount() <= 0) {
            return 0;
        }
        for (ModuleCap<IFluidHandler> cap : mCapabilities) {
            int fill = cap.capability.fill(resource, action);
            if (fill > 0) {
                return fill;
            }
        }
        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (resource == null || resource.getAmount() <= 0) {
            return FluidStack.EMPTY;
        }
        for (ModuleCap<IFluidHandler> cap : mCapabilities) {
            FluidStack drain = cap.capability.drain(resource, action);
            if (drain != FluidStack.EMPTY) {
                return drain;
            }
        }
        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (maxDrain <= 0) {
            return FluidStack.EMPTY;
        }
        for (ModuleCap<IFluidHandler> cap : mCapabilities) {
            FluidStack drain = cap.capability.drain(maxDrain, action);
            if (drain != FluidStack.EMPTY) {
                return drain;
            }
        }
        return FluidStack.EMPTY;
    }
}
