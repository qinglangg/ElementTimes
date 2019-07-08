package com.elementtimes.tutorial.client;

import com.elementtimes.tutorial.util.FluidUtil;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 用于水瓶的渲染
 * @author luqin2007
 */
@SideOnly(Side.CLIENT)
public class BottleItemStackColor implements IItemColor {

    @Override
    public int colorMultiplier(ItemStack stack, int tintIndex) {
        FluidStack fluid = FluidUtil.getFluid(stack);
        return tintIndex == 0 ? fluid == null
                ? FluidUtil.EMPTY.getFluid().getColor(null)
                : fluid.getFluid().getColor(null) : -1;
    }
}
