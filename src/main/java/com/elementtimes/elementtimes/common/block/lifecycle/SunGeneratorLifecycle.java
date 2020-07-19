package com.elementtimes.elementtimes.common.block.lifecycle;

import com.elementtimes.elementcore.api.capability.AccessibleEnergyHandler;
import com.elementtimes.elementcore.api.lifecycle.ILifecycle;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.config.Config;
import net.minecraft.world.World;



public class SunGeneratorLifecycle implements ILifecycle {

    protected BaseTileEntity mTile;
    protected boolean mRun;

    public SunGeneratorLifecycle(BaseTileEntity tile) {
        mTile = tile;
    }

    @Override
    public void onTickStart() {
        mRun = false;
    }

    @Override
    public boolean onCheckStart() {
        World world = mTile.getWorld();
        if (world == null || world.isRemote) {
            return false;
        }
        if (!world.isDaytime()) {
            return false;
        }
        AccessibleEnergyHandler handler = mTile.getEnergyHandler();
        return handler.getEnergyStored() < handler.getMaxEnergyStored();
    }

    @Override
    public boolean onLoop() {
        mTile.getEnergyHandler().receiveEnergyUncheck(getSpeed(), false);
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

    protected int getSpeed() {
        return Config.genSunSpeed.get();
    }
}
