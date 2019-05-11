package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.Elementtimes;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ElementtimesRecipes {
    public static void init() {
        GameRegistry.addSmelting(Blocks.STONE, new ItemStack(ElementtimesItems.Stoneingot, 1), 2.0f);
        GameRegistry.addSmelting(ElementtimesBlocks.Stoneblock, new ItemStack(ElementtimesItems.Calciumcarbonate, 1), 3.0f);
        GameRegistry.addSmelting(ElementtimesItems.Calciumcarbonate, new ItemStack(ElementtimesItems.Calciumoxide, 2), 4.0f);
        GameRegistry.addSmelting(ElementtimesBlocks.Copperore, new ItemStack(ElementtimesItems.Copper, 1), 5.0f);
        GameRegistry.addSmelting(ElementtimesBlocks.Sulfurore, new ItemStack(ElementtimesItems.Sulfurpowder, 3), 5.0f);
        GameRegistry.addSmelting(ElementtimesBlocks.Calciumfluoride, new ItemStack(Blocks.GLOWSTONE, 1), 5.0f);
        GameRegistry.addSmelting(ElementtimesItems.Sulfurorepowder, new ItemStack(ElementtimesItems.Sulfurpowder, 2), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Greenstonepowder, new ItemStack(Items.EMERALD, 1), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Redstonepowder, new ItemStack(Items.REDSTONE, 2), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Bluestonepowder, new ItemStack(Items.DYE, 2, 4), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Ironpower, new ItemStack(Items.IRON_INGOT, 1), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Goldpowder, new ItemStack(Items.GOLD_INGOT, 1), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Diamondpowder, new ItemStack(Items.DIAMOND, 1), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Copperpowder, new ItemStack(ElementtimesItems.Copper, 1), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Coalpowder, new ItemStack(Items.COAL, 1,0), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Quartzpowder, new ItemStack(Items.QUARTZ, 2), 4.0f);
        GameRegistry.addSmelting(ElementtimesItems.Corn, new ItemStack(ElementtimesItems.Bakedcorn, 1), 2.0f);
        GameRegistry.addSmelting(ElementtimesBlocks.Platinumore, new ItemStack(ElementtimesItems.Platinumingot, 1), 5.0f);
        GameRegistry.addSmelting(ElementtimesItems.Platinumorepowder, new ItemStack(ElementtimesItems.Platinumingot, 1), 4.0f);
        GameRegistry.addSmelting(Items.POTIONITEM, new ItemStack(ElementtimesItems.Steam, 1), 3.0f);
        GameRegistry.addSmelting(ElementtimesItems.Sulfitesolution, new ItemStack(ElementtimesItems.Sulphuricacid, 2), 3.0f);
    }        
}