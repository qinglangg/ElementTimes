package com.elementtimes.tutorial.util;

import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;

/**
 * 用于为 3*3 有序合成产物附魔非诅咒附魔最大等级
 * @author lq2007
 */
public class MaxEnchantmentLevelFactory implements IRecipeFactory {

    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        ShapedRecipes recipes = (ShapedRecipes) RecipeUtil.shapedRecipesParser().parse(context, json);
        ItemStack result = recipes.getRecipeOutput();
        ItemUtil.addMaxEnchantments(result);
        return recipes;
    }
}