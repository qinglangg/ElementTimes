package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.FluidSlot;
import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileFluidGenerator;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class FluidGeneratorData extends BaseGuiData<TileFluidGenerator> {

    public static final FluidGeneratorData INSTANCE = new FluidGeneratorData();

    public FluidGeneratorData() {
        super(Industry.generatorFluid);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.bucketIn(0, 71, 41),
                ItemSlot.bucketOut(1, 89, 41),
        };
    }

    @Nonnull
    @Override
    public FluidSlot[] getFluids() {
        return new FluidSlot[] {
                FluidSlot.ingredient(0, 65, 20),
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/fluidgenerator.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x164x82().withTitleY(71).withEnergy(45, 66, 24, 164, 86, 4);
    }
}
