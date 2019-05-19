package com.elementtimes.tutorial.util;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.apache.commons.lang3.tuple.ImmutablePair;

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
}
