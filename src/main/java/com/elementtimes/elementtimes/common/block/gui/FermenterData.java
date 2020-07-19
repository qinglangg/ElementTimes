package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.FluidSlot;
import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileFermenter;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class FermenterData extends BaseGuiData<TileFermenter> {

    public static final FermenterData INSTANCE = new FermenterData();

    public FermenterData() {
        super(Industry.fermenter);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.product(0, 12, 37),
                ItemSlot.product(1, 66, 19),
                ItemSlot.product(2, 33, 63),
                ItemSlot.product(3, 111, 63),
                ItemSlot.product(4, 129, 63),
                ItemSlot.product(5, 147, 63),
                ItemSlot.product(6, 90, 42),
                ItemSlot.product(7, 33, 81),
                ItemSlot.product(8, 111, 81),
                ItemSlot.product(9, 129, 81),
                ItemSlot.product(10, 147, 81)
        };
    }

    @Nonnull
    @Override
    public FluidSlot[] getFluids() {
        return new FluidSlot[] {
                FluidSlot.ingredient(0, 33, 12),
                FluidSlot.ingredient(1, 111, 12),
                FluidSlot.ingredient(2, 129, 12),
                FluidSlot.ingredient(3, 147, 12)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/fermenter.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x210x127().withTitleY(115).withProcess(63, 43).withEnergy(43, 117);
    }
}
