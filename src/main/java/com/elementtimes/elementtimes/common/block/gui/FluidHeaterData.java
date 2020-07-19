package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.FluidSlot;
import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileFluidHeater;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class FluidHeaterData extends BaseGuiData<TileFluidHeater> {

    public static final FluidHeaterData INSTANCE = new FluidHeaterData();

    public FluidHeaterData() {
        super(Industry.fluidHeater);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                 ItemSlot.bucketIn(0, 8, 83),
                 ItemSlot.bucketIn(1, 136, 83),
                ItemSlot.bucketOut(2, 26, 83),
                ItemSlot.bucketOut(3, 152, 83)
        };
    }

    @Nonnull
    @Override
    public FluidSlot[] getFluids() {
        return new FluidSlot[] {
                FluidSlot.ingredient(0, 17, 25),
                FluidSlot.product(1, 143, 25)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/fluidheater.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x204x122().withTitleY(100)
                .withProcess(53, 18, 0, 204, 70, 19)
                .withProcess(57, 52, 0, 223, 62, 15)
                .withEnergy(45, 89, 0, 237, 90, 4);
    }
}
