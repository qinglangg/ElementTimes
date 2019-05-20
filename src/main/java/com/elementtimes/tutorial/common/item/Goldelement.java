package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import com.elementtimes.tutorial.interface_.item.IGeneratorFurl;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.item.Item;

public class Goldelement extends Item implements IGeneratorFurl
{
	public Goldelement() 
	{
		setRegistryName("goldelement");
		setUnlocalizedName("goldelement");
		setCreativeTab(Elementtimestab.tabBlocks);
	}

	@Override
	public int getEnergy() {
		return ElementtimesConfig.general.generaterGoldGen;
	}
}
