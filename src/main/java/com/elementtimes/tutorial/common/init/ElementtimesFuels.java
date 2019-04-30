package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.common.init.ElementtimesItems;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.IFuelHandler;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ElementtimesFuels {
	public static void init() {
		GameRegistry.registerFuelHandler(new IFuelHandler(){
			@Override
			public int getBurnTime(ItemStack fuel) {
				return ElementtimesItems.Sulfurpowder != fuel.getItem() ? 0 : 800;
			}
        });
		GameRegistry.registerFuelHandler(new IFuelHandler(){
			@Override
			public int getBurnTime(ItemStack fuel) {
				return ElementtimesItems.Sucrosecharcoal != fuel.getItem() ? 0 : 800;
			}
        });
		GameRegistry.registerFuelHandler(new IFuelHandler(){
			@Override
			public int getBurnTime(ItemStack fuel) {
				return ElementtimesItems.Amylum != fuel.getItem() ? 0 : 600;
			}
        });
		GameRegistry.registerFuelHandler(new IFuelHandler(){
			@Override
			public int getBurnTime(ItemStack fuel) {
				return ElementtimesItems.Hydrogen != fuel.getItem() ? 0 : 1600;
			}
        });
	}
}
