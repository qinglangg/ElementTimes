package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.FluidSlot;
import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileCondenser;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class CondenserData extends BaseGuiData<TileCondenser> {

    public static final CondenserData INSTANCE = new CondenserData();

    public CondenserData() {
        super(Industry.condenser);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.bucketIn(0, 8, 83),
                ItemSlot.bucketIn(1, 134, 83),
                ItemSlot.bucketOut(2, 26, 83),
                ItemSlot.bucketOut(3, 151, 83)
        };
    }

    @Nonnull
    @Override
    public FluidSlot[] getFluids() {
        return new FluidSlot[] {
                FluidSlot.ingredient(0, 17, 25),
                FluidSlot.product(1, 143, 25),
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/condenser.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x204x122().withProcess(54, 18, 0, 204, 70, 20)
                .withProcess(55, 52, 0, 224, 64, 15)
                .withEnergy(45, 89, 0, 239, 90, 4)
                .withTitleY(115);
    }
}
