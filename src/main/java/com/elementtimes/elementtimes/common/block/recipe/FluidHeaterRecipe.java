package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.FluidIngredient;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;



public class FluidHeaterRecipe extends BaseRecipe {

    public static final BaseRecipeType<FluidHeaterRecipe> TYPE = new BaseRecipeType<FluidHeaterRecipe>() {};

    public FluidHeaterRecipe(int energy, String name, Fluid input, Fluid output) {
        super(energy, 0f,
                NonNullList.create(),
                NonNullList.create(),
                NonNullList.withSize(1, new FluidIngredient(input)),
                NonNullList.withSize(1, new FluidIngredient(output)),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
