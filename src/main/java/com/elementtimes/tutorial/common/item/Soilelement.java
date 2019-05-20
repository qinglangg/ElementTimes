package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import com.elementtimes.tutorial.interface_.item.IGeneratorFurl;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.item.Item;

public class Soilelement  extends Item implements IGeneratorFurl
{
	public Soilelement() 
	{
		setRegistryName("soilelement");
		setUnlocalizedName("soilelement");
		setCreativeTab(Elementtimestab.tabBlocks);	
	}

	@Override
	public int getEnergy() {
		return ElementtimesConfig.general.generaterSoilGen;
	}
}
