package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.Elementtimestab;
import com.elementtimes.tutorial.interface_.item.IGeneratorElement;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.item.Item;

public class Fiveelements  extends Item implements IGeneratorElement
{
	public Fiveelements() 
	{
		setRegistryName("fiveelements");
		setUnlocalizedName("fiveelements");
		setCreativeTab(Elementtimestab.tabBlocks);
	}

	@Override
	public int getEnergy() {
		return ElementtimesConfig.general.generaterFive;
	}
}
