package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileExtractor;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class ExtractorData extends BaseGuiData<TileExtractor> {

    public static final ExtractorData INSTANCE = new ExtractorData();

    public ExtractorData() {
        super(Industry.extractor);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.ingredient(0, 55, 31),
                ItemSlot.product(1, 109, 15),
                ItemSlot.product(2, 109, 33),
                ItemSlot.product(3, 109, 51)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/solidcentrifuge.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x166x84().withTitleY(72)
                .withProcess(80, 31, 0, 183, 16, 16)
                .withEnergy(43, 73, 24, 166, 90, 4);
    }
}
