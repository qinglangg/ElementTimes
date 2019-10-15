package com.elementtimes.tutorial.common.generator;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * 硫矿石生成
 * @author luqin2007
 */
public class SulfurGenerator extends WorldGenerator {

    @Override
    public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos position) {
        IBlockState spawnBlock = ElementtimesBlocks.sulfurOre.getDefaultState();
        int r = rand.nextInt(1) + 1;
        int size = r + 3;
        BlockPos pos = findSpawnPos(worldIn, position.add(rand.nextInt(16 - 2 * r), 0, rand.nextInt(16 - 2 * r)));
        if (pos != null) {
            // 生成
            for (int x = pos.getX(); x <= pos.getX() + size * r; ++x) {
                for (int z = pos.getZ(); z <= pos.getZ() + size * r; ++z) {
                    for (int y = pos.getY(); y <= pos.getY() + r; ++y) {
                        BlockPos blockpos = new BlockPos(x, y, z);
                        Block block = worldIn.getBlockState(blockpos).getBlock();
                        if (block == Blocks.STONE || block == Blocks.COBBLESTONE) {
                            worldIn.setBlockState(blockpos, spawnBlock, 2);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private BlockPos findSpawnPos(World world, BlockPos pos) {
        BlockPos posPtr = new BlockPos(pos.getX(), 0, pos.getZ());
        int lavaHeight = -1;
        while (posPtr.getY() <= 30) {
            Block block = world.getBlockState(posPtr).getBlock();
            if (block == Blocks.AIR) {
                if (isLavaBlock(world, posPtr.down())) {
                    lavaHeight = posPtr.getY();
                }
            } else if (block == Blocks.STONE) {
                if (lavaHeight > 0) {
                    return posPtr;
                } else {
                    if (isLavaBlock(world, posPtr.east())) {
                        posPtr = posPtr.east();
                        lavaHeight = posPtr.getY();
                    } else if (isLavaBlock(world, posPtr.west())) {
                        posPtr = posPtr.west();
                        lavaHeight = posPtr.getY();
                    } else if (isLavaBlock(world, posPtr.north())) {
                        posPtr = posPtr.north();
                        lavaHeight = posPtr.getY();
                    } else if (isLavaBlock(world, posPtr.south())) {
                        posPtr = posPtr.south();
                        lavaHeight = posPtr.getY();
                    }
                }
            }
            posPtr = posPtr.up();
        }
        return null;
    }

    private boolean isLavaBlock(World world, BlockPos pos) {
        Block down = world.getBlockState(pos).getBlock();
        return down == Blocks.LAVA || down == Blocks.FLOWING_LAVA;
    }
}
