package com.elementtimes.tutorial.interfaces.tileentity;

import com.elementtimes.tutorial.common.capability.impl.RfEnergy;
import com.elementtimes.tutorial.other.SideHandlerType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.CapabilityEnergy;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * 与能量有关的接口
 * @author luqin2007
 */
public interface ITileEnergyHandler extends ICapabilityProvider, INBTSerializable<NBTTagCompound> {

    RfEnergy getEnergyHandler();

    String NBT_ENERGY = "_energy_";
    String NBT_ENERGY_OLD = "energy";

    Map<EnumFacing, SideHandlerType> getEnergyTypeMap();

    default SideHandlerType getEnergyType(EnumFacing facing) {
        return getEnergyTypeMap().getOrDefault(facing, SideHandlerType.NONE);
    }

    default RfEnergy.EnergyProxy getEnergyProxy(int receive, int extract) {
        return getEnergyHandler().new EnergyProxy(receive, extract);
    }

    default RfEnergy.EnergyProxy getEnergyProxy(SideHandlerType type) {
        if (type == SideHandlerType.INPUT) {
            return getEnergyHandler().new EnergyProxy(true, false);
        }
        if (type == SideHandlerType.OUTPUT) {
            return getEnergyHandler().new EnergyProxy(false, true);
        }
        if (type == SideHandlerType.IN_OUT) {
            return getEnergyHandler().new EnergyProxy(true, true);
        }
        if (type == SideHandlerType.READONLY) {
            return getEnergyHandler().new EnergyProxy(false, false);
        }
        return null;
    }

    default RfEnergy.EnergyProxy getEnergyProxy(EnumFacing facing) {
        return getEnergyProxy(getEnergyType(facing));
    }

    default RfEnergy.EnergyProxy getReadonlyEnergyProxy() {
        return getEnergyProxy(SideHandlerType.READONLY);
    }

    @Override
    default boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityEnergy.ENERGY && getEnergyType(facing) != SideHandlerType.NONE;
    }

    @Nullable
    @Override
    default <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        return capability.cast((T) getEnergyProxy(getEnergyType(facing)));
    }

    @Override
    default void deserializeNBT(NBTTagCompound nbt) {
        // 旧版本兼容
        if (nbt.hasKey(NBT_ENERGY_OLD)) {
            getEnergyHandler().deserializeNBT(nbt.getCompoundTag(NBT_ENERGY_OLD));
            nbt.removeTag(NBT_ENERGY_OLD);
        }

        if (nbt.hasKey(NBT_ENERGY)) {
            getEnergyHandler().deserializeNBT(nbt.getCompoundTag(NBT_ENERGY));
        }
    }

    default NBTTagCompound writeToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound.setTag(NBT_ENERGY, getEnergyHandler().serializeNBT());
        return nbtTagCompound;
    }

    /**
     * 设置最大能量变化量
     * 用电器时则为耗电量，发电时则为发电量
     */
    default int getMaxEnergyChange() {
        return 10000;
    }

    /**
     * 设置最大转移电量，如管道输入输出电量
     */
    default void setMaxTransfer(int transfer) {
        getEnergyHandler().setTransfer(transfer);
    }
}
