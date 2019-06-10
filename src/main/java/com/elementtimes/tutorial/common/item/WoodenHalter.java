package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.block.tree.RubberLog;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 木笼头
 * @author luqin
 */
public class WoodenHalter extends Item {

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing,
                                      float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            IBlockState bs = worldIn.getBlockState(pos);
            if (bs.getBlock() == ElementtimesBlocks.rubberLog && bs.getValue(RubberLog.HAS_RUBBER)) {
                player.getHeldItem(hand).damageItem(1, player);
                worldIn.setBlockState(pos, bs.withProperty(RubberLog.HAS_RUBBER, false));
                worldIn.markBlockRangeForRenderUpdate(pos, pos);
                player.dropItem(new ItemStack(ElementtimesItems.rubberRaw), true, false);
            }
        }
        return EnumActionResult.PASS;
    }
}
