package com.elementtimes.tutorial.other;

import com.elementtimes.elementcore.api.template.capability.fluid.ITankHandler;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IMachineLifecycle;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.TilePumpFluid;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.*;

import java.util.ArrayList;
import java.util.List;

public class FluidBlockRecipeLifecycle implements IMachineLifecycle {

    private TilePumpFluid mPump;

    private List<BlockPos> mPos = new ArrayList<>();
    private int mRadius = 0, mDepth = 0;
    private int mInterval = 0, mWorkPtr = 0;
    private boolean mNextPtr = false, mNextInterval = false;

    private FluidStack mFluidStack = null;

    public FluidBlockRecipeLifecycle(TilePumpFluid pump) {
        mPump = pump;
    }

    @Override
    public void onTickStart() {
        if (mRadius != getRadius() || mDepth != getDepth()) {
            mPos.clear();
            mRadius = getRadius();
            mDepth = getDepth();
            BlockPos pos = mPump.getPos();
            BlockPos.getAllInBox(pos.down().east(mRadius).north(mRadius), pos.down(mDepth).west(mRadius).south(mRadius)).forEach(mPos::add);
        }
    }

    @Override
    public boolean onCheckStart() {
        if (mInterval == 0) {
            mNextInterval = false;
            BlockPos pos = mPos.get(mWorkPtr);
            IBlockState fluidBlock = mPump.getWorld().getBlockState(pos);
            Block block = fluidBlock.getBlock();
            Fluid fluid = FluidRegistry.lookupFluidForBlock(block);
            if (fluid != null && isSource(fluidBlock, block, pos)) {
                mFluidStack = getFluidStack(fluid, block, pos);
                if (mPump.getTanks(SideHandlerType.OUTPUT).fillIgnoreCheck(0, mFluidStack, false) > 0) {
                    mPump.setEnergyProcessed(0);
                    mPump.setEnergyUnprocessed(getEnergyCost(mFluidStack));
                    replaceBlock(pos);
                    mNextPtr = false;
                    return true;
                }
            } else {
                mNextPtr = true;
            }
        } else {
            mWorkPtr = 0;
            mNextPtr = false;
            mNextInterval = true;
        }

        return false;
    }

    @Override
    public boolean onLoop() {
        int amount = mFluidStack.amount;
        if (amount == 0) {
            return true;
        }
        int cost = mPump.getEnergyHandler().extractEnergy(Math.max(mPump.getEnergyTick(), 1), false);
        if (cost == 0) {
            return false;
        }
        ITankHandler tanks = mPump.getTanks(SideHandlerType.OUTPUT);
        // fluid
        int transfer;
        int unprocessed = mPump.getEnergyUnprocessed();
        if (cost >= unprocessed) {
            transfer = amount;
        } else {
            transfer = Math.max(1, (int) ((float) amount * cost / unprocessed));
        }
        FluidStack stack = new FluidStack(mFluidStack, transfer);
        transfer = tanks.fillIgnoreCheck(0, stack, false);
        // energy
        int costFinal;
        if (transfer == amount) {
            costFinal = cost;
        } else if (transfer > 0) {
            costFinal = Math.max(1, (int) ((float) cost * transfer / amount));
        } else {
            costFinal = 0;
        }
        if (transfer == 0 || costFinal == 0) {
            return false;
        }
        // doFill
        tanks.fillIgnoreCheck(0, new FluidStack(mFluidStack, transfer), true);
        mPump.processEnergy(costFinal);
        mPump.getEnergyHandler().extractEnergy(costFinal, false);
        mFluidStack.amount -= transfer;
        return true;
    }

    @Override
    public boolean onCheckFinish() {
        return mFluidStack.amount <= 0;
    }

    @Override
    public boolean onFinish() {
        mNextPtr = false;
        mNextInterval = true;
        mInterval++;
        return true;
    }

    @Override
    public boolean onCheckResume() {
        return mPump.getEnergyHandler().getEnergyStored() > 0;
    }

    @Override
    public void onResume() {
        mNextPtr = false;
        mNextInterval = false;
    }

    @Override
    public void onTickFinish() {
        if (mNextInterval) {
            mInterval++;
            if (mInterval >= getInterval()) {
                mInterval = 0;
            }
            mNextInterval = false;
        }

        if (mNextPtr) {
            mWorkPtr++;
            mNextPtr = false;
        }
    }

    private boolean isSource(IBlockState state, Block block, BlockPos pos) {
        if (block instanceof BlockFluidClassic) {
            return ((BlockFluidClassic) block).isSourceBlock(mPump.getWorld(), pos);
        } else if (state.getPropertyKeys().contains(BlockFluidBase.LEVEL)) {
            return state.getValue(BlockFluidBase.LEVEL) == 0;
        }
        return true;
    }

    private FluidStack getFluidStack(Fluid fluid, Block block, BlockPos pos) {
        if (block instanceof BlockFluidBase) {
            return ((BlockFluidBase) block).drain(mPump.getWorld(), pos, false);
        } else {
            return new FluidStack(fluid, Fluid.BUCKET_VOLUME);
        }
    }

    private void replaceBlock(BlockPos pos) {
        mPump.getWorld().removeTileEntity(pos);
        mPump.getWorld().setBlockState(pos, ElementtimesBlocks.fr.getDefaultState(), 3);
    }

    public int getInterval() {
        return 20;
    }

    public int getRadius() {
        return 10;
    }

    public int getDepth() {
        return 20;
    }

    public int getEnergyCost(FluidStack stack) {
        return 10;
    }
}
