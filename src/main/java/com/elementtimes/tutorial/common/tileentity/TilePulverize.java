package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.annotations.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;

import net.minecraft.init.Items;

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
                .add("0", ElementtimesConfig.PUL.pulPowderEnergy, "oreIron", 1, ElementtimesItems.ironPowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("1", ElementtimesConfig.PUL.pulPowderEnergy, "oreRedstone", 1, ElementtimesItems.redstonePowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("2", ElementtimesConfig.PUL.pulPowderEnergy, "oreGold", 1, ElementtimesItems.goldPowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("3", ElementtimesConfig.PUL.pulPowderEnergy, "oreDiamond", 1, ElementtimesItems.diamondPowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("4", ElementtimesConfig.PUL.pulPowderEnergy, "oreLapis", 1, ElementtimesItems.bluestonePowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("5", ElementtimesConfig.PUL.pulPowderEnergy, "oreEmerald", 1, ElementtimesItems.greenstonePowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("6", ElementtimesConfig.PUL.pulPowderEnergy, "oreCopper", 1, ElementtimesItems.copperPowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("7", ElementtimesConfig.PUL.pulPowderEnergy, "oreCoal", 1, ElementtimesItems.coalPowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("8", ElementtimesConfig.PUL.pulPowderEnergy, "orePlatinum", 1, ElementtimesItems.platinumOrePowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("9", ElementtimesConfig.PUL.pulPowderEnergy, "oreSilver", 1, ElementtimesItems.silverPowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("10", ElementtimesConfig.PUL.pulPowderEnergy, "oreQuartz", 1, ElementtimesItems.quartzPowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("11", ElementtimesConfig.PUL.pulPowderEnergy, "oreSulfur", 1, ElementtimesItems.sulfurOrePowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("12", ElementtimesConfig.PUL.pulPowderEnergy, "oreLead", 1, ElementtimesItems.leadPowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("13", ElementtimesConfig.PUL.pulPowderEnergy, "oreTin", 1, ElementtimesItems.tinPowder, ElementtimesConfig.PUL.pulPowderCount)
                .add("100",ElementtimesConfig.PUL.pulPowderEnergy, Items.BLAZE_ROD, 1, Items.BLAZE_POWDER, 8);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler createRecipe() {
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

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.Pulverize;
    }
}
