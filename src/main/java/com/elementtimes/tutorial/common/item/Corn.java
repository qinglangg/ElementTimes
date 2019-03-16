package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

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
