package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.capability.impl.RfEnergy;
import com.elementtimes.tutorial.interfaces.tileentity.IEnergyProvider;
import com.elementtimes.tutorial.interfaces.tileentity.IMachineLifecycle;
import com.elementtimes.tutorial.other.SideHandlerType;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 对所有发电设备的抽象
 * @author luqin2007
 */
public abstract class BaseGenerator extends BaseMachine implements IEnergyProvider {

    public BaseGenerator(int energyCapacity) {
        super(energyCapacity, 1, 0);
        for (EnumFacing value : EnumFacing.values()) {
            getEnergyTypeMap().put(value, SideHandlerType.OUTPUT);
        }

        addLifeCycle(new IMachineLifecycle() {
            @Override
            public void onTickFinish() {
                for (EnumFacing value : EnumFacing.values()) {
                    RfEnergy.EnergyProxy proxy = getEnergyProxy(value);
                    sendEnergy(proxy.getEnergyStored(), value.getOpposite(), world.getTileEntity(pos.offset(value)), proxy);
                }
            }
        });
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityEnergy.ENERGY) {
            return CapabilityEnergy.ENERGY.cast(getEnergyProxy(facing));
        }
        return super.getCapability(capability, facing);
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 80, 30)
        };
    }
}

