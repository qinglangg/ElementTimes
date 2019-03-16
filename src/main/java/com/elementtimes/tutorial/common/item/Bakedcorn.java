package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class Bakedcorn extends ItemFood
{
	public Bakedcorn() 
	{
		super(5, 2.0F, false);
		setRegistryName("bakedcorn");
		setUnlocalizedName("bakedcorn");
		setCreativeTab(Elementtimestab.tabBlocks);
	}
}
