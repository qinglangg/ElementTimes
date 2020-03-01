package com.elementtimes.tutorial.common.tileentity;

import net.minecraft.block.state.IBlockState;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
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
public class TileFluidTank extends TileEntity {

    private Handler mHandler = new Handler();

    public FluidStack getFluid() {
        return mHandler.fluid;
    }

    public int getCapability() {
        return mHandler.capability;
    }

    public int getLevel() {
        return mHandler.level;
    }

    public void setLevel(int newLevel) {
        mHandler.setLevel(newLevel);
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY.cast(mHandler);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setTag("t", mHandler.serializeNBT());
        return super.writeToNBT(compound);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        mHandler.deserializeNBT(compound.getCompoundTag("t"));
        super.readFromNBT(compound);
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(pos, 1, mHandler.serializeNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        mHandler.deserializeNBT(pkt.getNbtCompound());
    }

    @Override
    public void markDirty() {
        super.markDirty();
        if (!world.isRemote) {
            IBlockState state = world.getBlockState(pos);
            world.notifyBlockUpdate(pos, state, state, 1);
        }
    }

    @Override
    public boolean hasFastRenderer() {
        return true;
    }

    class Handler implements IFluidHandler, INBTSerializable<NBTTagCompound> {
        private FluidStack fluid = null;
        private int capability = 16000;
        private int level = 0;

        @Override
        public IFluidTankProperties[] getTankProperties() {
            return new IFluidTankProperties[] {new FluidTankProperties(new FluidStack(fluid, fluid.amount), capability, true, true)};
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            if (resource == null || resource.amount == 0) {
                return 0;
            }
            if (fluid != null && fluid.isFluidEqual(resource)) {
                int fill = Math.min(capability - fluid.amount, resource.amount);
                if (fill > 0 && doFill) {
                    fluid.amount += fill;
                    onFluidChange();
                }
                return fill;
            }
            if (fluid == null) {
                int fill = Math.min(capability, resource.amount);
                if (doFill) {
                    fluid = new FluidStack(resource, fill);
                    onFluidChange();
                }
                return fill;
            }
            return 0;
        }

        @Nullable
        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            if (resource == null || fluid == null) {
                return null;
            }
            if (fluid.isFluidEqual(resource)) {
                int drain = Math.min(fluid.amount, resource.amount);
                if (drain > 0 && doDrain) {
                    if (fluid.amount == drain) {
                        fluid = null;
                    } else {
                        fluid.amount -= drain;
                    }
                    onFluidChange();
                }
                return fluid == null ? null : new FluidStack(fluid, drain);
            }
            return null;
        }

        @Nullable
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            if (fluid == null) {
                return null;
            }
            int drain = Math.min(maxDrain, fluid.amount);
            if (drain > 0) {
                if (doDrain) {
                    if (fluid.amount == drain) {
                        fluid = null;
                    } else {
                        fluid.amount -= drain;
                    }
                    onFluidChange();
                }
                return fluid == null ? null : new FluidStack(fluid, drain);
            }
            return null;
        }

        private void setLevel(int newLevel) {
            if (level != newLevel) {
                capability = 16000 + newLevel * 16000;
                if (newLevel < level) {
                    if (fluid != null) {
                        fluid.amount = Math.min(capability, fluid.amount);
                    }
                }
            }
            onFluidChange();
        }

        private void onFluidChange() {
            if (fluid != null && fluid.amount == 0) {
                fluid = null;
            }
            markDirty();
        }

        @Override
        public NBTTagCompound serializeNBT() {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("l", level);
            compound.setTag("f", fluid == null ? new NBTTagCompound() : fluid.writeToNBT(new NBTTagCompound()));
            return compound;
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            int l = nbt.getInteger("l");
            NBTTagCompound f = nbt.getCompoundTag("f");
            if (f.hasNoTags()) {
                fluid = null;
            } else {
                fluid = FluidStack.loadFluidStackFromNBT(f);
            }
            setLevel(l);
        }
    }
}
