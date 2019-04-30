package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraftforge.common.util.EnumHelper;

public class Elementhoe  extends ItemHoe
{
	public Elementhoe() 
	{
		super(EnumHelper.addToolMaterial("elementhoe", 4, 1000, 1000.0F, 15.0F, 100));
		setRegistryName("elementhoe");
		setUnlocalizedName("elementhoe");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}