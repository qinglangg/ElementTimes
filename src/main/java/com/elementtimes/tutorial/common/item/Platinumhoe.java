package com.elementtimes.tutorial.common.item;

import net.minecraft.item.ItemHoe;
import net.minecraftforge.common.util.EnumHelper;

public class Platinumhoe  extends ItemHoe
{
	public Platinumhoe() 
	{
		super(EnumHelper.addToolMaterial("platinumhoe", 4, 500, 300.0F, 15.0F, 100));
	}
}