package com.elementtimes.elementtimes.common.eletricity;

import com.elementtimes.elementcore.api.utils.CommonUtils;
import com.elementtimes.elementtimes.common.eletricity.src.block.TransferBlock;
import com.elementtimes.elementtimes.common.eletricity.src.trusteeship.EleSrcInputer;
import com.elementtimes.elementtimes.common.eletricity.src.trusteeship.EleSrcOutputer;
import com.elementtimes.elementtimes.common.eletricity.src.trusteeship.EleSrcTransfer;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO clean old codes: 重新注册吧。。。
 * @author EmptyDreams
 * @version V1.0
 */
@Mod.EventBusSubscriber
public final class TransferRegister {
	
	static {
//		EleWorker.registerInputer(new EleSrcInputer());
//		EleWorker.registerOutputer(new EleSrcOutputer());
//		EleWorker.registerTransfer(new EleSrcTransfer());
	}
	
	private static List<TransferBlock> blockList = new ArrayList<>(7);
//
//	@SubscribeEvent
//	public static void registerBlock(RegistryEvent.Register<Block> event) {
//		//银
//		blockList.add(new WireBlock("wire_silver"));
//		//铜
//		blockList.add(new WireBlock("wire_copper"));
//		//铁
//		blockList.add(new WireBlock("wire_iron"));
//		//铝
//		blockList.add(new WireBlock("wire_aluminum"));
//		//金
//		blockList.add(new WireBlock("wire_glod"));
//		//锡
//		blockList.add(new WireBlock("wire_tin"));
//		//高能导线
//		blockList.add(new WireBlock("wire_high"));
//		//元素导线
//		blockList.add(new WireBlock("wire_element"));
//
//		for (Block b : blockList) {
//			event.getRegistry().register(b);
//		}
//	}
//
	@SubscribeEvent
	public static void registerItem(RegistryEvent.Register<Item> event) {
		if (CommonUtils.isClient()) {
			blockList.forEach(it -> {
				event.getRegistry().register(it.asItem());
//				ModelResourceLocation model = new ModelResourceLocation(it.asItem().getRegistryName(), "inventory");
//				ModelLoader.setCustomModelResourceLocation(it.asItem(), 0, model);
			});
		} else {
			blockList.forEach(it -> {
				event.getRegistry().register(it.asItem());
			});
		}
	}
//
//	private static final class WireBlock extends TransferBlock {
//
//		WireBlock(String name) {
//			super(name);
//		}
//
//		@Override
//		public TileEntity createNewTileEntity(World world, int i) {
//			return new EleSrcCable(Integer.MAX_VALUE, 0);
//		}
//	}
	
}
