package com.elementtimes.tutorial.client;

import com.elementtimes.elementcore.api.ECUtils;
import com.elementtimes.elementcore.api.utils.FluidUtils;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * 用于水瓶的渲染
 * @author luqin2007
 */
@SuppressWarnings("unused")
@SideOnly(Side.CLIENT)
public class BottleItemStackColor implements IItemColor {

    @Override
    public int colorMultiplier(@Nonnull ItemStack stack, int tintIndex) {
        FluidStack fluid = ECUtils.fluid.getFluid(stack);
        return tintIndex == 0 ? fluid == null
                ? FluidUtils.EMPTY.getFluid().getColor(null)
                : fluid.getFluid().getColor(null) : -1;
    }
}
