package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.interface_.item.IGeneratorElement;
import net.minecraft.item.Item;

public class Soilelement  extends Item implements IGeneratorElement
{
	@Override
	public int getEnergy() {
		return ElementtimesConfig.general.generaterSoilGen;
	}
}
