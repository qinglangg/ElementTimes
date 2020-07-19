package com.elementtimes.elementtimes.common.item;

import com.elementtimes.elementtimes.common.block.RubberLog;
import com.elementtimes.elementtimes.common.init.blocks.Agriculture;
import com.elementtimes.elementtimes.common.init.Groups;
import com.elementtimes.elementtimes.common.init.Items;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;



public class WoodenHalter extends Item {

    public WoodenHalter() {
        super(new Properties().group(Groups.Agriculture).maxDamage(10));
    }

    @Override
    public ActionResultType onItemUse(ItemUseContext context) {
        World worldIn = context.getWorld();
        PlayerEntity player = context.getPlayer();
        BlockPos pos = context.getPos();
        if (!worldIn.isRemote && player != null) {
            BlockState bs = worldIn.getBlockState(pos);
            if (bs.getBlock() == Agriculture.logRubber && bs.get(RubberLog.HAS_RUBBER)) {
                BlockState state = bs.with(RubberLog.HAS_RUBBER, false);
                worldIn.setBlockState(pos, state);
                player.dropItem(new ItemStack(Items.rubberRaw), true, false);
                player.getHeldItem(context.getHand()).damageItem(1, player, p -> {});
            }
        }
        return super.onItemUse(context);
    }
}
