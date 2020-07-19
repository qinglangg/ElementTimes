package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.FluidIngredient;
import com.elementtimes.elementcore.api.recipe.ItemIngredient;
import com.elementtimes.elementcore.api.recipe.ItemStackIngredient;
import com.elementtimes.elementcore.api.utils.TagUtils;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;



public class SolidMelterRecipe extends BaseRecipe {

    public static final BaseRecipeType<SolidMelterRecipe> TYPE = new BaseRecipeType<SolidMelterRecipe>() {};

    public SolidMelterRecipe(String name, IItemProvider input, Fluid output) {
        super(10000, 0f,
                NonNullList.withSize(1, new ItemStackIngredient(input)),
                NonNullList.create(),
                NonNullList.create(),
                NonNullList.withSize(1, new FluidIngredient(output)),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public SolidMelterRecipe(String name, String type, String material, Fluid output) {
        super(10000, 0f,
                NonNullList.withSize(1, new ItemIngredient(1, Ingredient.fromTag(TagUtils.forgeItem(type, material)))),
                NonNullList.create(),
                NonNullList.create(),
                NonNullList.withSize(1, new FluidIngredient(output)),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
