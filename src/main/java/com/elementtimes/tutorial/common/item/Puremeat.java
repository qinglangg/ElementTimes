package com.elementtimes.tutorial.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import tab.Elementtimestab;

public class Puremeat extends ItemFood
{
	public Puremeat() 
	{
		super(4, 0.0F, false);
		setRegistryName("puremeat");
		setUnlocalizedName("puremeat");
		setCreativeTab(Elementtimestab.tabBlocks);
	}
}
