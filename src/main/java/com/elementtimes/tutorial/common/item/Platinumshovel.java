package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSpade;
import net.minecraftforge.common.util.EnumHelper;

public class Platinumshovel  extends ItemSpade
{
	public Platinumshovel() 
	{
		super(EnumHelper.addToolMaterial("platinumshovel", 4, 500, 300.0F, 15.0F, 100));
		setRegistryName("platinumshovel");
		setUnlocalizedName("platinumshovel");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}