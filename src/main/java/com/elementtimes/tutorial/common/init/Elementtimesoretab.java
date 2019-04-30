package com.elementtimes.tutorial.common.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Elementtimesoretab
{

	public static CreativeTabs tabBlocks = new CreativeTabs ("Elementtimesore")
	{
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ElementtimesItems.Bighammer);
		}
	};
}
