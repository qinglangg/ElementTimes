package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.FluidIngredient;
import com.elementtimes.elementcore.api.recipe.IRecipeType;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;



public class CondenserRecipe extends BaseRecipe {

    public static final IRecipeType<CondenserRecipe> TYPE = new BaseRecipeType<CondenserRecipe>() {};

    public CondenserRecipe(Fluid input, Fluid output, String name) {
        super(2000, 0, NonNullList.create(), NonNullList.create(),
                NonNullList.withSize(1, new FluidIngredient(input)),
                NonNullList.withSize(1, new FluidIngredient(output)),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
