package com.elementtimes.tutorial.interface_.block;

import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * @author KSGFK create in 2019/5/1
 */
public interface IDismantleBlock {
    default ItemStack dismantleBlock(World world, BlockPos pos, IBlockState state, boolean returnDrops) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null) {
                NBTTagCompound tag = tile.serializeNBT();
                tag.removeTag("x");
                tag.removeTag("y");
                tag.removeTag("z");
                ItemStack stack = new ItemStack(state.getBlock());
                stack.setCount(1);
                stack.setTagCompound(tag);
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }
}
