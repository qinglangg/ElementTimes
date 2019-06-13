package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;

import javax.annotation.Nonnull;

/**
 * 成型机
 * @author luqin2007
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileForming extends BaseOneToOne {

    public TileForming() {
        super(ElementtimesConfig.FORMING.maxEnergy);
    }

    public static MachineRecipeHandler sGearRecipeHandler;

    public static void init() {
        sGearRecipeHandler = new MachineRecipeHandler()
                .add("1", 10000, "plankWood", 9, ElementtimesItems.gearWood, 3)
                .add("2", 10000, "gemQuartz", 9, ElementtimesItems.gearQuartz, 3)
                .add("3", 10000, "stone", 9, ElementtimesItems.gearStone, 3)
                .add("4", 10000, "coal", 9, ElementtimesItems.gearCarbon, 3)
                .add("5", 10000, "ingotGold", 9, ElementtimesItems.gearGold, 3)
                .add("6", 10000, "ingotSteel", 9, ElementtimesItems.gearSteel, 3)
                .add("7", 10000, "gemDiamond", 9, ElementtimesItems.gearDiamond, 3)
                .add("8", 10000, "ingotIron", 9, ElementtimesItems.gearIron, 3)
                .add("9", 10000, "ingotPlatinum", 9, ElementtimesItems.gearPlatinum, 3)
                .add("0", 10000, "ingotCopper", 9, ElementtimesItems.gearCopper, 3);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return sGearRecipeHandler;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.FORMING.maxReceive);
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.FORMING.maxExtract;
    }

}
