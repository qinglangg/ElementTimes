package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.interfaces.item.IGeneratorElement;
import net.minecraft.item.Item;

import java.util.function.IntSupplier;

public class ItemElementEnergy extends Item implements IGeneratorElement {

    private IntSupplier energyProvider;

    public ItemElementEnergy(int energy) {
        energyProvider = () -> energy;
    }

    public ItemElementEnergy(IntSupplier energyProvider) {
        this.energyProvider = energyProvider;
    }

    @Override
    public int getEnergy() {
        return energyProvider.getAsInt();
    }
}
