package com.elementtimes.tutorial.other;

import com.elementtimes.tutorial.common.item.ItemBottleFuel;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * 用于水瓶的渲染
 * @author luqin2007
 */
public class BottleItemStackColor implements IItemColor {

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        NBTTagCompound fluid = ItemBottleFuel.getFluidNBT(stack);
        return tintIndex == 0 ? fluid.getKeySet().stream().findFirst().map(fluid::getInteger).orElse(0) : -1;
    }
}
