package com.elementtimes.tutorial.plugin;

import amerifrance.guideapi.api.GuideAPI;
import amerifrance.guideapi.api.impl.Book;
import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.tutorial.plugin.guideapi.ElementtimesGuideBook;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.Loader;

import java.io.IOException;
import java.io.InputStreamReader;
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
        return minecraft();
    }

    /**
     * 获取所有指导书
     * @return 所有指导书
     */
    public static List<ItemStack> getAllGuideBook() {
        ArrayList<ItemStack> books = new ArrayList<>();
        books.add(minecraft());
        if (Loader.isModLoaded(MOD_PATCHOULI)) {
            books.add(patchouli());
        }
        if (Loader.isModLoaded(MOD_GUIDE_API)) {
            books.add(guideApi());
        }
        return books;
    }

    private static ItemStack minecraft() {
        /*
        { author, title, pages[ text:"\n" ] }
         */
        if (MINECRAFT_BOOK.isEmpty()) {
            MINECRAFT_BOOK = new ItemStack(Items.WRITTEN_BOOK);
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setString("author", "ElementTimes");
            nbt.setString("title", new TextComponentTranslation("elementtimes.book.minecraft").getFormattedText());
            NBTTagList pages = new NBTTagList();
            if (ECUtils.common.isClient()) {
                net.minecraft.client.Minecraft mc = net.minecraft.client.Minecraft.getMinecraft();
                String languageCode = mc.getLanguageManager().getCurrentLanguage().getLanguageCode();
                net.minecraft.client.resources.IResource bookJson;
                try {
                    bookJson = mc.getResourceManager()
                            .getResource(new ResourceLocation("elementtimes", "mc_book/" + languageCode + ".json"));
                } catch (IOException e) {
                    try {
                        bookJson = mc.getResourceManager()
                                .getResource(new ResourceLocation("elementtimes", "mc_book/en_us.json"));
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                }
                JsonArray bookObj = new JsonParser()
                        .parse(new InputStreamReader(bookJson.getInputStream()))
                        .getAsJsonArray();
                for (JsonElement jsonElement : bookObj) {
                    JsonArray pageLines = jsonElement.getAsJsonArray();
                    StringBuilder sb = new StringBuilder("{\"text\":\"");
                    for (JsonElement pageLine : pageLines) {
                        sb.append(pageLine.getAsString()).append("\\n");
                    }
                    pages.appendTag(new NBTTagString(sb.append("\"}").toString()));
                }
            }
            nbt.setTag("pages", pages);
            MINECRAFT_BOOK.setTagCompound(nbt);
        }
        return MINECRAFT_BOOK.copy();
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
        if (GUIDE_API_BOOK.isEmpty()) {
            Book book = ElementtimesGuideBook.BOOK;
            if (book != null) {
                ItemStack stack = GuideAPI.getStackFromBook(book);
                if (!stack.isEmpty()) {
                    GUIDE_API_BOOK = stack;
                }
            }
        }
        return GUIDE_API_BOOK.copy();
    }
}
