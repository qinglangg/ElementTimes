package com.elementtimes.tutorial.common.item;

import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;

import java.util.function.IntSupplier;

/**
 * 可燃的瓶子？
 * @author luqin2007
 */
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
