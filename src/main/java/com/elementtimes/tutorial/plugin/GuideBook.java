package com.elementtimes.tutorial.plugin;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fml.common.Loader;

import java.util.ArrayList;
import java.util.List;

/**
 * 指导书兼容
 * 目前只加入了 Patchouli，以后考虑加入 GuideAPI，FTBGuides 等兼容
 * @author luqin2007
 */
public class GuideBook {

    private static ItemStack MINECRAFT_BOOK = ItemStack.EMPTY;
    private static ItemStack PATCHOULI_BOOK = ItemStack.EMPTY;
    private static ItemStack GUIDE_API_BOOK = ItemStack.EMPTY;
    private static final String MOD_GUIDE_API = "guideapi";
    private static final String MOD_PATCHOULI = "patchouli";

    /**
     * 获取指导书
     * 若存在其他 mod，则获取 mod 的书，否则获取 mc 成书
     * @return 指导书
     */
    public static ItemStack getGuideBook() {
        if (Loader.isModLoaded(MOD_PATCHOULI)) {
            return patchouli();
        } else if (Loader.isModLoaded(MOD_GUIDE_API)) {
            return guideApi();
        }
        return ItemStack.EMPTY;
    }

    /**
     * 获取所有指导书
     * @return 所有指导书
     */
    public static List<ItemStack> getAllGuideBook() {
        ArrayList<ItemStack> books = new ArrayList<>();
        if (Loader.isModLoaded(MOD_PATCHOULI)) {
            books.add(patchouli());
        }
        if (Loader.isModLoaded(MOD_GUIDE_API)) {
            books.add(guideApi());
        }
        return books;
    }

    private static ItemStack patchouli() {
        if (PATCHOULI_BOOK.isEmpty()) {
            PATCHOULI_BOOK = new ItemStack(Item.getByNameOrId("patchouli:guide_book"));
            if (!PATCHOULI_BOOK.hasTagCompound()) {
                PATCHOULI_BOOK.setTagCompound(new NBTTagCompound());
            }
            PATCHOULI_BOOK.getTagCompound().setString("patchouli:book", "elementtimes:guide");
        }
        return PATCHOULI_BOOK.copy();
    }

    private static ItemStack guideApi() {
//        if (GUIDE_API_BOOK.isEmpty()) {
//            amerifrance.guideapi.api.impl.Book book =
//                    com.elementtimes.tutorial.plugin.guideapi.ElementtimesGuideBook.BOOK;
//            if (book != null) {
//                ItemStack stack = amerifrance.guideapi.api.GuideAPI.getStackFromBook(book);
//                if (!stack.isEmpty()) {
//                    GUIDE_API_BOOK = stack;
//                }
//            }
//        }
        return GUIDE_API_BOOK.copy();
    }
}
