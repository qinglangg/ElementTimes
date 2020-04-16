package com.elementtimes.tutorial.other;

import com.elementtimes.elementcore.api.template.capability.EnergyHandler;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.WorldReplaceLifecycle;
import com.elementtimes.tutorial.common.tileentity.TilePumpFluid;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.fluids.*;

import java.util.ArrayList;
import java.util.List;

import static net.minecraftforge.fluids.BlockFluidBase.LEVEL;

public class FluidPumpLifecycle extends WorldReplaceLifecycle<FluidPumpLifecycle.Data, FluidStack> {

    private TilePumpFluid mPump;

    private List<BlockPos> mPos = new ArrayList<>();
    private int mRadius = 0, mDepth = 0;

    public FluidPumpLifecycle(TilePumpFluid pump) {
        mPump = pump;
    }

    @Override
    public void onTickStart() {
        super.onTickStart();
        if (mRadius != getRadius() || mDepth != getDepth()) {
            mPos.clear();
            mRadius = getRadius();
            mDepth = getDepth();
            BlockPos pos = mPump.getPos();
            BlockPos.getAllInBox(pos.down().east(mRadius).north(mRadius), pos.down(mDepth).west(mRadius).south(mRadius)).forEach(mPos::add);
        }
    }

    @Override
    public void findElements(List<Data> list) {
        World world = mPump.getWorld();
        int count = 10;
        for (BlockPos pos : mPos) {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            Fluid fluid = FluidRegistry.lookupFluidForBlock(block);
            if (fluid != null) {
                boolean isSource;
                if (block instanceof BlockFluidClassic) {
                    isSource = ((BlockFluidClassic) block).isSourceBlock(mPump.getWorld(), pos);
                } else if (state.getPropertyKeys().contains(LEVEL)) {
                    isSource = state.getValue(LEVEL) == 0;
                } else {
                    isSource = false;
                }
                if (isSource) {
                    // energy
                    EnergyHandler.EnergyProxy proxy = mPump.getEnergyProxy(SideHandlerType.OUTPUT);
                    int cost = getEnergyCost();
                    if (proxy.extractEnergy(cost, true) == cost) {
                        proxy.extractEnergy(cost, false);
                        Data s = new Data();
                        s.state = state;
                        s.block = block;
                        s.fluid = fluid;
                        s.pos = pos;
                        list.add(s);
                        count--;
                        if (count < 0) {
                            break;
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canFind() {
        return mPump.hasWorld() && !mPos.isEmpty() && mElements.size() < 10;
    }

    @Override
    public FluidStack convert(Data state) {
        Block block = state.block;
        if (block instanceof BlockFluidBase) {
            return ((BlockFluidBase) block).drain(mPump.getWorld(), state.pos, false);
        } else {
            return new FluidStack(state.fluid, Fluid.BUCKET_VOLUME);
        }
    }

    @Override
    public void removeOldElement(Wrapper wrapper) {
        IBlockState state = wrapper.getElement().state;
        BlockPos pos = wrapper.getElement().pos;
        if (state.getPropertyKeys().contains(LEVEL)) {
            int value = state.getValue(LEVEL);
            if (value < 15) {
                IBlockState ns = state.withProperty(LEVEL, value + 1);
                mPump.getWorld().setBlockState(pos, ns, 3);
                wrapper.getElement().state = ns;
            } else {
                mPump.getWorld().setBlockToAir(pos);
                wrapper.setRemoved(true);
            }
        } else {
            mPump.getWorld().setBlockToAir(pos);
            wrapper.setRemoved(true);
        }
    }

    @Override
    public void collectElement(Wrapper wrapper) {
        if (wrapper.isRemoved()) {
            FluidStack collect = wrapper.getCollect();
            FluidStack sub = new FluidStack(collect, Math.min(collect.amount, getTransfer()     ));
            int fill = mPump.getTanks(SideHandlerType.OUTPUT).fillIgnoreCheck(0, sub, true);
            collect.amount -= fill;
            if (collect.amount <= 0) {
                wrapper.setCollected(true);
            }
        }
    }

    @Override
    public void save(List<Wrapper> list) {
        NBTTagList tags = new NBTTagList();
        for (Wrapper wrapper : list) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setBoolean("c", wrapper.isCollected());
            compound.setBoolean("r", wrapper.isRemoved());
            compound.setInteger("ct", wrapper.getCollectTick());
            compound.setInteger("rt", wrapper.getRemoveTick());
            compound.setTag("f", wrapper.getCollect().writeToNBT(new NBTTagCompound()));
            compound.setInteger("x", wrapper.getElement().pos.getX());
            compound.setInteger("y", wrapper.getElement().pos.getY());
            compound.setInteger("z", wrapper.getElement().pos.getZ());
            tags.appendTag(compound);
        }
        mPump.bindNbt.setTag("fr", tags);
    }

    public void read() {
        NBTTagList tags = mPump.bindNbt.getTagList("fr", Constants.NBT.TAG_COMPOUND);
        List<Wrapper> wrappers = new ArrayList<>();
        for (int i = 0; i < tags.tagCount(); i++) {
            NBTTagCompound compound = tags.getCompoundTagAt(i);
            boolean isCollected = compound.getBoolean("c");
            boolean isRemoved = compound.getBoolean("r");
            int collectTick = compound.getInteger("ct");
            int removeTick = compound.getInteger("rt");
            FluidStack stack = FluidStack.loadFluidStackFromNBT(compound.getCompoundTag("f"));
            int x = compound.getInteger("x");
            int y = compound.getInteger("y");
            int z = compound.getInteger("z");
            Data data = new Data();
            data.pos = new BlockPos(x, y, z);
            assert stack != null;
            data.fluid = stack.getFluid();
            data.state = mPump.getWorld().getBlockState(data.pos);
            data.block = data.state.getBlock();
            Wrapper wrapper = new Wrapper(data, stack, removeTick, collectTick, isRemoved, isCollected);
            wrappers.add(wrapper);
        }
        loadSavedData(wrappers);
    }

    public int getRadius() {
        return 10;
    }

    public int getDepth() {
        return 20;
    }

    public int getEnergyCost() {
        return 10;
    }

    public int getTransfer() {
        return 100;
    }

    class Data {
        IBlockState state;
        Block block;
        Fluid fluid;
        BlockPos pos;
    }
}
