package com.elementtimes.tutorial.common.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Elementtimeschemicalindustrytab
{

	public static CreativeTabs tabBlocks = new CreativeTabs ("Elementtimeschemicalindustry")
	{
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ElementtimesItems.Sodiumsulfitesolution);
		}
	};
}
