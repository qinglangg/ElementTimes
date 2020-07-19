package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.FluidSlot;
import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileElectrolyticCell;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class ElectrolyticCellData extends BaseGuiData<TileElectrolyticCell> {

    public static final ElectrolyticCellData INSTANCE = new ElectrolyticCellData();

    public ElectrolyticCellData() {
        super(Industry.electrolyticCell);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.bucketIn(0, 36, 63),
                ItemSlot.bucketIn(1, 88, 63),
                ItemSlot.bucketIn(2, 106, 63),
                ItemSlot.bucketIn(3, 124, 63),
                ItemSlot.bucketOut(4, 36, 81),
                ItemSlot.bucketOut(5, 88, 81),
                ItemSlot.bucketOut(6, 106, 81),
                ItemSlot.bucketOut(7, 124, 81)
        };
    }

    @Nonnull
    @Override
    public FluidSlot[] getFluids() {
        return new FluidSlot[] {
                FluidSlot.ingredient(0, 36, 12),
                FluidSlot.product(1, 88, 12),
                FluidSlot.product(2, 106, 12),
                FluidSlot.product(3, 124, 12),
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/electrolyticcell.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x204x122().withTitleY(108)
                .withProcess(58, 20, 0, 204, 24, 17)
                .withProcess(63, 35, 0, 221, 16, 16)
                .withEnergy(43, 107, 24, 204, 90, 4);
    }
}
