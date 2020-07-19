package com.elementtimes.elementtimes.common.block.recipe;

import com.elementtimes.elementcore.api.recipe.BaseRecipe;
import com.elementtimes.elementcore.api.recipe.IRecipeType;
import com.elementtimes.elementcore.api.recipe.ItemIngredient;
import com.elementtimes.elementcore.api.recipe.ItemStackIngredient;
import com.elementtimes.elementcore.api.utils.TagUtils;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.tags.Tag;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;



public class ItemReducerRecipe extends BaseRecipe {

    public static IRecipeType<ItemReducerRecipe> TYPE = new BaseRecipeType<ItemReducerRecipe>() {};

    public ItemReducerRecipe(String name, ItemStack input, ItemStack output) {
        super(8000, 0f,
                NonNullList.withSize(1, new ItemStackIngredient(input)),
                NonNullList.withSize(1, new ItemStackIngredient(output)),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }

    public ItemReducerRecipe(String name, IItemProvider input, ItemStack output) {
        this(name, new ItemStack(input), output);
    }

    public ItemReducerRecipe(String name, String input, ItemStack output) {
        this(name, TagUtils.forgeItem("storage_blocks", input), output);
    }

    public ItemReducerRecipe(String name, Tag<Item> input, ItemStack output) {
        super(8000, 0f,
                NonNullList.withSize(1, new ItemIngredient(1, Ingredient.fromTag(input))),
                NonNullList.withSize(1, new ItemStackIngredient(output)),
                NonNullList.create(), NonNullList.create(), TYPE, new ResourceLocation(ElementTimes.MODID, name));
    }
}
