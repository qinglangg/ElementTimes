package com.elementtimes.tutorial.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.common.util.EnumHelper;
import tab.Elementtimestab;

public class Elementpickaxe  extends ItemPickaxe
{
	public Elementpickaxe() 
	{
		super(EnumHelper.addToolMaterial("elementpickaxe", 4, 1000, 1000.0F, 15.0F, 100));
		setRegistryName("elementpickaxe");
		setUnlocalizedName("elementpickaxe");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}