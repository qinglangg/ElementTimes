package com.elementtimes.elementtimes.common.block.lifecycle;

import com.elementtimes.elementcore.api.lifecycle.ILifecycle;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.fluid.Sources;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;



public class PumpAirLifecycle implements ILifecycle {

    protected BaseTileEntity mTile;
    protected boolean mRun;

    public PumpAirLifecycle(BaseTileEntity tile) {
        mTile = tile;
    }

    @Override
    public void onTickStart() {
        mRun = false;
    }

    @Override
    public boolean onCheckStart() {
        int energy = getEnergy();
        if (mTile.getEnergyHandler().extractEnergyUncheck(energy, true) == energy) {
            return mTile.getFluidHandler().fillUncheck(new FluidStack(Sources.air, getOutput()), IFluidHandler.FluidAction.SIMULATE) > 0;
        }
        return false;
    }

    @Override
    public void onStart() {
        mTile.getEnergyHandler().extractEnergyUncheck(getEnergy(), false);
    }

    @Override
    public boolean onLoop() {
        mTile.getFluidHandler().fillUncheck(new FluidStack(Sources.air, getOutput()), IFluidHandler.FluidAction.EXECUTE);
        mRun = true;
        return true;
    }

    @Override
    public boolean needSave() {
        return mRun;
    }

    @Override
    public boolean needSync() {
        return mRun;
    }

    protected int getEnergy() {
        return 10;
    }

    protected int getOutput() {
        return 10;
    }
}
