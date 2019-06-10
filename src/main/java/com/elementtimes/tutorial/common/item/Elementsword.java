package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.util.ItemUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nonnull;

public class Elementsword  extends ItemSword
{
	public Elementsword() 
	{
		super(EnumHelper.addToolMaterial("elementsword", 4, 1000, 10.0F, 90.0F, 20));
	}
	@Override
	public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
		super.getSubItems(tab, items);
		items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtil::addMaxEnchantments);
	}
}
