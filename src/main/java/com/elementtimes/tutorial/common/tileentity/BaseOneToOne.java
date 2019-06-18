package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.common.capability.impl.RfEnergy;
import com.elementtimes.tutorial.interfaces.tileentity.IGuiProvider;
import com.elementtimes.tutorial.other.SideHandlerType;
import net.minecraft.inventory.Slot;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 对一个输入 一个输出的机器的抽象
 * @author KSGFK create in 2019/5/12
 */
public abstract class BaseOneToOne extends BaseMachine {

    public BaseOneToOne(int maxEnergy) {
        super(maxEnergy, 1, 1);
        for (EnumFacing facing : EnumFacing.values()) {
            if (getEnergyTypeMap().get(facing) == SideHandlerType.OUTPUT) {
                getEnergyTypeMap().put(facing, SideHandlerType.NONE);
            } else if (getEnergyTypeMap().get(facing) == SideHandlerType.IN_OUT) {
                getEnergyTypeMap().put(facing, SideHandlerType.INPUT);
            }
        }
    }

    @Override
    public RfEnergy.EnergyProxy getEnergyProxy(EnumFacing facing) {
        if (getEnergyTypeMap().get(facing) == SideHandlerType.OUTPUT) {
            return getReadonlyEnergyProxy();
        }
        return super.getEnergyProxy(SideHandlerType.INPUT);
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 56, 30),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 110, 30)
        };
    }
}
