package com.elementtimes.tutorial.common.event;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

/**
 * 植物相关事件
 */
@Mod.EventBusSubscriber(modid = ElementTimes.MODID)
public class PlantEvent {

	@SubscribeEvent
	public static void onBreakGrass1(net.minecraftforge.event.world.BlockEvent.BreakEvent e) {
		IBlockState state = e.getState();
		World world = e.getWorld();
		Random rand = world.rand;
		BlockPos pos = e.getPos();
		if (state.getBlock() == Blocks.TALLGRASS) {
			//几率掉落（二十分之一）
			int chance = 20;
			if (rand.nextInt(chance) == 0) {
				world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(),
						new ItemStack(ElementtimesItems.corn)));
			}
		}
	}
	@SubscribeEvent
	public static void onBreakGrass2(net.minecraftforge.event.world.BlockEvent.BreakEvent e) {
		IBlockState state = e.getState();
		World world = e.getWorld();
		Random rand = world.rand;
		BlockPos pos = e.getPos();
		if (state.getBlock() == Blocks.TALLGRASS) {
			//几率掉落（三十分之一）
			int chance = 30;
			if (rand.nextInt(chance) == 0) {
				world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(),
						new ItemStack(ElementtimesItems.bamboo)));
			}
		}
	}
}
