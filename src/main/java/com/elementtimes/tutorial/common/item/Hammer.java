package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.Elementtimes;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class Hammer extends Item {

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        NBTTagCompound bind = itemStack.getOrCreateSubCompound(Elementtimes.MODID + "_bind");
        int d = 1;
        if (bind.hasKey("damage"))
            d = bind.getInteger("damage");
        ItemStack container = itemStack.copy();
        container.removeSubCompound(Elementtimes.MODID + "_bind");
        container.attemptDamageItem(d, itemRand, null);
        return container;
    }
}
