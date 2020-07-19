package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.FluidSlot;
import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileSolidReactor;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class SolidReactorData extends BaseGuiData<TileSolidReactor> {

    public static final SolidReactorData INSTANCE = new SolidReactorData();

    public SolidReactorData() {
        super(Industry.solidReactor);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.ingredient(0, 35, 42),
                ItemSlot.ingredient(1, 53, 42),
                ItemSlot.bucketIn(2, 126, 62),
                ItemSlot.product(3, 105, 33),
                ItemSlot.product(4, 105, 51),
                ItemSlot.bucketOut(5, 126, 80)
        };
    }

    @Nonnull
    @Override
    public FluidSlot[] getFluids() {
        return new FluidSlot[] {
                FluidSlot.product(0, 126, 11)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/solidreactor.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x204x122().withTitleY(85).withProcess(75, 42).withEnergy(43, 108);
    }
}
