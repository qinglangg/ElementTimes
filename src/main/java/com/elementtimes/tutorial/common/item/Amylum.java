package com.elementtimes.tutorial.common.item;

import net.minecraft.item.ItemStack;
/**
 * @author 卿岚
 */
public class Amylum extends ModFood {

	public Amylum() {
		super(5, 4.0F);
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return 400;
	}
}
