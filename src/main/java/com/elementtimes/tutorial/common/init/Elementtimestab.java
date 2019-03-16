package com.elementtimes.tutorial.common.init;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class Elementtimestab
{

	public static CreativeTabs tabBlocks = new CreativeTabs ("Elementtimes")
	{
		public ItemStack getTabIconItem()
		{
			return new ItemStack(ElementtimesItems.Fiveelements);
		}
	};
}
