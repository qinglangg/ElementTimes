package com.elementtimes.tutorial.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import tab.Elementtimestab;

public class Corn extends ItemFood
{
	public Corn() 
	{
		super(4, 0.0F, false);
		setRegistryName("corn");
		setUnlocalizedName("corn");
		setCreativeTab(Elementtimestab.tabBlocks);
	}
}
