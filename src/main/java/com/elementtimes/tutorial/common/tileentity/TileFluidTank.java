package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.UpdateHelper;
import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
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

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 流体储存机
 * @author luqin2007
 */
public class TileFluidTank extends TileEntity implements IFluidHandler {

    private FluidStack fluid = null;
    private int capability = 16000;
    private int level = 0;

    public FluidStack getFluid() {
        return fluid;
    }

    public int getCapability() {
        return capability;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int newLevel) {
        if (level != newLevel) {
            capability = 16000 + newLevel * 16000;
            if (newLevel < level) {
                if (fluid != null) {
                    fluid.amount = Math.min(capability, fluid.amount);
                }
            }
        }
        markDirty();
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(this);
        }
        return super.getCapability(capability, facing);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("l", level);
        compound.setTag("f", fluid == null ? new NBTTagCompound() : fluid.writeToNBT(new NBTTagCompound()));
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        UpdateHelper.tankNbtFix(compound);
        int l = compound.getInteger("l");
        NBTTagCompound f = compound.getCompoundTag("f");
        if (f.hasNoTags()) {
            fluid = null;
        } else {
            fluid = FluidStack.loadFluidStackFromNBT(f);
        }
        setLevel(l);
        super.readFromNBT(compound);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound compound = new NBTTagCompound();
        compound.setInteger("l", getLevel());
        if (isEmpty()) {
            compound.setBoolean("e", true);
        } else {
            compound.setBoolean("e", false);
            compound.setInteger("c", fluid.amount);
            compound.setString("f", fluid.getFluid().getName());
        }
        return new SPacketUpdateTileEntity(pos, 1, compound);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        NBTTagCompound compound = pkt.getNbtCompound();
        setLevel(compound.getInteger("l"));
        if (compound.getBoolean("e")) {
            fluid = null;
        } else {
            Fluid fluid = FluidRegistry.getFluid(compound.getString("f"));
            if (fluid != null) {
                this.fluid = new FluidStack(fluid, compound.getInteger("c"));
            }
        }
        markDirty();
    }

    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    public void markDirty() {
        if (fluid != null && fluid.amount <= 0) {
            fluid = null;
        }
        super.markDirty();
        if (world != null && !world.isRemote) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 1);
        }
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    @Override
    public IFluidTankProperties[] getTankProperties() {
        FluidTankProperties properties;
        if (isEmpty()) {
            properties = new FluidTankProperties(null, capability, true, true);
        } else {
            properties = new FluidTankProperties(fluid.copy(), capability, true, true);
        }
        return new IFluidTankProperties[] {properties};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (resource == null || resource.amount == 0) {
            return 0;
        }
        if (isEmpty()) {
            int fill = Math.min(capability, resource.amount);
            if (doFill) {
                fluid = new FluidStack(resource, fill);
                markDirty();
            }
            return fill;
        } else if (fluid.isFluidEqual(resource)) {
            int fill = Math.min(capability - fluid.amount, resource.amount);
            if (fill > 0) {
                if (doFill) {
                    fluid.amount += fill;
                    markDirty();
                }
                return fill;
            }
        }
        return 0;
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) {
        if (isEmpty() || resource == null || resource.amount <= 0) {
            return null;
        }
        if (resource == fluid || fluid.isFluidEqual(resource)) {
            int drain = Math.min(fluid.amount, resource.amount);
            if (drain > 0) {
                FluidStack cache = fluid;
                if (doDrain) {
                    fluid.amount -= drain;
                    markDirty();
                }
                return new FluidStack(cache, drain);
            }
        }
        return null;
    }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) {
        if (isEmpty() || maxDrain <= 0) {
            return null;
        }
        int drain = Math.min(maxDrain, fluid.amount);
        if (drain > 0) {
            FluidStack cache = fluid;
            if (doDrain) {
                fluid.amount -= drain;
                markDirty();
            }
            return new FluidStack(cache, drain);
        }
        return null;
    }

    public boolean isEmpty() {
        return fluid == null || fluid.amount == 0 || fluid.getFluid() == null;
    }
}
