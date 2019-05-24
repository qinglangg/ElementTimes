package com.elementtimes.tutorial.common.item;

import net.minecraft.item.ItemPickaxe;
import net.minecraftforge.common.util.EnumHelper;

public class Platinumpick  extends ItemPickaxe
{
	public Platinumpick() 
	{
		super(EnumHelper.addToolMaterial("platinumpick", 4, 500, 300.0F, 15.0F, 50));
	}
}