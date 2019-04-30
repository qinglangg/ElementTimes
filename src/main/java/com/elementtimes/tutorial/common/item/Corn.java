package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.block.CornCrop;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class Corn extends ItemFood
{
	public Corn() 
	{
		super(4, 0.0F, false);
		setRegistryName("corn");
		setUnlocalizedName("corn");
		setCreativeTab(Elementtimestab.tabBlocks);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand,
			EnumFacing facing, float hitX, float hitY, float hitZ) {
		if(!worldIn.isRemote)
		worldIn.setBlockState(pos.up(),ElementtimesBlocks.Corncrop.getDefaultState());
		return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);

	}

}
