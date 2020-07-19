package com.elementtimes.elementtimes.common.item;

import com.elementtimes.elementcore.api.utils.MathUtils;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.init.Groups;
import com.elementtimes.elementtimes.common.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;

import javax.annotation.Nonnull;



public class Hammer extends Item {

    public static final String TAG_DAMAGE = "damage";
    public static final String TAG_REMOVE = "damage";

    public Hammer(int damage) {
        super(new Properties().group(Groups.Ore).maxDamage(damage));
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == Items.diamondIngot;
    }

    @Override
    public boolean hasContainerItem(ItemStack stack) {
        return true;
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        itemStack.attemptDamageItem(1, random, null);
        return itemStack;
    }
}
