package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.annotations.ModInvokeStatic;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.machineRecipe.MachineRecipeHandler;
import net.minecraft.init.Items;

import javax.annotation.Nonnull;

/**
 * 物质还原机
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileItemReducer extends BaseOneToOne {

    public static MachineRecipeHandler RECIPE = null;

    public static void init() {
        if (RECIPE == null) {
            RECIPE = new MachineRecipeHandler()
                    .add("0", ElementtimesConfig.ITEM_REDUCER.powderEnergy, ElementtimesItems.bambooCharcoal, 1, ElementtimesItems.bamboo, 4)
                    .add("1", ElementtimesConfig.ITEM_REDUCER.powderEnergy, ElementtimesItems.sucroseCharCoal, 1, Items.REEDS, 4)
                    .add("b0", ElementtimesConfig.ITEM_REDUCER.powderEnergy, "blockTin", 1, ElementtimesItems.tin, 9)
                    .add("b1", ElementtimesConfig.ITEM_REDUCER.powderEnergy, "blockLead", 1, ElementtimesItems.lead, 9)
                    .add("b2", ElementtimesConfig.ITEM_REDUCER.powderEnergy, "blockCopper", 1, ElementtimesItems.copper, 9)
                    .add("b3", ElementtimesConfig.ITEM_REDUCER.powderEnergy, "blockPlatinum", 1, ElementtimesItems.platinumIngot, 9)
                    .add("b4", ElementtimesConfig.ITEM_REDUCER.powderEnergy, "blockSilver", 1, ElementtimesItems.silver, 9)
                    .add("b5", ElementtimesConfig.ITEM_REDUCER.powderEnergy, "blockSteel", 1, ElementtimesItems.steelIngot, 9)
                    .add("b6", ElementtimesConfig.ITEM_REDUCER.powderEnergy, ElementtimesBlocks.diamondBlock, 1, ElementtimesItems.diamondIngot, 9)
            ;
        }
    }

    TileItemReducer() {
        super(ElementtimesConfig.ITEM_REDUCER.maxEnergy);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler createRecipe() {
        return RECIPE;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.ITEM_REDUCER.maxReceive);
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.ITEM_REDUCER.maxExtract;
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.ItemReducer;
    }
}
