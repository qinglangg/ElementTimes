package com.elementtimes.elementtimes.common.item;

import com.elementtimes.elementtimes.common.init.blocks.Agriculture;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * @author 卿岚
 */
public class Corn extends ModFood {

	public Corn() {
		super(5, 0.0F);
	}

	@Override
	@Nonnull
	public ActionResultType onItemUse(ItemUseContext context) {
		World world = context.getWorld();
		if(!world.isRemote) {
			BlockPos pos = context.getPos();
			BlockState state = world.getBlockState(pos);
			if(state.getBlock() == Blocks.FARMLAND && state.get(FarmlandBlock.MOISTURE) > 0) {
				world.setBlockState(pos.up(), Agriculture.cornCrop.getDefaultState());
				PlayerEntity player = context.getPlayer();
				if (player != null) {
					player.getHeldItem(context.getHand()).shrink(1);
				}
			}
		}
		return super.onItemUse(context);
	}
}
