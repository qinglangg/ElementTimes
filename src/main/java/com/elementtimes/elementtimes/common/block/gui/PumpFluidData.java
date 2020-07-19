package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.FluidSlot;
import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TilePumpFluid;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class PumpFluidData extends BaseGuiData<TilePumpFluid> {

    public static final PumpFluidData INSTANCE = new PumpFluidData();

    public PumpFluidData() {
        super(Industry.pumpFluid);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.bucketIn(0, 56, 58),
                ItemSlot.bucketOut(1, 98, 58)
        };
    }

    @Nonnull
    @Override
    public FluidSlot[] getFluids() {
        return new FluidSlot[] {
                FluidSlot.ingredient(0, 77, 28)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/pump.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x204x122().withTitleY(85).withEnergy(43, 108, 24, 204, 90, 4);
    }
}
