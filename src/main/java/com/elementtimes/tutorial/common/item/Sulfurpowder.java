package com.elementtimes.tutorial.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Sulfurpowder  extends Item
{
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return 800;	
	}
}
