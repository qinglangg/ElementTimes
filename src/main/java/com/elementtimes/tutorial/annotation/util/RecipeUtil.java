package com.elementtimes.tutorial.annotation.util;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.Arrays;
import java.util.Map;

/**
 * 用于合成的工具类
 * annotation 包以后怕是会独立出去，所以尽量不会依赖包外的东西
 * @author luqin2007
 */
public class RecipeUtil {

    public static ItemStack getFromItemName(String name) {
        String[] split = name.split(":");
        Item item;
        int damage = 0;
        if (split.length == 1) {
            item = Item.getByNameOrId("minecraft:" + name);
        } else if (split.length == 2) {
            item = Item.getByNameOrId(name);
        } else {
            try {
                item = Item.getByNameOrId(split[0] + ":" + split[1]);
                damage = Integer.valueOf(split[2]);
            } catch (Exception e) {
                item = Item.getByNameOrId(name);
            }
        }
        ItemStack stack = item == null ? new ItemStack(Items.AIR) : new ItemStack(item);
        stack.setItemDamage(damage);
        return stack;
    }

    public static Ingredient getIngredient(Object obj) {
        try {
            if (obj instanceof String) {
                String oreOrName = (String) obj;
                // [ore] 开头 强制矿辞
                if (oreOrName.startsWith("[ore]")) {
                    oreOrName = oreOrName.substring(5);
                    return CraftingHelper.getIngredient(oreOrName);
                }
                // [id] 开头 强制 RegistryName
                if (oreOrName.startsWith("[id]")) {
                    oreOrName = oreOrName.substring(4);
                    return Ingredient.fromStacks(getFromItemName(oreOrName));
                }
                // 否则 先测试矿辞 失败则使用 RegistryName
                Ingredient ingredient = CraftingHelper.getIngredient(oreOrName);
                ItemStack[] matchingStacks = ingredient.getMatchingStacks();
                if (matchingStacks.length == 0) {
                    return Ingredient.fromStacks(getFromItemName(oreOrName));
                }
                return ingredient;
            } else {
                return CraftingHelper.getIngredient(obj);
            }
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Ingredient.EMPTY;
        }
    }

    public static void collectOneBlockCraftingResult(String oreName, Map<ItemStack, ItemStack> receiver) {
        Arrays.stream(CraftingHelper.getIngredient(oreName).getMatchingStacks())
                .filter(stack -> !stack.isEmpty() && Block.getBlockFromItem(stack.getItem()) != Blocks.AIR)
                .map(stack -> ItemHandlerHelper.copyStackWithSize(stack, 1))
                .forEach(stack -> receiver.put(stack, com.elementtimes.tutorial.util.RecipeUtil.getCraftingResult(NonNullList.withSize(1, stack))));
    }
}
