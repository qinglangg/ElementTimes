package com.elementtimes.tutorial.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 物品/物品栈有关方法
 * @author lq2007
 */
public class ItemUtil {

    /**
     * 获取适用于某一物品的所有附魔
     */
    public static List<Enchantment> getSuitableEnchantments(ItemStack itemStack) {
        return GameRegistry.findRegistry(Enchantment.class).getValuesCollection().stream()
                .filter(enchantment -> enchantment.canApply(itemStack))
                .collect(Collectors.toList());
    }

    /**
     * 为某一物品附魔所有非诅咒的最大等级
     */
    public static void addMaxEnchantments(ItemStack itemStack) {
        EnchantmentHelper.setEnchantments(
                getSuitableEnchantments(itemStack).stream()
                        // 去诅咒
                        .filter(enchantment -> !enchantment.isCurse())
                        // 获取最大等级
                        .map(enchantment -> new ImmutablePair <>(enchantment, enchantment.getMaxLevel()))
                        // 转换为 Map
                        .collect(Collectors.toMap(ImmutablePair::getKey, ImmutablePair::getValue)),
                itemStack
        );
    }

    public static NonNullList<ItemStack> getAllItems(String oreName) {
        Ingredient ingredient = CraftingHelper.getIngredient(oreName);
        return NonNullList.from(ItemStack.EMPTY, ingredient.getMatchingStacks());
    }

    public static ItemStack getItemById(String id) {
        String[] sub = id.split(":");
        Item item = null;
        int meta = 0;
        if (sub.length == 1) {
            item = Item.getByNameOrId("minecraft:" + id);
        } else //noinspection AlibabaUndefineMagicConstant
            if (sub.length > 2) {
            String last = sub[sub.length - 1];
            try {
                meta = Integer.parseInt(last);
                item = Item.getByNameOrId(id.substring(0, id.length() - last.length() - 1));
            } catch (NumberFormatException ignore) {}
        }
        if (item == null) {
            item = Item.getByNameOrId(id);
        }
        if (item == null) {
            item = Items.AIR;
        }
        return new ItemStack(item, 1, meta);
    }

    public static List<ItemStack> toList(IItemHandler itemHandler) {
        List<ItemStack> input = new ArrayList<>(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            input.add(i, itemHandler.getStackInSlot(i));
        }
        return input;
    }

    public static NBTTagList toNBTList(List<ItemStack> items) {
        NBTTagList list = new NBTTagList();
        items.forEach(item -> list.appendTag(item.writeToNBT(new NBTTagCompound())));
        return list;
    }

    public static List<ItemStack> fromNBTList(NBTTagList list) {
        int count = list.tagCount();
        List<ItemStack> itemStacks = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            itemStacks.add(new ItemStack(list.getCompoundTagAt(i)));
        }
        return itemStacks;
    }
}
