package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimeschemicalindustrytab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGlassBottle;

public class Sulfitesolution extends ItemGlassBottle
{
	public Sulfitesolution() 
	{
		setRegistryName("sulfitesolution");
		setUnlocalizedName("sulfitesolution");
		setCreativeTab(Elementtimeschemicalindustrytab.tabBlocks);
	}
}
