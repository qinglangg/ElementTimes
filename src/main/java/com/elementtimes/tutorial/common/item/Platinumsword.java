package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.item.Item;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

public class Platinumsword  extends ItemSword
{
	public Platinumsword() 
	{
		super(EnumHelper.addToolMaterial("platinumsword", 4, 500, 10.0F, 20.0F, 20));
		setRegistryName("platinumsword");
		setUnlocalizedName("platinumsword");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}
}
