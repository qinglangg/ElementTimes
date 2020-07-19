package com.elementtimes.elementtimes.common.item;

import com.elementtimes.elementcore.api.block.IDismantleBlock;
import com.elementtimes.elementtimes.common.init.Groups;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 扳手

 */
public class Spanner extends Item {

    public Spanner() {
        super(new Properties().group(Groups.Industry).maxStackSize(1));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        if (!context.getWorld().isRemote) {
            BlockPos pos = context.getPos();
            World world = context.getWorld();
            BlockState blockState = world.getBlockState(pos);
            Block block = blockState.getBlock();
            if (block instanceof IDismantleBlock) {
                ((IDismantleBlock) block).dismantleBlock(world, pos);
                return ActionResultType.SUCCESS;
            }
        }
        return super.onItemUse(context);
    }
}
