package com.elementtimes.tutorial.util;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.tuple.ImmutablePair;

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
        NonNullList list = NonNullList.create();
        for (ItemStack itemstack : OreDictionary.getOres(oreName)) {
            if (itemstack.getMetadata() == OreDictionary.WILDCARD_VALUE)
                itemstack.getItem().getSubItems(CreativeTabs.SEARCH, list);
            else
                list.add(itemstack);
        }
        return list;
    }
}
