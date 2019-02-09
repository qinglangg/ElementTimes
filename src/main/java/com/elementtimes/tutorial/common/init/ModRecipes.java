package com.elementtimes.tutorial.common.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes
{

	public static void init()
	{

		GameRegistry.addSmelting(Blocks.STONE, new ItemStack(ElementtimesItems.Stoneingot, 1),  2.0f);
		GameRegistry.addSmelting(ElementtimesBlocks.Stoneblock, new ItemStack(ElementtimesItems.Calciumcarbonate, 1),  3.0f); 
		GameRegistry.addSmelting(ElementtimesItems.Calciumcarbonate, new ItemStack(ElementtimesItems.Calciumoxide, 4),  3.0f); 
	}
}