package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraftforge.common.util.EnumHelper;

public class Platinumhoe  extends ItemHoe
{
	public Platinumhoe() 
	{
		super(EnumHelper.addToolMaterial("platinumhoe", 4, 500, 300.0F, 15.0F, 100));
		setRegistryName("platinumhoe");
		setUnlocalizedName("platinumhoe");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}