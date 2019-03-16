package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;

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
