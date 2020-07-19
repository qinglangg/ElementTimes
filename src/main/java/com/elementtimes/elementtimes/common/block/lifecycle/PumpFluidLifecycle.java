package com.elementtimes.elementtimes.common.block.lifecycle;

import com.elementtimes.elementcore.api.capability.AccessibleEnergyHandler;
import com.elementtimes.elementcore.api.lifecycle.BaseReplaceLifecycle;
import com.elementtimes.elementtimes.common.block.machine.TilePumpFluid;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.IBucketPickupHandler;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.fluid.IFluidState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.IFluidHandler;

import java.util.ArrayList;
import java.util.List;



public class PumpFluidLifecycle extends BaseReplaceLifecycle<BlockPos, FluidStack> {

    protected NonNullList<BlockPos> mPos = NonNullList.create();
    protected int mRadius, mDepth, mSize, mListPtr;
    protected TilePumpFluid mPump;

    public PumpFluidLifecycle(TilePumpFluid pump) {
        mPump = pump;
    }

    @Override
    public void onTickStart() {
        super.onTickStart();
        if (mPos.isEmpty() || mRadius != getRadius() || mDepth != getDepth()) {
            mRadius = getRadius();
            mDepth = getDepth();
            BlockPos origin = mPump.getPos();
            BlockPos start = origin.down().east(mRadius).north(mRadius);
            BlockPos end = origin.down(mDepth).west(mRadius).south(mRadius);
            mSize = mRadius * mRadius * mDepth;
            mPos = NonNullList.withSize(mSize, start);
            mListPtr = 0;
            BlockPos.getAllInBox(start, end).forEachOrdered(pos -> mPos.set(mListPtr++, pos));
            mListPtr = 0;
        }
    }

    @Override
    public void findElements(List<BlockPos> list) {
        World world = mPump.getWorld();
        if (world == null) {
            return;
        }
        int maxCache = getMaxCache();
        if (list.size() >= maxCache) {
            return;
        }
        if (mListPtr >= mSize) {
            mListPtr = 0;
        }
        int find = Math.min(mListPtr + getMaxFind(), mSize);
        while (mListPtr < find && list.size() >= maxCache) {
            BlockPos pos = mPos.get(mListPtr++);
            BlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (block instanceof IBucketPickupHandler) {
                IFluidState fluidState = state.getFluidState();
                if (fluidState.isSource()) {
                    Fluid fluid = fluidState.getFluid();
                    if (fluid != Fluids.EMPTY) {
                        list.add(pos);
                    }
                }
            }
        }
    }

    @Override
    public boolean canFind() {
        return mPump.hasWorld() && !mPos.isEmpty();
    }

    @Override
    public FluidStack convert(BlockPos pos) {
        World world = mPump.getWorld();
        if (world == null) {
            return FluidStack.EMPTY;
        }
        return FluidUtil.getFluidHandler(world, pos, null)
                .map(handler -> handler.drain(Integer.MAX_VALUE, IFluidHandler.FluidAction.SIMULATE))
                .orElse(FluidStack.EMPTY);
    }

    @Override
    public void removeOldElement(Wrapper wrapper) {
        World world = mPump.getWorld();
        if (world == null) {
            return;
        }
        int cost = getEnergyCost();
        AccessibleEnergyHandler energyHandler = mPump.getEnergyHandler();
        int energy = energyHandler.extractEnergyUncheck(cost, true);
        if (energy < cost) {
            return;
        }
        energyHandler.extractEnergyUncheck(cost, false);
        BlockState state = world.getBlockState(wrapper.getElement());
        Block block = state.getBlock();
        if (block instanceof IBucketPickupHandler) {
            ((IBucketPickupHandler) block).pickupFluid(world, wrapper.getElement(), state);
        }
        wrapper.setRemoved(true);
    }

    @Override
    public void collectElement(Wrapper wrapper) {
        if (wrapper.isRemoved()) {
            FluidStack collect = wrapper.getCollect();
            FluidStack sub = new FluidStack(collect, Math.min(collect.getAmount(), getTransfer()));
            int fill = mPump.getFluidHandler().fillUncheck(0, sub, IFluidHandler.FluidAction.EXECUTE);
            collect.shrink(fill);
            if (collect.isEmpty()) {
                wrapper.setCollected(true);
            }
        }
    }

    @Override
    public void save(List<Wrapper> list) {
        ListNBT tags = new ListNBT();
        for (Wrapper wrapper : list) {
            CompoundNBT compound = new CompoundNBT();
            compound.putBoolean("c", wrapper.isCollected());
            compound.putBoolean("r", wrapper.isRemoved());
            compound.putInt("ct", wrapper.getCollectTick());
            compound.putInt("rt", wrapper.getRemoveTick());
            compound.put("f", wrapper.getCollect().writeToNBT(new CompoundNBT()));
            compound.putLong("elem", wrapper.getElement().toLong());
            tags.add(compound);
        }
        mPump.bindNbt.put("fr", tags);
    }

    public void read() {
        ListNBT tags = mPump.bindNbt.getList("fr", Constants.NBT.TAG_COMPOUND);
        List<Wrapper> wrappers = new ArrayList<>();
        for (int i = 0; i < tags.size(); i++) {
            CompoundNBT compound = tags.getCompound(i);
            boolean isCollected = compound.getBoolean("c");
            boolean isRemoved = compound.getBoolean("r");
            int collectTick = compound.getInt("ct");
            int removeTick = compound.getInt("rt");
            long elem = compound.getLong("elem");
            FluidStack stack = FluidStack.loadFluidStackFromNBT(compound.getCompound("f"));
            Wrapper wrapper = new Wrapper(BlockPos.fromLong(elem), stack, removeTick, collectTick, isRemoved, isCollected);
            wrappers.add(wrapper);
        }
        loadSavedData(wrappers);
    }

    public int getDepth() {
        return 20;
    }

    public int getRadius() {
        return 10;
    }

    public int getMaxCache() {
        return 100;
    }

    public int getMaxFind() {
        return 3000;
    }

    public int getEnergyCost() {
        return 10;
    }

    public int getTransfer() {
        return 100;
    }
}