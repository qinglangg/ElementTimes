package com.elementtimes.tutorial.common.event;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

import java.util.Map;

public class RecipeRemove{
	public RecipeRemove(){
		removeSmelting(Item.getItemFromBlock(Blocks.IRON_ORE));
	}

	private void removeSmelting(Item item){
		Map<ItemStack, ItemStack> smeltList = FurnaceRecipes.instance().getSmeltingList();
		smeltList.keySet().removeIf(input -> input.getItem() == item);
	}
}