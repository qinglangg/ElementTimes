package com.elementtimes.tutorial.annotation.other;

import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 用于多物品耐久匹配的配方。
 * items 为可接受物品，damageCount 为一次合成要消耗的耐久，用于判断
 *
 * @see net.minecraft.item.crafting.Ingredient
 * @see net.minecraftforge.oredict.OreIngredient
 * @author luqin2007
 */
public class DamageIngredient extends Ingredient {
    private NonNullList<ItemStack> tools;
    private IntList itemIds = null;
    private ItemStack[] array = null;
    private int lastSizeA = -1, lastSizeL = -1;

    public DamageIngredient(Item[] items, int damageCount) {
        super(0);
        tools = NonNullList.create();
        for (Item item : items) {
            ItemStack itemStack = new ItemStack(item);
            tools.add(itemStack.copy());
            if (item.isDamageable() && damageCount > 0) {
                for (int i = 1; i < item.getMaxDamage(itemStack) - damageCount + 1; i++) {
                    ItemStack stack = itemStack.copy();
                    stack.setItemDamage(i);
                    tools.add(stack);
                }
            }
        }
    }

    @Override
    @Nonnull
    public ItemStack[] getMatchingStacks() {
        if (array == null || lastSizeA != tools.size()) {
            NonNullList<ItemStack> lst = NonNullList.create();
            lst.addAll(tools);
            array = lst.toArray(new ItemStack[0]);
            lastSizeA = tools.size();
        }
        return array;
    }

    @Override
    @Nonnull
    public IntList getValidItemStacksPacked() {
        if (itemIds == null || lastSizeL != tools.size()) {
            itemIds = new IntArrayList(tools.size());

            for (ItemStack itemstack : tools) {
                int id = Item.REGISTRY.getIDForObject(itemstack.getItem()) << 16 | itemstack.getItemDamage() & 65535;
                itemIds.add(id);
            }
            lastSizeL = tools.size();
        }

        return itemIds;
    }

    @Override
    public boolean apply(@Nullable ItemStack input) {
        if (input != null && !input.isEmpty()) {
            for (ItemStack target : tools) {
                if (target.isItemEqual(input)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void invalidate() {
        itemIds = null;
        array = null;
    }

    @Override
    public boolean isSimple() {
        return false;
    }
}
