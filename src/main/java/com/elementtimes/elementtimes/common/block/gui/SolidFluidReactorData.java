package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.FluidSlot;
import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileSolidFluidReactor;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class SolidFluidReactorData extends BaseGuiData<TileSolidFluidReactor> {

    public static final SolidFluidReactorData INSTANCE = new SolidFluidReactorData();

    public SolidFluidReactorData() {
        super(Industry.solidFluidReactor);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.ingredient(0, 22, 30),
                ItemSlot.bucketIn(1, 43, 66),
                ItemSlot.bucketIn(2, 116, 66),
                ItemSlot.bucketIn(3, 137, 66),
                ItemSlot.product(4, 95, 22),
                ItemSlot.product(5, 95, 40),
                ItemSlot.bucketOut(6, 43, 84),
                ItemSlot.bucketOut(7, 116, 84),
                ItemSlot.bucketOut(8, 137, 84)
        };
    }

    @Nonnull
    @Override
    public FluidSlot[] getFluids() {
        return new FluidSlot[] {
                FluidSlot.ingredient(0, 43, 15),
                FluidSlot.product(1, 116, 15),
                FluidSlot.product(2, 137, 15)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/solidfluidreactor.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x204x122().withTitleY(14).withProcess(65, 30).withEnergy(43, 108);
    }
}
