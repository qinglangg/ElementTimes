package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.util.ItemUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;

public class Elementsword  extends ItemSword
{
	public Elementsword() 
	{
		super(EnumHelper.addToolMaterial("elementsword", 4, 1000, 10.0F, 90.0F, 20));
	}
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		super.getSubItems(tab, items); //  Item 实现：判断 添加一个物品
		items.forEach(ItemUtil::addMaxEnchantments);
	}
}
