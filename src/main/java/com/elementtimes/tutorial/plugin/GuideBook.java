package com.elementtimes.tutorial.plugin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;

/**
 * 指导书兼容
 * 目前只加入了 Patchouli，以后考虑加入 GuideAPI，FTBGuides 等兼容
 * @author luqin2007
 */
public class GuideBook {

    public static ItemStack patchouliBook() {
        if (Loader.isModLoaded("patchouli")) {
            ItemStack book = new ItemStack(Item.getByNameOrId("patchouli:guide_book"));
            if (!book.hasTagCompound()) {
                book.setTagCompound(new NBTTagCompound());
            }
            book.getTagCompound().setString("patchouli:book", "elementtimes:guide");
            return book;
        }
        return ItemStack.EMPTY;
    }
}
