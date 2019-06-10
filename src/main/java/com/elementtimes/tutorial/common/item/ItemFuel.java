package com.elementtimes.tutorial.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.function.IntSupplier;

/**
 * 可然的物品
 * @author luqin2007
 */
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
