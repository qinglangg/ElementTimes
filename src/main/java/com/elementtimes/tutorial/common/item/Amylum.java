package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

public class Amylum extends ItemFood
{
	public Amylum() 
	{
		super(9, 0.0F, false);
		setRegistryName("amylum");
		setUnlocalizedName("amylum");
		setCreativeTab(Elementtimestab.tabBlocks);
	}
}
