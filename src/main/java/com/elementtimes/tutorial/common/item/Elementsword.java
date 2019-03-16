package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

public class Elementsword  extends ItemSword
{
	public Elementsword() 
	{
		super(EnumHelper.addToolMaterial("elementsword", 4, 1000, 10.0F, 90.0F, 20));
		setRegistryName("elementsword");
		setUnlocalizedName("elementsword");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}
