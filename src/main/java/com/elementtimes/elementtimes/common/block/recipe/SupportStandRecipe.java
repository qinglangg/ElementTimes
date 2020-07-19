package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.IIngredient;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidStack;



public class SupportStandRecipe extends BaseRecipe {

    public static final BaseRecipeType<SupportStandRecipe> TYPE = new BaseRecipeType<SupportStandRecipe>() {};

    public SupportStandRecipe(int energy, float experience, NonNullList<IIngredient<ItemStack>> itemInputs, NonNullList<IIngredient<ItemStack>> itemOutputs, NonNullList<IIngredient<FluidStack>> fluidInputs, NonNullList<IIngredient<FluidStack>> fluidOutputs, ResourceLocation id) {
        super(energy, experience, itemInputs, itemOutputs, fluidInputs, fluidOutputs, TYPE, id);
    }
}
