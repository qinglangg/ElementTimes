package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSoup;

public class Cornbroth extends ItemSoup
{
	public Cornbroth() 
	{
		super(20);
		setRegistryName("cornbroth");
		setUnlocalizedName("cornbroth");
		setCreativeTab(Elementtimestab.tabBlocks);
	}
}
