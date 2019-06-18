package com.elementtimes.tutorial.other;

import com.elementtimes.tutorial.util.FluidUtil;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;

/**
 * 用于水瓶的渲染
 * @author luqin2007
 */
public class BottleItemStackColor implements IItemColor {

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        FluidStack fluid = FluidUtil.getFluid(stack);
        return tintIndex == 0 ? fluid == null
                ? FluidUtil.EMPTY.getFluid().getColor(null)
                : fluid.getFluid().getColor(null) : -1;
    }
}
