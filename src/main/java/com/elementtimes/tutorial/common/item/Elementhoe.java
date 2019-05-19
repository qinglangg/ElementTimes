package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;
import com.elementtimes.tutorial.util.ItemUtil;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.EnumHelper;

public class Elementhoe  extends ItemHoe
{
	public Elementhoe() 
	{
		super(EnumHelper.addToolMaterial("elementhoe", 4, 1000, 1000.0F, 15.0F, 100));
		setRegistryName("elementhoe");
		setUnlocalizedName("elementhoe");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
	@Override
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
		super.getSubItems(tab, items); //  Item 实现：判断 添加一个物品
		items.stream().filter(itemStack -> itemStack.getItem() == this).forEach(ItemUtil::addMaxEnchantments);
	}
}