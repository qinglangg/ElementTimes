package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.ModElement;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.MachineRecipeHandler;

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
        super(ElementtimesConfig.FURNACE.maxEnergy);
    }

    public static MachineRecipeHandler sRecipeHandler;

    public static void init() {
        sRecipeHandler = new MachineRecipeHandler();
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return sRecipeHandler;
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.FURNACE.maxExtract;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.FURNACE.maxReceive);
    }
}

