package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.MachineRecipeHandler;
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
                .set("0", 4000, ElementtimesItems.starchPowder, 1, new ItemStack(ElementtimesItems.sucroseCharCoal), 1)
                .set("1", 4000, ElementtimesItems.amylum, 2, ElementtimesItems.sucroseCharCoal, 1)
                .set("2", 4000, ElementtimesItems.sucroseCharCoal, 2, new ItemStack(Items.COAL, 1, 1), 1)
                .set("3", 4000, new ItemStack(Items.COAL, 1, 1), 1, new ItemStack(Items.COAL, 1, 0), 1)
                .set("4", 50000, new ItemStack(Items.COAL, 1, 0), 1, Items.DIAMOND, 1)
                .set("5", 50000, Blocks.DIAMOND_BLOCK, 1, ElementtimesItems.diamondIngot, 1)
                .set("6", 100000, Blocks.DIRT, 1, Blocks.FARMLAND, 1)
                .set("7", 100000, Blocks.GRASS, 1, Blocks.GRASS_PATH, 1)
                .set("8", 20000, Blocks.SAND, 1, Blocks.SOUL_SAND, 1)
                .set("9", 20000, Blocks.GLASS, 1, Items.QUARTZ, 1);
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
}
