package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;

import javax.annotation.Nonnull;

/**
 * 提取机
 * 未完成
 * @author luqin2007
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileExtractor extends BaseOneToOne {

    public TileExtractor() {
        super(ElementtimesConfig.EXTRACTOR.maxEnergy);
    }

    public static MachineRecipeHandler sRecipeHandler;

    public static void init() {
        sRecipeHandler = new MachineRecipeHandler()
                .add("0", 1000, ElementtimesBlocks.rubberLeaf, 1, ElementtimesItems.rubberRaw, 1)
                .add("1", 4000, ElementtimesBlocks.rubberLog, 1, ElementtimesItems.rubberRaw, 4)
                .add("2", 2000, ElementtimesBlocks.rubberSapling, 1, ElementtimesItems.rubberRaw, 2);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return sRecipeHandler;
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.EXTRACTOR.maxExtract;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.EXTRACTOR.maxReceive);
    }
}

