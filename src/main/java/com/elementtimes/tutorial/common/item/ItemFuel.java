package com.elementtimes.tutorial.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.IntSupplier;

public class ItemFuel extends Item {

    private IntSupplier burningTimeProvider;

    public ItemFuel(int burningTime) {
        burningTimeProvider = () -> burningTime;
    }

    public ItemFuel(IntSupplier burningTimeProvider) {
        this.burningTimeProvider = burningTimeProvider;
    }

    @Override
    public int getItemBurnTime(ItemStack itemStack) {
        return burningTimeProvider.getAsInt();
    }
}
