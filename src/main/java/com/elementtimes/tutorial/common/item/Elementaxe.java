package com.elementtimes.tutorial.common.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemAxe;
import net.minecraftforge.common.util.EnumHelper;
import tab.Elementtimestab;

public class Elementaxe  extends ItemAxe
{
	public Elementaxe() 
	{
		super(EnumHelper.addToolMaterial("elementsword", 4, 3000, 1000.0F, 15.0F, 25),100.0F,0.0F);
		setRegistryName("elementaxe");
		setUnlocalizedName("elementaxe");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}