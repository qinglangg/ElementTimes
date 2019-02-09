package com.elementtimes.tutorial.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import tab.Elementtimestab;

public class Starchpowder extends ItemFood
{
	public Starchpowder() 
	{
		super(1, 0.0F, false);
		setRegistryName("starchpowder");
		setUnlocalizedName("starchpowder");
		setCreativeTab(Elementtimestab.tabBlocks);
	}
}
