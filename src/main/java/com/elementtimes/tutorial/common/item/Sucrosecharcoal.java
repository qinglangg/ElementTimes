package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Sucrosecharcoal  extends Item
{
	public Sucrosecharcoal() 
	{
		setRegistryName("sucrosecharcoal");
		setUnlocalizedName("sucrosecharcoal");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return 800;
	}
	
}
