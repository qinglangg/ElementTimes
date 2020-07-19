package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.ItemIngredient;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;



public class PulverizerRecipe extends BaseRecipe {

    public static final BaseRecipeType<PulverizerRecipe> TYPE = new BaseRecipeType<PulverizerRecipe>() {};

    public PulverizerRecipe(String name, IItemProvider input, IItemProvider output) {
        super(8000, 0f,
                NonNullList.withSize(1, new ItemIngredient(1, Ingredient.fromItems(input))),
                NonNullList.withSize(1, new ItemIngredient(5, Ingredient.fromItems(output))),
                NonNullList.create(), NonNullList.create(),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public PulverizerRecipe(String name, Tag<Item> input, IItemProvider output) {
        super(8000, 0f,
                NonNullList.withSize(1, new ItemIngredient(1, Ingredient.fromTag(input))),
                NonNullList.withSize(1, new ItemIngredient(5, Ingredient.fromItems(output))),
                NonNullList.create(), NonNullList.create(),
                TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
