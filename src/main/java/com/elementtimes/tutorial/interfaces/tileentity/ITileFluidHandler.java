package com.elementtimes.tutorial.interfaces.tileentity;

import com.elementtimes.tutorial.common.capability.impl.TankHandler;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.util.FluidUtil;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * 流体存储有关的接口
 * @author luqin2007
 */
public interface ITileFluidHandler extends ICapabilityProvider, INBTSerializable<NBTTagCompound> {

    String NBT_FLUID = "_fluids_";
    String NBT_FLUID_INPUT = "_inputs_";
    String NBT_FLUID_OUTPUT = "_outputs_";

    Map<EnumFacing, SideHandlerType> getTankTypeMap();

    Map<SideHandlerType, TankHandler> getTanksMap();

    default TankHandler getTanks(SideHandlerType type) {
        return getTanksMap().get(type);
    }

    default SideHandlerType getTankType(EnumFacing facing) {
        return getTankTypeMap().getOrDefault(facing, SideHandlerType.NONE);
    }

    default void setFluidSlot(int fluidInput, int inputCapacity, int fluidOutput, int outputCapacity) {
        getTanksMap().put(SideHandlerType.INPUT, new TankHandler(this::isFillValid, TankHandler.FALSE, fluidInput, inputCapacity));
        getTanksMap().put(SideHandlerType.OUTPUT, new TankHandler(TankHandler.FALSE, TankHandler.TRUE, fluidOutput, outputCapacity));
        // 空闲
        getTanksMap().put(SideHandlerType.NONE, TankHandler.EMPTY);
        getTanksMap().put(SideHandlerType.READONLY, TankHandler.EMPTY);
        getTanksMap().put(SideHandlerType.IN_OUT, TankHandler.EMPTY);
    }

    @Override
    default boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY
                && getTankType(facing) != SideHandlerType.NONE
                && getTanks(getTankType(facing)).getTankProperties().length > 0;
    }

    @Nullable
    @Override
    default <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability.cast((T) getTanks(getTankType(facing)));
    }

    @Override
    default void deserializeNBT(NBTTagCompound nbt) {
        if (nbt.hasKey(NBT_FLUID)) {
            NBTTagCompound fluids = nbt.getCompoundTag(NBT_FLUID);
            if (fluids.hasKey(NBT_FLUID_INPUT)) {
                getTanksMap().get(SideHandlerType.INPUT).deserializeNBT(fluids.getCompoundTag(NBT_FLUID_INPUT));
            }
            if (fluids.hasKey(NBT_FLUID_OUTPUT)) {
                getTanksMap().get(SideHandlerType.OUTPUT).deserializeNBT(fluids.getCompoundTag(NBT_FLUID_OUTPUT));
            }
        }
    }

    default NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        NBTTagCompound nbt = new NBTTagCompound();
        // input
        TankHandler inputs = getTanks(SideHandlerType.INPUT);
        if (!FluidUtil.isNoCapability(inputs.getTankProperties())) {
            nbt.setTag(NBT_FLUID_INPUT, inputs.serializeNBT());
        }
        // output
        TankHandler outputs = getTanks(SideHandlerType.OUTPUT);
        if (!FluidUtil.isNoCapability(outputs.getTankProperties())) {
            nbt.setTag(NBT_FLUID_OUTPUT, outputs.serializeNBT());
        }
        if (!nbt.hasNoTags()) {
            nbtTagCompound.setTag(NBT_FLUID, nbt);
        }
        return nbtTagCompound;
    }

    default boolean isFillValid(int slot, FluidStack fluidStack) { return false; }
}
