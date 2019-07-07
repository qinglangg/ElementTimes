package com.elementtimes.tutorial.other;

import com.elementtimes.tutorial.common.item.ItemBottleFuel;
import com.elementtimes.tutorial.util.FluidUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nullable;

public class FluidIngredient extends Ingredient {
    private ItemStack mItemStack;

    private FluidIngredient(ItemStack itemStack) {
        mItemStack = itemStack;
    }

    public static FluidIngredient bucket(FluidStack fluidStack) {
        return new FluidIngredient(net.minecraftforge.fluids.FluidUtil.getFilledBucket(fluidStack));
    }

    public static FluidIngredient bucket(Fluid fluid) {
        return bucket(new FluidStack(fluid, Fluid.BUCKET_VOLUME));
    }

    public static FluidIngredient bottle(FluidStack fluidStack) {
        return new FluidIngredient(ItemBottleFuel.createByFluid(fluidStack));
    }

    public static FluidIngredient bottle(Fluid fluid) {
        return new FluidIngredient(ItemBottleFuel.createByFluid(fluid));
    }

    @Override
    public ItemStack[] getMatchingStacks() {
        return new ItemStack[] {mItemStack};
    }

    @Override
    public boolean apply(@Nullable ItemStack p_apply_1_) {
        if (mItemStack.isItemEqual(p_apply_1_)) {
            FluidStack fluidStackIn = FluidUtil.getFluid(p_apply_1_);
            FluidStack fluidStack = FluidUtil.getFluid(mItemStack);
            if (fluidStack != null && fluidStackIn != null) {
                return fluidStack.containsFluid(fluidStackIn);
            }
        }
        return false;
    }
}
