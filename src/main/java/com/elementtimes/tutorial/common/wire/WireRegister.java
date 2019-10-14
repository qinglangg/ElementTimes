package com.elementtimes.tutorial.common.wire;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import java.util.ArrayList;
import java.util.List;

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
		//高能导线
		blockList.add(new WireBlock("wire_high"));
		//元素导线
		blockList.add(new WireBlock("wire_element"));
		// java.lang.ClassCastException: [Ljava.lang.Object; cannot be cast to [Lnet.minecraft.block.Block;
		event.getRegistry().registerAll(blockList.toArray(new Block[0]));
		// registerTileEntity
		GameRegistry.registerTileEntity(TileEntityWire.class, new ResourceLocation("elementtimes", "wire"));
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
