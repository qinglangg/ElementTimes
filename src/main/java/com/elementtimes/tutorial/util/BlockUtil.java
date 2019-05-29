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
        TileEntity tileentity = worldIn.getTileEntity(pos);

        if (state == iblockstate) return;

        worldIn.setBlockState(pos, state, 3);

        if (tileentity != null) {
            tileentity.validate();
            worldIn.setTileEntity(pos, tileentity);
        }
    }

    public static NonNullList<ItemStack> getAllBlocks(String oreName) {
        NonNullList<ItemStack> list = NonNullList.create();
        OreDictionary.getOres(oreName).forEach(itemStack -> {
            Block block = Block.getBlockFromItem(itemStack.getItem());
            if (block != Blocks.AIR) {
                if (itemStack.getItemDamage() == OreDictionary.WILDCARD_VALUE) {
                    block.getSubBlocks(block.getCreativeTabToDisplayOn(), list);
                } else {
                    list.add(itemStack);
                }
            }
        });
        return list;
    }
}
