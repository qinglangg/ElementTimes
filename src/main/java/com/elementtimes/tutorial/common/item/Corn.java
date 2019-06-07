package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.BlockFarmland;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemFood;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Corn extends ItemFood {

	public Corn() 
	{
		super(5, 0.0F, false);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote) {
			IBlockState state = worldIn.getBlockState(pos);
			if(state.getBlock()==Blocks.FARMLAND&&state.getValue(BlockFarmland.MOISTURE)>0) {
				worldIn.setBlockState(pos.up(),ElementtimesBlocks.cornCrop.getDefaultState());
				player.getHeldItem(hand).shrink(1);
			}
		}
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
	}

}
