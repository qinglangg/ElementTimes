package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.util.ItemUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nonnull;

public class Elementhoe  extends ItemHoe {

	public Elementhoe() 
	{
		super(EnumHelper.addToolMaterial("elementhoe", 4, 1000, 1000.0F, 15.0F, 100));
	}

	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtil::addMaxEnchantments);
	}
}