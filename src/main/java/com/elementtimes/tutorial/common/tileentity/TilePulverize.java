package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.MachineRecipeHandler;

import javax.annotation.Nonnull;

/**
 * 打粉机
 * @author KSGFK create in 2019/5/6
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TilePulverize extends BaseOneToOne {

    public TilePulverize() {
        super(ElementtimesConfig.PUL.pulMaxEnergy);
    }

    public static MachineRecipeHandler sRecipeHandler;

    public static void init() {
        sRecipeHandler = new MachineRecipeHandler()
                .set("0", ElementtimesConfig.PUL.pulPowderEnergy, "oreIron", 1, ElementtimesItems.ironPower, ElementtimesConfig.PUL.pulPowderCount)
                .set("1", ElementtimesConfig.PUL.pulPowderEnergy, "oreRedstone", 1, ElementtimesItems.redstonePowder, ElementtimesConfig.PUL.pulPowderCount)
                .set("2", ElementtimesConfig.PUL.pulPowderEnergy, "oreGold", 1, ElementtimesItems.goldPowder, ElementtimesConfig.PUL.pulPowderCount)
                .set("3", ElementtimesConfig.PUL.pulPowderEnergy, "oreDiamond", 1, ElementtimesItems.diamondPowder, ElementtimesConfig.PUL.pulPowderCount)
                .set("4", ElementtimesConfig.PUL.pulPowderEnergy, "oreLapis", 1, ElementtimesItems.bluestonePowder, ElementtimesConfig.PUL.pulPowderCount)
                .set("5", ElementtimesConfig.PUL.pulPowderEnergy, "oreEmerald", 1, ElementtimesItems.greenstonePowder, ElementtimesConfig.PUL.pulPowderCount)
                .set("6", ElementtimesConfig.PUL.pulPowderEnergy, "oreCopper", 1, ElementtimesItems.copperPowder, ElementtimesConfig.PUL.pulPowderCount)
                .set("7", ElementtimesConfig.PUL.pulPowderEnergy, "oreCoal", 1, ElementtimesItems.coalPowder, ElementtimesConfig.PUL.pulPowderCount)
                .set("8", ElementtimesConfig.PUL.pulPowderEnergy, "orePlatinum", 1, ElementtimesItems.platinumOrePowder, ElementtimesConfig.PUL.pulPowderCount);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return sRecipeHandler;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.PUL.pulMaxReceive);
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.PUL.pulMaxExtract;
    }
}
