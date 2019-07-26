package com.elementtimes.tutorial.common.event;

import java.util.Iterator;
import java.util.Map;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

public class RecipeRemove {
	private static void removeSmelting(Item item) {
		Map smeltList = FurnaceRecipes.instance().getSmeltingList();
		for (Iterator<ItemStack> i = smeltList.keySet().iterator(); i.hasNext();) {
			ItemStack input = i.next();
			if (input.getItem() == Item.getItemFromBlock(Blocks.IRON_ORE)) {
				i.remove();
			}
		}
	}
}
