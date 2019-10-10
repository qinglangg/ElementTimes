package com.elementtimes.tutorial.common.event;

import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.Random;

/**
 * 打草几率掉玉米事件
 * @author 金竹
 */
@Mod.EventBusSubscriber
public class CommonEvent {

	@SubscribeEvent
	public static void onBreakGrass(net.minecraftforge.event.world.BlockEvent.BreakEvent e) {
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
	public static void onItemUse(PlayerInteractEvent.RightClickBlock event) {
		if (!event.getWorld().isRemote) {
			EntityPlayer player = event.getEntityPlayer();
			ItemStack item = player.getHeldItem(event.getHand());
			IBlockState state = event.getWorld().getBlockState(event.getPos());
			Block block = state.getBlock();
			// 耕地
			boolean interrupt = (item.getItem() instanceof ItemHoe && (block == Blocks.GRASS || block == Blocks.GRASS_PATH || (block == Blocks.DIRT && state.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT)))
					// 草径
					|| (item.getItem() instanceof ItemSpade && block == Blocks.GRASS);
			if (interrupt) {
				event.setCanceled(true);
			}
		}
	}
}
