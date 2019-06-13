package com.elementtimes.tutorial.common.item;

import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;

public class Amylum extends ItemFood {

	public Amylum() {
		super(5, 5.0F, false);
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return 400;
	}
}
