package com.elementtimes.tutorial.common.item;

import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

public class Platinumsword  extends ItemSword
{
	public Platinumsword() 
	{
		super(EnumHelper.addToolMaterial("platinumsword", 4, 500, 10.0F, 20.0F, 20));
	}
}
