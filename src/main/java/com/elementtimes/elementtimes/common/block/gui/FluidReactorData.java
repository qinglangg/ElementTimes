package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.FluidSlot;
import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileFluidReactor;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class FluidReactorData extends BaseGuiData<TileFluidReactor> {

    public static final FluidReactorData INSTANCE = new FluidReactorData();

    public FluidReactorData() {
        super(Industry.fluidReactor);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.bucketIn(0, 18, 66),
                ItemSlot.bucketIn(1, 36, 66),
                ItemSlot.bucketIn(2, 106, 66),
                ItemSlot.bucketIn(3, 124, 66),
                ItemSlot.bucketIn(4, 142, 66),
                ItemSlot.product(5, 88, 30),
                ItemSlot.bucketOut(6, 18, 84),
                ItemSlot.bucketOut(7, 36, 84),
                ItemSlot.bucketOut(8, 106, 84),
                ItemSlot.bucketOut(9, 124, 84),
                ItemSlot.bucketOut(10, 142, 84)
        };
    }

    @Nonnull
    @Override
    public FluidSlot[] getFluids() {
        return new FluidSlot[] {
                FluidSlot.ingredient(0, 18, 15),
                FluidSlot.ingredient(1, 36, 15),
                FluidSlot.product(2, 106, 15),
                FluidSlot.product(3, 124, 15),
                FluidSlot.product(4, 142, 15)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/fluidreactor.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x204x122().withTitleY(100).withProcess(58, 30).withEnergy(43, 108);
    }
}
