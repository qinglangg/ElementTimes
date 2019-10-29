package com.elementtimes.tutorial.other.recipe;

import com.google.gson.JsonObject;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipes;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.common.crafting.JsonContext;
import net.minecraftforge.oredict.OreIngredient;

import java.util.Objects;

/**
 * blockMultiXxx 方块的反序列化类
 * 参数：group, material1, material2(大小写随意), ore1, ore2(二者可选，为矿辞)
 * @author luqin2007
 */
public class FactoryMultiBlock implements IRecipeFactory {
    @Override
    public IRecipe parse(JsonContext context, JsonObject json) {
        String group = JsonUtils.getString(json, "group", "elementtimes:recipes");
        String material1 = JsonUtils.getString(json, "material1").toLowerCase();
        String material2 = JsonUtils.getString(json, "material2").toLowerCase();
        String ore1 = JsonUtils.getString(json, "ore1", material1);
        String ore2 = JsonUtils.getString(json, "ore2", material2);
        String upperOre1 = String.valueOf(ore1.charAt(0)).toUpperCase() + ore1.substring(1).toLowerCase();
        String upperOre2 = String.valueOf(ore2.charAt(0)).toUpperCase() + ore2.substring(1).toLowerCase();
        String plate1 = "plate" + upperOre1;
        String plate2 = "plate" + upperOre2;
        String gear1 = "gear" + upperOre1;
        String gear2 = "gear" + upperOre2;
        String result = "elementtimes:blockmulti" + material1 + material2;
        NonNullList<Ingredient> input = NonNullList.create();
        input.add(new OreIngredient(plate1));
        input.add(new OreIngredient("rubber"));
        input.add(new OreIngredient(plate2));
        input.add(new OreIngredient(gear1));
        input.add(new OreIngredient("rubber"));
        input.add(new OreIngredient(gear2));
        input.add(new OreIngredient(plate1));
        input.add(new OreIngredient("rubber"));
        input.add(new OreIngredient(plate2));
        return new ShapedRecipes(group, 3, 3, input, new ItemStack(Objects.requireNonNull(Item.getByNameOrId(result))));
    }
}
