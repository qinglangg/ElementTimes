package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.registries.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "creativeTank"))
public class TileCreativeFluidTank extends TileEntity implements IFluidHandler {

    private Fluid mFluid = Fluids.WATER;

    public TileCreativeFluidTank(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    public Fluid getFluid() {
        if (mFluid == null) {
            setFluid(Fluids.WATER);
        }
        return mFluid;
    }

    public void setFluid(Fluid fluid) {
        if (fluid == null) {
            fluid = Fluids.WATER;
        }
        if (mFluid != fluid) {
            mFluid = fluid;
            markDirty();
        }
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return new FluidStack(getFluid(), Integer.MAX_VALUE);
    }

    @Override
    public int getTankCapacity(int tank) {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return stack.getFluid() == getFluid();
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        setFluid(resource.getFluid());
        return resource.getAmount();
    }

    @Override
    @Nonnull
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return resource.copy();
    }

    @Override
    @Nonnull
    public FluidStack drain(int maxDrain, FluidAction action) {
        return new FluidStack(getFluid(), maxDrain);
    }

    @Override
    public void read(CompoundNBT compound) {
        setFluid(ForgeRegistries.FLUIDS.getValue(ResourceLocation.tryCreate(compound.getString("fluid"))));
        super.read(compound);
    }

    @Override
    @Nonnull
    public CompoundNBT write(@Nonnull CompoundNBT compound) {
        compound.putString("fluid", getFluid().getRegistryName().toString());
        return super.write(compound);
    }

    @Override
    public <T> LazyOptional<T> getCapability(Capability<T> capability, @Nullable Direction facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> (T) this);
        }
        return super.getCapability(capability, facing);
    }
}
