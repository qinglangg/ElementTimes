package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.FluidSlot;
import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileCentrifuge;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class CentrifugeData extends BaseGuiData<TileCentrifuge> {

    public static final CentrifugeData INSTANCE = new CentrifugeData();

    public CentrifugeData() {
        super(Industry.centrifuge);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.bucketIn(0, 18, 66),
                ItemSlot.bucketIn(1, 70, 66),
                ItemSlot.bucketIn(2, 88, 66),
                ItemSlot.bucketIn(3, 106, 66),
                ItemSlot.bucketIn(4, 124, 66),
                ItemSlot.bucketIn(5, 142, 66),
                ItemSlot.bucketOut(6, 18, 84),
                ItemSlot.bucketOut(7, 70, 84),
                ItemSlot.bucketOut(8, 88, 84),
                ItemSlot.bucketOut(9, 106, 84),
                ItemSlot.bucketOut(10, 124, 84),
                ItemSlot.bucketOut(11, 142, 84)
        };
    }

    @Nonnull
    @Override
    public FluidSlot[] getFluids() {
        return new FluidSlot[] {
                FluidSlot.ingredient(0, 18, 15),
                FluidSlot.product(1, 70, 15),
                FluidSlot.product(2, 88, 15),
                FluidSlot.product(3, 106, 15),
                FluidSlot.product(4, 124, 15),
                FluidSlot.product(5, 142, 15),
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/centrifuge.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x204x122().withProcess(40, 30).withEnergy(43, 107).withTitleY(112);
    }
}
