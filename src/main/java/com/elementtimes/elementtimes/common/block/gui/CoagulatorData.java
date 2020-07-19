package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.FluidSlot;
import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileCoagulator;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class CoagulatorData extends BaseGuiData<TileCoagulator> {

    public static final CoagulatorData INSTANCE = new CoagulatorData();

    public CoagulatorData() {
        super(Industry.coagulator);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.product(0, 108, 30),
                ItemSlot.bucketIn(1, 47, 61),
                ItemSlot.bucketOut(2, 65, 61)
        };
    }

    @Nonnull
    @Override
    public FluidSlot[] getFluids() {
        return new FluidSlot[] {
                FluidSlot.ingredient(0, 56, 10)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/coagulator.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x179x97().withProcess(81, 36, 0, 196, 16, 16).withEnergy(43, 86).withTitleY(85);
    }
}
