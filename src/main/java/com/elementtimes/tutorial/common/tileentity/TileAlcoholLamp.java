package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.capability.impl.TankHandler;
import com.elementtimes.tutorial.interfaces.tileentity.ITileFluidHandler;
import com.elementtimes.tutorial.other.SideHandlerType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * 酒精灯
 * @author luqin2007
 */
public class TileAlcoholLamp extends TileEntity implements ITileFluidHandler {

    private Map<EnumFacing, SideHandlerType> mTankTypeMap = new HashMap<>(EnumFacing.values().length);
    private Map<SideHandlerType, TankHandler> mTanksMap = new HashMap<>(SideHandlerType.values().length);

    public TileAlcoholLamp() {
        for (EnumFacing facing : EnumFacing.values()) {
            mTankTypeMap.put(facing, SideHandlerType.IN_OUT);
        }
        TankHandler tankHandler = new TankHandler(this::isFillValid, TankHandler.TRUE, 1, Integer.MAX_VALUE);
        for (SideHandlerType type : SideHandlerType.values()) {
            mTanksMap.put(type, tankHandler);
        }
    }

    @Override
    public Map<EnumFacing, SideHandlerType> getTankTypeMap() {
        return mTankTypeMap;
    }

    @Override
    public Map<SideHandlerType, TankHandler> getTanksMap() {
        return mTanksMap;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        ITileFluidHandler.super.writeToNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return ITileFluidHandler.super.writeToNBT(super.writeToNBT(compound));
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return serializeNBT();
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return super.hasCapability(capability, facing) || ITileFluidHandler.super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return ITileFluidHandler.super.getCapability(capability, facing);
        }
        return super.getCapability(capability, facing);
    }
}
