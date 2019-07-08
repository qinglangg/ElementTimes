package com.elementtimes.tutorial.util;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class BlockUtil {

    public static <T extends Comparable<T>> IBlockState checkAndSetState(IBlockState state, IProperty<T> property, T value) {
        if (value != state.getValue(property)) {
            return state.withProperty(property, value);
        }
        return state;
    }

    public static void setBlockState(World world, BlockPos pos, IBlockState newState, @Nullable TileEntity tileEntity) {
        IBlockState oldState = world.getBlockState(pos);
        if (oldState != newState) {
            world.setBlockState(pos, newState, 3);
            if (tileEntity != null) {
                tileEntity.validate();
                world.setTileEntity(pos, tileEntity);
            }
            world.markBlockRangeForRenderUpdate(pos, pos);
        }
    }
}
