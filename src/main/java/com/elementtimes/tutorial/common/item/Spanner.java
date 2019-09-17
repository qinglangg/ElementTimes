package com.elementtimes.tutorial.common.item;

import com.elementtimes.elementcore.api.template.block.interfaces.IDismantleBlock;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * 扳手
 * @author ???
 */
public class Spanner extends Item {

    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity tile = worldIn.getTileEntity(pos);
            if (tile != null) {
                Block b = worldIn.getBlockState(pos).getBlock();
                if (b instanceof IDismantleBlock) {
                    ((IDismantleBlock) b).dismantleBlock(worldIn, pos);
                }
            }
        }
        return EnumActionResult.PASS;
    }
}
