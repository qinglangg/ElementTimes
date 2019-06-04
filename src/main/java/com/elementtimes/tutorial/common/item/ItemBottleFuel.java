package com.elementtimes.tutorial.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;

import java.util.function.IntSupplier;

public class ItemBottleFuel extends ItemGlassBottle {

    private IntSupplier burningTimeProvider;

    public ItemBottleFuel(int burningTime) {
        burningTimeProvider = () -> burningTime;
    }

    public ItemBottleFuel(IntSupplier burningTimeProvider) {
        this.burningTimeProvider = burningTimeProvider;
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return burningTimeProvider.getAsInt();
    }
}
