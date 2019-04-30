package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;

public class Money  extends Item
{
	public Money() 
	{
		setRegistryName("money");
		setUnlocalizedName("money");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}
