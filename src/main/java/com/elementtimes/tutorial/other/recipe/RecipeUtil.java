package com.elementtimes.tutorial.other.recipe;

import com.elementtimes.tutorial.ElementTimes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.function.Supplier;

/**
 * 与合成表有关的方法
 * 目前主要是 IRecipeFactory 相关辅助
 * @author luqin2007
 */
public class RecipeUtil {

    /**
     * 反射获取默认有序合成，即 minecraft:crafting_shaped 类型注册的 IRecipeFactory
     */
    public static IRecipeFactory shapedRecipesParser() {
        try {
            Field recipesField = CraftingHelper.class.getDeclaredField("recipes");
            recipesField.setAccessible(true);
            Map<ResourceLocation, IRecipeFactory> recipes = (Map<ResourceLocation, IRecipeFactory>) recipesField.get(null);
            return recipes.get(new ResourceLocation("minecraft:crafting_shaped"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 创建齿轮合成表
     * @param inputCenter 中心物品
     * @param inputSide 四边物品
     * @param output 输出物品
     * @return 合成表
     */
    public static Supplier<IRecipe> gearRecipe(Object inputCenter, Object inputSide, Item output) {
        return () -> {
            Ingredient in1;
            if (inputCenter instanceof Block) {
                // 避免 meta = OreDictionary.WILDCARD_VALUE 的特殊值，对 Block 特殊处理
                in1 = Ingredient.fromStacks(new ItemStack((Block) inputCenter));
            } else {
                in1 = CraftingHelper.getIngredient(inputCenter);
            }
            Ingredient in4;
            if (inputSide instanceof Block) {
                in4 = Ingredient.fromStacks(new ItemStack((Block) inputSide));
            } else {
                in4 = CraftingHelper.getIngredient(inputSide);
            }
            Ingredient emp = Ingredient.EMPTY;
            ItemStack out = new ItemStack(output, 3);
            CraftingHelper.ShapedPrimer primer = new CraftingHelper.ShapedPrimer();
            primer.width = 3;
            primer.height = 3;
            primer.input = NonNullList.from(emp,
                    emp, in4, emp,
                    in4, in1, in4,
                    emp, in4, emp);
            return new ShapedOreRecipe(new ResourceLocation(ElementTimes.MODID, "recipe"), out, primer);
        };
    }
}
