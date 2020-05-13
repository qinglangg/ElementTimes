package com.elementtimes.tutorial.common.tileentity.stand.capability;

import com.elementtimes.tutorial.common.tileentity.stand.TileSupportStand;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ModuleFluids implements IFluidHandler {

    protected final TileSupportStand mTileEntity;
    protected final EnumFacing mFacing;
    protected final List<ModuleCap<IFluidHandler>> mCapabilities;

    public ModuleFluids(TileSupportStand tss, EnumFacing facing) {
        mTileEntity = tss;
        mFacing = facing;
        mCapabilities = mTileEntity.getCapabilities(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, mFacing);
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        List<IFluidTankProperties> properties = new ArrayList<>();
        for (ModuleCap<IFluidHandler> cap : mCapabilities) {
            Collections.addAll(properties, cap.capability.getTankProperties());
        }
        return properties.toArray(new IFluidTankProperties[0]);
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount <= 0) {
            return 0;
        }
        for (ModuleCap<IFluidHandler> cap : mCapabilities) {
            int fill = cap.capability.fill(resource, doFill);
            if (fill > 0) {
                return fill;
            }
        }
        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (resource == null || resource.amount <= 0) {
            return null;
        }
        for (ModuleCap<IFluidHandler> cap : mCapabilities) {
            FluidStack drain = cap.capability.drain(resource, doDrain);
            if (drain != null) {
                return drain;
            }
        }
        return null;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (maxDrain <= 0) {
            return null;
        }
        for (ModuleCap<IFluidHandler> cap : mCapabilities) {
            FluidStack drain = cap.capability.drain(maxDrain, doDrain);
            if (drain != null) {
                return drain;
            }
        }
        return null;
    }
}
