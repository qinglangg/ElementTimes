package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraftforge.common.util.EnumHelper;

public class Platinumaxe  extends ItemAxe
{
	public Platinumaxe() 
	{
		super(EnumHelper.addToolMaterial("platinumaxe", 4, 800, 300.0F, 15.0F, 25),30.0F,0.0F);
		setRegistryName("platinumaxe");
		setUnlocalizedName("platinumaxe");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}