package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

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
