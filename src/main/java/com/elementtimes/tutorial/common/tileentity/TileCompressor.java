package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import net.minecraft.init.Items;

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
                .add("0", ElementtimesConfig.COMPRESSOR.powderEnergy, "logWood", 1, ElementtimesItems.platewood, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("1", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotCopper", 1, ElementtimesItems.plateCopper, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("2", ElementtimesConfig.COMPRESSOR.powderEnergy, "gemDiamond", 1, ElementtimesItems.plateDiamond, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("3", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotGold", 1, ElementtimesItems.plateGold, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("4", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotIron", 1, ElementtimesItems.plateIron, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("5", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotPlatinum", 1, ElementtimesItems.platePlatinum, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("6", ElementtimesConfig.COMPRESSOR.powderEnergy, "gemQuartz", 1, ElementtimesItems.plateQuartz, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("7", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotSteel", 1, ElementtimesItems.plateSteel, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("8", ElementtimesConfig.COMPRESSOR.powderEnergy, "stone", 1, ElementtimesItems.plateStone, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("9", ElementtimesConfig.COMPRESSOR.powderEnergy, Items.COAL, 1, ElementtimesItems.plateCarbon, ElementtimesConfig.COMPRESSOR.powderCount)
                .add("10", ElementtimesConfig.COMPRESSOR.powderEnergy, Items.BLAZE_ROD, 1, Items.BLAZE_POWDER, 8)
                .add("11", ElementtimesConfig.COMPRESSOR.powderEnergy, "ingotSilver", 1, ElementtimesItems.plateSilver, ElementtimesConfig.COMPRESSOR.powderCount);
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
