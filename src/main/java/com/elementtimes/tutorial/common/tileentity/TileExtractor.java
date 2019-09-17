package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.init.Items;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;

/**
 * 提取机
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileExtractor extends BaseTileEntity {

    public TileExtractor() {
        super(ElementtimesConfig.EXTRACTOR.maxEnergy, 1, 1);
    }

    public static MachineRecipeHandler RECIPE;

    public static void init() {
        RECIPE = new MachineRecipeHandler(1, 1, 0, 0)
                .add(1000, ElementtimesBlocks.rubberLeaf, 1, ElementtimesItems.rubberRaw, 1)
                .add(4000, ElementtimesBlocks.rubberLog, 1, ElementtimesItems.rubberRaw, 4)
                .add(2000, ElementtimesBlocks.rubberSapling, 1, ElementtimesItems.rubberRaw, 2)
                .add(2000, Items.GUNPOWDER, 1, ElementtimesItems.sulfurPowder, 1);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public int getEnergyTick() {
        return ElementtimesConfig.EXTRACTOR.maxExtract;
    }

    @Override
    public void applyConfig() {
        setEnergyTransfer(ElementtimesConfig.EXTRACTOR.maxReceive);
    }

    @Override
    @Nonnull
    public Slot[] getSlots() {
        return new Slot[]{new SlotItemHandler(this.getItemHandler(SideHandlerType.INPUT), 0, 56, 30), new SlotItemHandler(this.getItemHandler(SideHandlerType.OUTPUT), 0, 110, 30)};
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/5.png");
    }

    @Override
    public GuiSize getSize() {
        return GUI_SIZE_176_156_74.copy().withTitleY(60)
                .withProcess(80, 30, 0, 156, 24, 17)
                .withEnergy(43, 55, 24, 156, 90, 4);
    }

    @Override
    public String getTitle() {
        return ElementtimesBlocks.extractor.getLocalizedName();

    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.Extractor.id();
    }

    @Override
    public void update() {
        update(this);
    }
}

