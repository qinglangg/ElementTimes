package com.elementtimes.elementtimes.common.event;

import com.elementtimes.elementcore.api.utils.MathUtils;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.init.Items;
import net.minecraft.block.Blocks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

/**
 * 植物相关事件
 */
@Mod.EventBusSubscriber(modid = ElementTimes.MODID)
public class PlantEvent {

	@SubscribeEvent
	public static void onBreakGrass1(net.minecraftforge.event.world.BlockEvent.BreakEvent e) {
		if (e.getState().getBlock() == Blocks.TALL_GRASS) {
			//几率掉落（二十分之一）
			int chance = 20;
			if (MathUtils.RAND.nextInt(chance) == 0) {
				e.getPlayer().entityDropItem(Items.corn);
			}
		}
	}
}
