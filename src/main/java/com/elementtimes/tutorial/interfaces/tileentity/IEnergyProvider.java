package com.elementtimes.tutorial.interfaces.tileentity;

import com.elementtimes.tutorial.common.capability.impl.RfEnergy;
import com.elementtimes.tutorial.other.SideHandlerType;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.IEnergyStorage;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 发电相关方法
 * @author luqin2007
 */
public interface IEnergyProvider extends INBTSerializable<NBTTagCompound> {

    /**
     * 向外传电
     * @param count 发送的电量
     * @param facing 发送面
     * @param te 发送面对应 TileEntity
     * @param proxy 使用的代理，将从其中提取能量
     * @return 接收的能量
     */
    default void sendEnergy(int count, @Nullable EnumFacing facing, @Nullable TileEntity te, @Nonnull RfEnergy.EnergyProxy proxy) {
        if (te != null && te.hasCapability(CapabilityEnergy.ENERGY, facing) && proxy.canExtract() && proxy.getEnergyStored() > 0) {
            IEnergyStorage storage = te.getCapability(CapabilityEnergy.ENERGY, facing);
            if (storage != null && storage.canReceive()) {
                int extract = proxy.extractEnergy(count, true);
                int receive = storage.receiveEnergy(extract, true);
                if (receive > 0) {
                    int r = storage.receiveEnergy(receive, false);
                    proxy.extractEnergy(r, false);
                }
            }
        }
    }

    /**
     * 创建一个只能放，不能接收的能量代理
     * @param facing 面
     * @param transfer 最大传输电量
     * @return 能量代理
     */
    default RfEnergy.EnergyProxy getEnergyProxy(EnumFacing facing, int transfer) {
        if (this instanceof ITileEnergyHandler) {
            ITileEnergyHandler energyHandler = (ITileEnergyHandler) this;
            SideHandlerType type = energyHandler.getEnergyType(facing);
            if (type == SideHandlerType.IN_OUT || type == SideHandlerType.OUTPUT) {
                return energyHandler.getEnergyProxy(0, transfer);
            }
            return energyHandler.getReadonlyEnergyProxy();
        }
        return null;
    }
}
