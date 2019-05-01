package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.common.util.EnumHelper;

public class Platinumpick  extends ItemPickaxe
{
	public Platinumpick() 
	{
		super(EnumHelper.addToolMaterial("platinumpick", 4, 500, 300.0F, 15.0F, 50));
		setRegistryName("platinumpick");
		setUnlocalizedName("platinumpick");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}