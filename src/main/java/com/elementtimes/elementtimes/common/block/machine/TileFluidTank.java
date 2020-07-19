package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Getter2;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.Constants;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 流体储存机

 */
@ModTileEntity.Ter(@Getter2("com.elementtimes.elementtimes.client.block.FluidTankRender"))
@ModTileEntity(blocks = @Getter(value = Industry.class, name = "fluidTank"))
public class TileFluidTank extends TileEntity implements IFluidHandler {

    private FluidStack fluid = FluidStack.EMPTY;
    private int level = 0;

    public TileFluidTank(TileEntityType<TileFluidTank> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public FluidStack getFluid() {
        return fluid;
    }

    public int getCapability() {
        return Config.storageFluid.get() * (level + 1);
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int newLevel) {
        if (level != newLevel) {
            if (newLevel < level && !fluid.isEmpty()) {
                fluid.setAmount(Math.min(getCapability(), fluid.getAmount()));
            }
            markDirty();
        }
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (world != null && !world.isRemote) {
            BlockState state = getBlockState();
            world.notifyBlockUpdate(pos, state, state, Constants.BlockFlags.BLOCK_UPDATE);
        }
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return fluid.copy();
    }

    @Override
    public int getTankCapacity(int tank) {
        return tank == 0 ? getCapability() : 1;
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return fluid.isEmpty() || fluid.isFluidEqual(stack);
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (resource == null || resource.isEmpty()) {
            return 0;
        }
        if (isEmpty()) {
            int fill = Math.min(getCapability(), resource.getAmount());
            if (action.execute()) {
                fluid = new FluidStack(resource, fill);
                markDirty();
            }
            return fill;
        } else if (fluid.isFluidEqual(resource)) {
            int fill = Math.min(getCapability() - fluid.getAmount(), resource.getAmount());
            if (fill > 0) {
                if (action.execute()) {
                    fluid.grow(fill);
                    markDirty();
                }
                return fill;
            }
        }
        return 0;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        if (isEmpty() || maxDrain <= 0) {
            return FluidStack.EMPTY;
        }
        int drain = Math.min(maxDrain, fluid.getAmount());
        if (drain > 0) {
            FluidStack cache = fluid;
            if (action.execute()) {
                fluid.shrink(drain);
                markDirty();
            }
            return new FluidStack(cache, drain);
        }
        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        if (isEmpty() || resource == null || resource.isEmpty()) {
            return FluidStack.EMPTY;
        }
        if (resource == fluid || fluid.isFluidEqual(resource)) {
            int drain = Math.min(fluid.getAmount(), resource.getAmount());
            if (drain > 0) {
                FluidStack cache = fluid;
                if (action.execute()) {
                    fluid.shrink(drain);
                    markDirty();
                }
                return new FluidStack(cache, drain);
            }
        }
        return FluidStack.EMPTY;
    }

    public boolean isEmpty() {
        return fluid == null || fluid.isEmpty();
    }

    @Override
    @Nonnull
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> (T) this);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public void read(CompoundNBT compound) {
        fluid = FluidStack.loadFluidStackFromNBT(compound.getCompound("fluid"));
        level = compound.getInt("level");
        super.read(compound);
    }

    @Override
    @Nonnull
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        compound.put("fluid", fluid.writeToNBT(new CompoundNBT()));
        compound.putInt("level", level);
        return super.write(compound);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(pos, 1, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        handleUpdateTag(pkt.getNbtCompound());
    }

    @Override
    @Nonnull
    public CompoundNBT getUpdateTag() {
        CompoundNBT compound = new CompoundNBT();
        compound.putInt("l", level);
        compound.put("f", fluid.writeToNBT(new CompoundNBT()));
        return compound;
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        fluid = FluidStack.loadFluidStackFromNBT(tag.getCompound("f"));
        setLevel(tag.getInt("l"));
    }
}
