package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimeschemicalindustrytab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGlassBottle;

public class Sulphuricacid extends ItemGlassBottle
{
	public Sulphuricacid() 
	{
		setRegistryName("sulphuricacid");
		setUnlocalizedName("sulphuricacid");
		setCreativeTab(Elementtimeschemicalindustrytab.tabBlocks);
	}
}