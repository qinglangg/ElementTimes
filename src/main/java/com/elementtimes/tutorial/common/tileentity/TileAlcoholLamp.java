package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.capability.fluid.ITankHandler;
import com.elementtimes.elementcore.api.template.capability.fluid.TankHandler;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileFluidHandler;
import com.elementtimes.tutorial.common.block.AlcoholLamp;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 酒精灯
 * @author luqin2007
 */
public class TileAlcoholLamp extends TileEntity implements ITileFluidHandler {

    private ITankHandler mTankHandler;

    public TileAlcoholLamp() {
        mTankHandler = new TankHandler(this::isFillValid, TankHandler.TRUE, 1, AlcoholLamp.ALCOHOL_AMOUNT);
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        ITileFluidHandler.super.deserializeNBT(compound);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        return ITileFluidHandler.super.writeToNBT(super.writeToNBT(compound));
    }

    @Override
    public boolean isFillValid(int slot, FluidStack fluidStack) {
        return fluidStack != null && fluidStack.getFluid() == ElementtimesFluids.ethanol;
    }

    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        return serializeNBT();
    }

    @Override
    public ITankHandler getTanks(SideHandlerType type) {
        return mTankHandler;
    }

    @Override
    public SideHandlerType getTankType(EnumFacing facing) {
        return SideHandlerType.ALL;
    }

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return ITileFluidHandler.super.hasCapability(capability, facing) || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        final T capability1 = ITileFluidHandler.super.getCapability(capability, facing);
        return capability1 == null ? super.getCapability(capability, facing) : capability1;
    }

}
