package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileFuelGenerator;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class FuelGeneratorData extends BaseGuiData<TileFuelGenerator> {

    public static final FuelGeneratorData INSTANCE = new FuelGeneratorData();

    public FuelGeneratorData() {
        super(Industry.generatorFuel);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.ingredient(0, 80, 30)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/0.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x156x74().withTitleY(60).withEnergy(43, 55, 0, 156, 89, 4);
    }
}
