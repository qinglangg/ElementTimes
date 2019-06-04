package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.MachineRecipeHandler;

import javax.annotation.Nonnull;

/**
 * 压缩机
 * @author lq2007 create in 2019/5/19
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileCompressor extends BaseOneToOne {
    public TileCompressor() {
        super(ElementtimesConfig.COMPRESSOR.maxEnergy);
    }

    public static MachineRecipeHandler sRecipeHandler;

    public static void init() {
        sRecipeHandler = new MachineRecipeHandler()
                .set("0", ElementtimesConfig.COMPRESSOR.powderEnergy, "logWood", 1, ElementtimesItems.platewood, ElementtimesConfig.COMPRESSOR.powderCount)
                .set("1", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotCopper", 1, ElementtimesItems.plateCopper, ElementtimesConfig.COMPRESSOR.powderCount)
                .set("2", ElementtimesConfig.COMPRESSOR.powderEnergy, "gemDiamond", 1, ElementtimesItems.plateDiamond, ElementtimesConfig.COMPRESSOR.powderCount)
                .set("3", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotGold", 1, ElementtimesItems.plateGold, ElementtimesConfig.COMPRESSOR.powderCount)
                .set("4", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotIron", 1, ElementtimesItems.plateIron, ElementtimesConfig.COMPRESSOR.powderCount)
                .set("5", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotPlatinum", 1, ElementtimesItems.platePlatinum, ElementtimesConfig.COMPRESSOR.powderCount)
                .set("6", ElementtimesConfig.COMPRESSOR.powderEnergy, "gemQuartz", 1, ElementtimesItems.plateQuartz, ElementtimesConfig.COMPRESSOR.powderCount)
                .set("7", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotSteel", 1, ElementtimesItems.plateSteel, ElementtimesConfig.COMPRESSOR.powderCount)
                .set("8", ElementtimesConfig.COMPRESSOR.powderEnergy, "stone", 1, ElementtimesItems.plateStone, ElementtimesConfig.COMPRESSOR.powderCount)
                .set("9", ElementtimesConfig.COMPRESSOR.powderEnergy, ElementtimesItems.sucroseCharCoal, 1, ElementtimesItems.plateCarbon, ElementtimesConfig.COMPRESSOR.powderCount);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return sRecipeHandler;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.COMPRESSOR.maxReceive);
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.COMPRESSOR.maxExtract;
    }
}
