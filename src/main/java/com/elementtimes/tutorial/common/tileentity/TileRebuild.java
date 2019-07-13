package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.annotations.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * 物质重构机
 * @author luqin2007
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileRebuild extends BaseOneToOne {

    public static MachineRecipeHandler sRecipeHandler;

    public static void init() {
        sRecipeHandler = new MachineRecipeHandler()
                .add("0", 4000, ElementtimesItems.starchPowder, 1, new ItemStack(ElementtimesItems.sucroseCharCoal, 1))
                .add("1", 100000, new ItemStack(Items.GOLDEN_APPLE, 1, 1), Items.TOTEM_OF_UNDYING, 1)
                .add("2", 4000, ElementtimesItems.sucroseCharCoal, 2, new ItemStack(Items.COAL, 1, 1))
                .add("3", 4000, new ItemStack(Items.COAL, 1, 1), new ItemStack(Items.COAL, 1, 0))
                .add("4", 50000, new ItemStack(Items.COAL, 1, 0), Items.DIAMOND, 1)
                .add("5", 50000, Blocks.DIAMOND_BLOCK, 1, ElementtimesItems.diamondIngot, 1)
                .add("6", 100000, Blocks.DIRT, 1, Blocks.FARMLAND, 1)
                .add("7", 100000, Blocks.GRASS, 1, Blocks.GRASS_PATH, 1)
                .add("8", 20000, Blocks.SAND, 1, Blocks.SOUL_SAND, 1)
                .add("9", 20000, Blocks.GLASS, 1, Items.QUARTZ, 1)
                .add("10", 2000, ElementtimesItems.sucroseCharCoal,1, ElementtimesItems.bambooCharcoal, 1)
                .add("11", 2000, ElementtimesItems.bambooCharcoal,1, ElementtimesItems.sucroseCharCoal, 1);
    }

    public TileRebuild() {
        super(ElementtimesConfig.REBUILD.maxEnergy);
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return sRecipeHandler;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.REBUILD.maxReceive);
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.REBUILD.maxExtract;
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return ElementtimesGUI.Machines.Rebuild;
    }
}
