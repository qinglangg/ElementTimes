package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;
import com.elementtimes.tutorial.util.ItemUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;

public class Elementshovel  extends ItemSpade
{
	public Elementshovel() 
	{
		super(EnumHelper.addToolMaterial("elementshovel", 4, 1000, 1000.0F, 15.0F, 100));
		setRegistryName("elementshovel");
		setUnlocalizedName("elementshovel");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		super.getSubItems(tab, items); //  Item 实现：判断 添加一个物品
		items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtil::addMaxEnchantments);
	}
}