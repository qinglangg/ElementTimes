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
		//铜
		blockList.add(new WireBlock("wire_copper"));
		//铁
		blockList.add(new WireBlock("wire_iron"));
		//铝
		blockList.add(new WireBlock("wire_aluminum"));
		//金
		blockList.add(new WireBlock("wire_glod"));
		//锡
		blockList.add(new WireBlock("wire_tin"));
		//高能-银
		blockList.add(new WireBlock("wire_silver_high"));
		//高能-铜
		blockList.add(new WireBlock("wire_copper_high"));
		//高能-铝
		blockList.add(new WireBlock("wire_aluminum_high"));
		//元素
		blockList.add(new WireBlock("wire_element"));
		
		event.getRegistry().registerAll((Block[]) blockList.toArray());
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
