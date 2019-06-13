package com.elementtimes.tutorial.util;

import com.elementtimes.tutorial.ElementTimes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.common.crafting.IRecipeFactory;
import net.minecraftforge.items.ItemHandlerHelper;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import javax.annotation.Nonnull;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Map;

/**
 * 与合成表有关的方法
 * 目前主要是 IRecipeFactory 相关辅助
 * @author luqin2007
 */
public class RecipeUtil {

    /**
     * 获取任意注册的 IRecipeFactory 对象
     * @param type 该 IRecipeFactory 类型，即合成表中 type 对应的值
     */
    public static IRecipeFactory getIRecipeFactoryByType(String type) {
        if ("forge:ore_shaped".equals(type)) {
            return ShapedOreRecipe::factory;
        }
        if ("forge:ore_shapeless".equals(type)) {
            return ShapelessOreRecipe::factory;
        }
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

    private static InventoryCrafting tempCrafting = new InventoryCrafting(new Container() {
        @Override
        public boolean canInteractWith(@Nonnull EntityPlayer playerIn) { return false; }
        @Override
        public void onCraftMatrixChanged(IInventory inventoryIn) { }
    }, 3, 3);

    /**
     * 测试合成表
     * @param input 输入
     * @return 输出
     */
    public static ItemStack getCraftingResult(@Nonnull NonNullList<ItemStack> input) {
        tempCrafting.clear();
        for (int i = 0; i < input.size(); i++) {
            tempCrafting.setInventorySlotContents(i, input.get(i));
        }
        ItemStack resultEntry = CraftingManager.findMatchingResult(tempCrafting, null).copy();
        tempCrafting.clear();
        return resultEntry;
    }

    /**
     * 获取单物品输入的输出
     * @param oreName 输入矿辞名
     * @param receiver 输出
     */
    public static void collectOneBlockCraftingResult(String oreName, Map<ItemStack, ItemStack> receiver) {
        Arrays.stream(CraftingHelper.getIngredient(oreName).getMatchingStacks())
                .filter(stack -> !stack.isEmpty() && Block.getBlockFromItem(stack.getItem()) != Blocks.AIR)
                .map(stack -> ItemHandlerHelper.copyStackWithSize(stack, 1))
                .forEach(stack -> receiver.put(stack, RecipeUtil.getCraftingResult(NonNullList.withSize(1, stack))));
    }

    /**
     * 创建齿轮合成表
     * @param inputCenter 中心物品
     * @param inputSide 四边物品
     * @param output 输出物品
     * @return 合成表
     */
    public static ShapedOreRecipe gearRecipe(Object inputCenter, Object inputSide, Item output) {
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
    }
}
