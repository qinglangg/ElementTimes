package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.*;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;



public class SolidReactorRecipe extends BaseRecipe {

    public static final BaseRecipeType<SolidReactorRecipe> TYPE = new BaseRecipeType<SolidReactorRecipe>() {};

    public static NonNullList<IIngredient<ItemStack>> list(ItemStack input0, ItemStack input1) {
        NonNullList<IIngredient<ItemStack>> list = NonNullList.withSize(2, ItemIngredient.EMPTY);
        list.set(0, new ItemStackIngredient(input0));
        list.set(1, new ItemStackIngredient(input1));
        return list;
    }

    public SolidReactorRecipe(int energy, String name, ItemStack input0, ItemStack input1, ItemStack output0, ItemStack output1, FluidStack fluid) {
        super(energy, 0f,
                list(input0, input1),
                list(output0, output1),
                NonNullList.create(),
                NonNullList.withSize(1, new FluidStackIngredient(fluid)),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
