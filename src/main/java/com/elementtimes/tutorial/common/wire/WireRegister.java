package com.elementtimes.tutorial.common.wire;

import java.util.List;
import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public final class WireRegister {

	private static List<Block> blockList = new ArrayList<>();
	private static List<Item> itemList = new ArrayList<>();
	
	@SubscribeEvent
	public static void registerBlock(RegistryEvent.Register<Block> event) {
		Block wire = new WireBlock("wire_copper");
		event.getRegistry().register(wire);
		blockList.add(wire);
	}
	
	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		Item item;
		for (Block b : blockList) {
			item = new Wire(b, b.getRegistryName().getResourcePath());
			itemList.add(item);
			event.getRegistry().register(item);
		}
		if (FMLCommonHandler.instance().getSide().isClient()) {
			for (int i = 0; i < blockList.size(); ++i) {
				ModelResourceLocation model = new ModelResourceLocation(blockList.get(i).getRegistryName(), "inventory");
				ModelLoader.setCustomModelResourceLocation(itemList.get(i), 0, model);
			}
		}
	}
	
}
