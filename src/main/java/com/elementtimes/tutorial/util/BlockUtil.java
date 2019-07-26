package com.elementtimes.tutorial.util;

import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
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

    public static EnumFacing getPosFacing(BlockPos before, BlockPos pos) {
        // face
        EnumFacing facing = null;
        int dx = pos.getX() - before.getX();
        if (dx > 0) {
            facing = EnumFacing.EAST;
        } else if (dx < 0) {
            facing = EnumFacing.WEST;
        }
        int dy = pos.getY() - before.getY();
        if (dy > 0 && facing == null) {
            facing = EnumFacing.UP;
        } else if (dy < 0 && facing == null) {
            facing = EnumFacing.DOWN;
        } else if (dy != 0) {
            return null;
        }
        int dz = pos.getZ() - before.getZ();
        if (dz > 0 && facing != null) {
            facing = EnumFacing.SOUTH;
        } else if (dz < 0 && facing != null) {
            facing = EnumFacing.NORTH;
        } else if (dz != 0) {
            return null;
        }
        return facing;
    }
}
