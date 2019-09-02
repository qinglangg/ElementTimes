package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.machineRecipe.MachineRecipeHandler;
import net.minecraft.init.Items;

import javax.annotation.Nonnull;

/**
 * 提取机
 * @author luqin2007
 */
@ModInvokeStatic("init")
public class TileExtractor extends BaseOneToOne {

    public TileExtractor() {
        super(ElementtimesConfig.EXTRACTOR.maxEnergy);
    }

    public static MachineRecipeHandler sRecipeHandler;

    public static void init() {
        sRecipeHandler = new MachineRecipeHandler()
                .add("0", 1000, ElementtimesBlocks.rubberLeaf, 1, ElementtimesItems.rubberRaw, 1)
                .add("1", 4000, ElementtimesBlocks.rubberLog, 1, ElementtimesItems.rubberRaw, 4)
                .add("2", 2000, ElementtimesBlocks.rubberSapling, 1, ElementtimesItems.rubberRaw, 2)
                .add("3", 2000, Items.GUNPOWDER, 1, ElementtimesItems.sulfurPowder, 1);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler createRecipe() {
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

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.Extractor;
    }
}

