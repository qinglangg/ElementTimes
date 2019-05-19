package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimeschemicalindustrytab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;

public class Hydrogen extends ItemGlassBottle
{
	public Hydrogen() 
	{
		setRegistryName("hydrogen");
		setUnlocalizedName("hydrogen");
		setCreativeTab(Elementtimeschemicalindustrytab.tabBlocks);
	}
	@Override
	public int getItemBurnTime(ItemStack itemStack) {
		return 1600;
	}
}
