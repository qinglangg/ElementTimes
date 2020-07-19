package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.FluidSlot;
import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileSolidMelter;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;



public class SolidMelterData extends BaseGuiData<TileSolidMelter> {

    public static final SolidMelterData INSTANCE = new SolidMelterData();

    public SolidMelterData() {
        super(Industry.solidMelter);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.ingredient(0, 45, 31),
                ItemSlot.bucketIn(1, 116, 28),
                ItemSlot.bucketOut(2, 116, 46)
        };
    }

    @Nonnull
    @Override
    public FluidSlot[] getFluids() {
        return new FluidSlot[] {
                FluidSlot.product(0, 95, 16)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/solidmelter.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x166x84().withTitleY(4).withProcess(65, 31).withEnergy(43, 72);
    }
}
