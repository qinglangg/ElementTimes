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
 * 物质还原机
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileItemReducer extends BaseTileEntity {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler(1, 1, 0, 0)
                    .add(ElementtimesConfig.ITEM_REDUCER.powderEnergy, ElementtimesItems.bambooCharcoal, 1, ElementtimesItems.bamboo, 4)
                    .add(ElementtimesConfig.ITEM_REDUCER.powderEnergy, ElementtimesItems.sucroseCharCoal, 1, Items.REEDS, 4)
                    .add(ElementtimesConfig.ITEM_REDUCER.powderEnergy, "blockTin", 1, ElementtimesItems.tin, 9)
                    .add(ElementtimesConfig.ITEM_REDUCER.powderEnergy, "blockLead", 1, ElementtimesItems.lead, 9)
                    .add(ElementtimesConfig.ITEM_REDUCER.powderEnergy, "blockCopper", 1, ElementtimesItems.copper, 9)
                    .add(ElementtimesConfig.ITEM_REDUCER.powderEnergy, "blockPlatinum", 1, ElementtimesItems.platinumIngot, 9)
                    .add(ElementtimesConfig.ITEM_REDUCER.powderEnergy, "blockSilver", 1, ElementtimesItems.silver, 9)
                    .add(ElementtimesConfig.ITEM_REDUCER.powderEnergy, "blockSteel", 1, ElementtimesItems.steelIngot, 9)
                    .add(ElementtimesConfig.ITEM_REDUCER.powderEnergy, ElementtimesBlocks.diamondBlock, 1, ElementtimesItems.diamondIngot, 9)
            ;
        }
    }

    public TileItemReducer() {
        super(ElementtimesConfig.ITEM_REDUCER.maxEnergy, 1, 1);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return RECIPE;
    }

    @Override
    public void applyConfig() {
        setEnergyTransfer(ElementtimesConfig.ITEM_REDUCER.maxReceive);
    }

    @Override
    public int getEnergyTick() {
        return ElementtimesConfig.ITEM_REDUCER.maxExtract;
    }

    @Override
    public int getGuiId() {
        return ElementtimesGUI.Machines.ItemReducer.id();
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
        return ElementtimesBlocks.itemReducer.getLocalizedName();

    }

    @Override
    public void update() {
        update(this);
    }
}
