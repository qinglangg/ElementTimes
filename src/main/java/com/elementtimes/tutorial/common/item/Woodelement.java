package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.interface_.item.IGeneratorElement;
import net.minecraft.item.Item;

public class Woodelement extends Item implements IGeneratorElement {
	public int getEnergy() {
		return ElementtimesConfig.general.generaterWoodGen;
	}
}
