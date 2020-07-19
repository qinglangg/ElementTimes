package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.ItemIngredient;
import com.elementtimes.elementcore.api.recipe.ItemStackIngredient;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;



public class FormingRecipe extends BaseRecipe {

    public static final BaseRecipeType<FormingRecipe> TYPE = new BaseRecipeType<FormingRecipe>() {};

    public FormingRecipe(String name, IItemProvider input, IItemProvider output) {
        super(10000, 0f,
                NonNullList.withSize(1, new ItemStackIngredient(9, input)),
                NonNullList.withSize(1, new ItemStackIngredient(3, output)),
                NonNullList.create(),
                NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public FormingRecipe(String name, Tag<Item> input, IItemProvider output) {
        super(10000, 0f,
                NonNullList.withSize(1, new ItemIngredient(9, Ingredient.fromTag(input))),
                NonNullList.withSize(1, new ItemStackIngredient(3, output)),
                NonNullList.create(),
                NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
