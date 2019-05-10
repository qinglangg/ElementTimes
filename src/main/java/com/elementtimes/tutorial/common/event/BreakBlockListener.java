package com.elementtimes.tutorial.common.event;

import java.util.Random;

import com.elementtimes.tutorial.common.init.ElementtimesItems;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class BreakBlockListener {
	@SubscribeEvent
	public void onBreakGrass(BlockEvent.BreakEvent e) {
		IBlockState state = e.getState();
		World world = e.getWorld();
		Random rand = world.rand;
		BlockPos pos = e.getPos();
		if (state.getBlock() == Blocks.TALLGRASS) {
			//几率掉落
			if (rand.nextInt(20) == 0) {
				world.spawnEntity(new EntityItem(world, pos.getX(), pos.getY(), pos.getZ(),
						new ItemStack(ElementtimesItems.Corn)));
			}
		}
	}

}