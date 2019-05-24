package com.elementtimes.tutorial.common.item;

import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;

public class Hydrogen extends ItemGlassBottle
{
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return 1600;
	}
}
