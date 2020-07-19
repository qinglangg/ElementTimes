package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TilePulverizer;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



public class PulverizerData extends BaseGuiData<TilePulverizer> {

    public static final PulverizerData INSTANCE = new PulverizerData();

    public PulverizerData() {
        super(Industry.pulverizer);
    }

    @Nonnull
    @Override
    public ItemSlot[] getSlots() {
        return new ItemSlot[] {
                ItemSlot.ingredient(0, 56, 30),
                ItemSlot.product(1, 110, 30)
        };
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/5.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x156x74().withTitleY(60)
                .withProcess(80, 30, 0, 156, 24, 17)
                .withEnergy(43, 55, 24, 156, 90, 4);
    }
}
