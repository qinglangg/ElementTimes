package com.elementtimes.tutorial.common.item;

import com.elementtimes.elementcore.api.common.ECUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;

public class Elementbow  extends ItemBow {

	public Elementbow() 
	{
		super();
        this.setMaxDamage(38400);
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ECUtils.item::addAllEnchantments);
	}
}