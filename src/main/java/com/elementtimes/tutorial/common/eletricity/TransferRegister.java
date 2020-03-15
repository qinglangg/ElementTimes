package com.elementtimes.tutorial.common.eletricity;

import java.util.ArrayList;
import java.util.List;

import com.elementtimes.tutorial.common.eletricity.src.block.TransferBlock;
import com.elementtimes.tutorial.common.eletricity.src.tileentity.EleSrcCable;
import com.elementtimes.tutorial.common.eletricity.src.trusteeship.EleSrcInputer;
import com.elementtimes.tutorial.common.eletricity.src.trusteeship.EleSrcOutputer;
import com.elementtimes.tutorial.common.eletricity.src.trusteeship.EleSrcTransfer;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author EmptyDreams
 * @version V1.0
 */
@Mod.EventBusSubscriber
public final class TransferRegister {
	
	static {
		EleWorker.registerTransfer(new EleSrcTransfer());
		EleWorker.registerOutputer(new EleSrcOutputer());
		EleWorker.registerInputer(new EleSrcInputer());
	}
	
	private static List<TransferBlock> blockList = new ArrayList<>(7);
	
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
		
		for (Block b : blockList) {
			event.getRegistry().register(b);
		}
	}
	
	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		if (FMLCommonHandler.instance().getSide().isClient()) {
			blockList.forEach(it -> {
				event.getRegistry().register(it.ITEM);
				ModelResourceLocation model = new ModelResourceLocation(it.ITEM.getRegistryName(), "inventory");
				ModelLoader.setCustomModelResourceLocation(it.ITEM, 0, model);
			});
		} else {
			blockList.forEach(it -> {
				event.getRegistry().register(it.ITEM);
			});
		}
	}
	
	private static final class WireBlock extends TransferBlock {
		
		WireBlock(String name) {
			super(name);
		}
		
		@Override
		public TileEntity createNewTileEntity(World world, int i) {
			return new EleSrcCable(Integer.MAX_VALUE, 0);
		}
	}
	
}
