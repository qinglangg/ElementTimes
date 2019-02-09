package com.elementtimes.tutorial.common.item;

import net.minecraft.item.Item;
import tab.Elementtimestab;

public class Concrete  extends Item
{
	public Concrete() 
	{
		setRegistryName("concrete");
		setUnlocalizedName("concrete");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}
