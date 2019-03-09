package com.elementtimes.tutorial.common.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {
    public static void init() {
        GameRegistry.addSmelting(Blocks.STONE, new ItemStack(ElementtimesItems.Stoneingot, 1), 2.0f);
        GameRegistry.addSmelting(ElementtimesBlocks.Stoneblock, new ItemStack(ElementtimesItems.Calciumcarbonate, 1), 3.0f);
        GameRegistry.addSmelting(ElementtimesItems.Calciumcarbonate, new ItemStack(ElementtimesItems.Calciumoxide, 2), 5.0f);
        GameRegistry.addSmelting(ElementtimesBlocks.Copperore, new ItemStack(ElementtimesItems.Copper, 1), 5.0f);

        GameRegistry.addSmelting(ElementtimesItems.Greenstonepowder, new ItemStack(Items.EMERALD, 1), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Redstonepowder, new ItemStack(Items.REDSTONE, 1), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Bluestonepowder, new ItemStack(Items.DYE, 1, 4), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Ironpower, new ItemStack(Items.IRON_INGOT, 1), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Goldpowder, new ItemStack(Items.GOLD_INGOT, 1), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Diamondpowder, new ItemStack(Items.DIAMOND, 1), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Copperpowder, new ItemStack(ElementtimesItems.Copper, 1), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Coalpowder, new ItemStack(Items.COAL, 1,0), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Corn, new ItemStack(ElementtimesItems.Bakedcorn, 1), 2.0f);

    }
}