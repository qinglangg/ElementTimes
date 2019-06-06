package com.elementtimes.tutorial.util;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

public class BlockUtil {

    // 代码参考 原版熔炉
    public static void setState(IBlockState state, World worldIn, BlockPos pos) {
        IBlockState iblockstate = worldIn.getBlockState(pos);
        if (state != iblockstate) {
            TileEntity tileentity = worldIn.getTileEntity(pos);
            worldIn.setBlockState(pos, state, 3);
            if (tileentity != null) {
                tileentity.validate();
                worldIn.setTileEntity(pos, tileentity);
            }
        }
    }

    public static NonNullList<ItemStack> getAllBlocks(String oreName) {
        NonNullList<ItemStack> list = ItemUtil.getAllItems(oreName);
        for (ItemStack stack : list) {
            if (stack.isEmpty() || Block.getBlockFromItem(stack.getItem()) == Blocks.AIR) {
                list.remove(stack);
            }
        }
        return list;
    }
}
