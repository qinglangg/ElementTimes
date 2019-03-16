package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;

public class Concrete  extends Item
{
	public Concrete() 
	{
		setRegistryName("concrete");
		setUnlocalizedName("concrete");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}
