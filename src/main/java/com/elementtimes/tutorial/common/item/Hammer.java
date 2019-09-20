package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import javax.annotation.Nonnull;

/**
 * 大小锤子
 * 唯一指定调试工具
 * @author luqin2007
 */
public class Hammer extends Item {

    public static final String TAG_DAMAGE = "damage";
    public static final String TAG_REMOVE = "damage";

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == ElementtimesItems.diamondIngot;
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        NBTTagCompound bind = itemStack.getOrCreateSubCompound(ElementTimes.MODID + "_bind");
        int d = 1;
        if (bind.hasKey(TAG_DAMAGE)) {
            d = bind.getInteger(TAG_DAMAGE);
        }
        ItemStack container = itemStack.copy();
        if (bind.getBoolean(TAG_REMOVE)) {
            container.setTagCompound(null);
        } else {
            container.removeSubCompound(ElementTimes.MODID + "_bind");
        }
        container.attemptDamageItem(d, itemRand, null);

        return container;
    }
}
