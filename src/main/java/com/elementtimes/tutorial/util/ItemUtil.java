package com.elementtimes.tutorial.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
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
     * @param itemStack 要查找的物品
     * @return 所有可被附魔到该物品的附魔
     */
    public static List<Enchantment> getSuitableEnchantments(ItemStack itemStack) {
        return GameRegistry.findRegistry(Enchantment.class).getValuesCollection().stream()
                .filter(enchantment -> enchantment.canApply(itemStack))
                .collect(Collectors.toList());
    }

    /**
     * 为某一物品附魔所有非诅咒的最大等级
     * @param itemStack 要附魔的物品
     */
    public static void addMaxEnchantments(ItemStack itemStack) {
        EnchantmentHelper.setEnchantments(
                getSuitableEnchantments(itemStack).stream()
                        .filter(enchantment -> !enchantment.isCurse())
                        .map(enchantment -> new ImmutablePair <>(enchantment, enchantment.getMaxLevel()))
                        .collect(Collectors.toMap(ImmutablePair::getKey, ImmutablePair::getValue)),
                itemStack
        );
    }

    /**
     * 将一个物品容器中的物品转化为 ItemStack 列表
     * @param itemHandler 物品容器
     * @return 物品列表
     */
    public static List<ItemStack> toList(IItemHandler itemHandler) {
        List<ItemStack> input = new ArrayList<>(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            input.add(i, itemHandler.getStackInSlot(i));
        }
        return input;
    }

    /**
     * 将物品列表转化为 NBT 列表
     * @param items 物品列表
     * @return NBT 列表
     */
    public static NBTTagList toNBTList(List<ItemStack> items) {
        NBTTagList list = new NBTTagList();
        items.forEach(item -> list.appendTag(item.writeToNBT(new NBTTagCompound())));
        return list;
    }

    /**
     * 将 NBT 列表转化为物品列表
     * @param list NBT 列表
     * @return 物品列表
     */
    public static List<ItemStack> fromNBTList(NBTTagList list) {
        int count = list.tagCount();
        List<ItemStack> itemStacks = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            itemStacks.add(new ItemStack(list.getCompoundTagAt(i)));
        }
        return itemStacks;
    }
}
