package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.ItemStackIngredient;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;



public class RebuildRecipe extends BaseRecipe {

    public static final BaseRecipeType<RebuildRecipe> TYPE = new BaseRecipeType<RebuildRecipe>() {};

    public RebuildRecipe(int energy, String name, IItemProvider input, IItemProvider output, int outCount) {
        super(energy, 0f,
                NonNullList.withSize(1, new ItemStackIngredient(1, input)),
                NonNullList.withSize(1, new ItemStackIngredient(outCount, output)),
                NonNullList.create(), NonNullList.create(),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public RebuildRecipe(int energy, String name, ItemStack input, ItemStack output) {
        super(energy, 0f,
                NonNullList.withSize(1, new ItemStackIngredient(input)),
                NonNullList.withSize(1, new ItemStackIngredient(output)),
                NonNullList.create(), NonNullList.create(),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
