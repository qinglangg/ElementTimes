package com.elementtimes.tutorial.util;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import java.lang.reflect.Field;
import java.util.Map;

/**
 * 与合成表有关的方法
 * 目前主要是 IRecipeFactory 相关辅助
 */
public class RecipeUtil {

    /**
     * 获取任意注册的 IRecipeFactory 对象
     * @param type 该 IRecipeFactory 类型，即合成表中 type 对应的值
     */
    public static IRecipeFactory getIRecipeFactoryByType(String type) {
        if (type.equals("forge:ore_shaped")) return ShapedOreRecipe::factory;
        if (type.equals("forge:ore_shapeless")) return ShapelessOreRecipe::factory;
        try {
            Field recipesField = CraftingHelper.class.getDeclaredField("recipes");
            recipesField.setAccessible(true);
            Map<ResourceLocation, IRecipeFactory> recipes = (Map<ResourceLocation, IRecipeFactory>) recipesField.get(null);
            return recipes.get(new ResourceLocation(type));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 反射获取默认无序合成，即 minecraft:crafting_shapeless 类型注册的 IRecipeFactory
     */
    public static IRecipeFactory shapelessRecipesParser() {
        return getIRecipeFactoryByType("minecraft:crafting_shapeless");
    }

    /**
     * 反射获取默认有序合成，即 minecraft:crafting_shaped 类型注册的 IRecipeFactory
     */
    public static IRecipeFactory shapedRecipesParser() {
        return getIRecipeFactoryByType("minecraft:crafting_shaped");
    }

    /**
     * 获取默认有序矿辞合成，即 forge:ore_shaped 类型注册的 IRecipeFactory
     */
    public static IRecipeFactory shapedOreRecipeParser() {
        return getIRecipeFactoryByType("forge:ore_shaped");
    }

    /**
     * 获取默认无序矿辞合成，即 forge:ore_shapeless 类型注册的 IRecipeFactory
     */
    public static IRecipeFactory shapelessOreRecipeParser() {
        return getIRecipeFactoryByType("forge:ore_shapeless");
    }

}
