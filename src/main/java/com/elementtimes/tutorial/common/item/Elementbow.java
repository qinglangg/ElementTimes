package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.util.ItemUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class Elementbow  extends ItemBow {

	public Elementbow() 
	{
		super();
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtil::addMaxEnchantments);
	}
}