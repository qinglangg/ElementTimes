package com.elementtimes.tutorial.util;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author KSGFK create in 2019/5/1
 */
public interface IDismantleBlock {
    ItemStack dismantleBlock(World world, BlockPos pos, IBlockState state, boolean returnDrops);
}
