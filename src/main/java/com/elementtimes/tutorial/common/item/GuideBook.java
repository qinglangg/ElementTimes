package com.elementtimes.tutorial.common.item;

import com.elementtimes.elementcore.api.annotation.tools.ModBook;
import com.elementtimes.elementcore.api.book.Book;
import com.elementtimes.elementcore.api.book.ItemBook;
import com.elementtimes.elementcore.api.book.Page;
import com.elementtimes.elementcore.api.book.screen.Text;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.init.ElementtimesTabs;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;

@ModBook
public class GuideBook extends Book {

    public GuideBook() {
        super(new ResourceLocation(ElementTimes.MODID, "guidebook"));
        setNameTranslation("elementtimes.book.name");
        addTooltipTranslations("elementtimes.book.tooltip");
        addShapelessRecipe("recipes", Items.BOOK, ElementtimesItems.rubber);

        Page page0 = new Page(this);
        page0.add(new Text(new TextComponentTranslation("elementtimes.book.page0.line0")));
        page0.add(new Text(new TextComponentTranslation("elementtimes.book.page0.line1")));
        page0.add(new Text(new TextComponentTranslation("elementtimes.book.page0.line2")));
        Page page1 = new Page(this);
        page1.add(new Text(new TextComponentTranslation("elementtimes.book.page1.line0")));
        page1.add(new Text(new TextComponentTranslation("elementtimes.book.page1.line1")));
        page1.add(new Text(new TextComponentTranslation("elementtimes.book.page1.line2")));
        page1.add(new Text(new TextComponentTranslation("elementtimes.book.page1.line3")));
        page1.add(new Text(new TextComponentTranslation("elementtimes.book.page1.line4")));
        page1.add(new Text(new TextComponentTranslation("elementtimes.book.page1.line5")));
        page1.add(new Text(new TextComponentTranslation("elementtimes.book.page1.line6")));
        page1.add(new Text(new TextComponentTranslation("elementtimes.book.page1.line7")));
        Page page2 = new Page(this);
        page2.add(new Text(new TextComponentTranslation("elementtimes.book.page2.line0")));
        page2.add(new Text(new TextComponentTranslation("elementtimes.book.page2.line1")));
        page2.add(new Text(new TextComponentTranslation("elementtimes.book.page2.line2")));
        page2.add(new Text(new TextComponentTranslation("elementtimes.book.page2.line3")));
        Page page3 = new Page(this);
        page3.add(new Text(new TextComponentTranslation("elementtimes.book.page3.line0")));
        page3.add(new Text(new TextComponentTranslation("elementtimes.book.page3.line1")));
        page3.add(new Text(new TextComponentTranslation("elementtimes.book.page3.line2")));
        page3.add(new Text(new TextComponentTranslation("elementtimes.book.page3.line3")));
        page3.add(new Text(new TextComponentTranslation("elementtimes.book.page3.line4")));
        Page page4 = new Page(this);
        page4.add(new Text(new TextComponentTranslation("elementtimes.book.page4.line0")));
        addPages(page0, page1, page2, page3, page4);
    }

    @Override
    protected ItemBook decorateItem(ItemBook book) {
        super.decorateItem(book);
        book.setCreativeTab(ElementtimesTabs.Main);
        return book;
    }
}
