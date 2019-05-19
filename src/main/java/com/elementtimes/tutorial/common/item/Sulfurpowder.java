package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimesoretab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class Sulfurpowder  extends Item
{
	public Sulfurpowder() 
	{
		setRegistryName("sulfurpowder");
		setUnlocalizedName("sulfurpowder");
		setCreativeTab(Elementtimesoretab.tabBlocks);	
	}

	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return 800;	
	}
}
