package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraftforge.common.util.EnumHelper;

public class Elementshovel  extends ItemSpade
{
	public Elementshovel() 
	{
		super(EnumHelper.addToolMaterial("elementshovel", 4, 1000, 1000.0F, 15.0F, 100));
		setRegistryName("elementshovel");
		setUnlocalizedName("elementshovel");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}