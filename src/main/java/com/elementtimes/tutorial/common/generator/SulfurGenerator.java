package com.elementtimes.tutorial.common.generator;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
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
        ChunkPos chunkPos = worldIn.getChunkFromBlockCoords(position).getPos();
        int fixX = position.getX() == chunkPos.getXStart() ? 1 : position.getX() == chunkPos.getXEnd() ? -1 : 0;
        int fixZ = position.getZ() == chunkPos.getZStart() ? 1 : position.getZ() == chunkPos.getZEnd() ? -1 : 0;
        BlockPos pos = findSpawnPos(worldIn, position.add(fixX, 0, fixZ), 30);
        if (pos == null) {
            return false;
        }
        if (worldIn.getBlockState(pos).getMaterial() == Material.WATER) {
            int size = rand.nextInt(2) + 3;
            // 防止递归加载区块
            int disChunkX = pos.getX() - size - chunkPos.getXStart();
            int disChunkZ = pos.getZ() - size - chunkPos.getZStart();
            int offsetX = disChunkX < 0 ? -disChunkX :
                    disChunkX + (size << 1) + 1 > 16 ? -(size << 1) - 1 : 0;
            int offsetZ = disChunkZ < 0 ? -disChunkZ :
                    disChunkZ + (size << 1) + 1 > 16 ? -(size << 1) - 1 : 0;
            pos = pos.add(offsetX, 0, offsetZ);
            // 生成
            for (int x = pos.getX() - size; x <= pos.getX() + size; ++x) {
                for (int z = pos.getZ() - size; z <= pos.getZ() + size; ++z) {
                    int dx = x - pos.getX();
                    int dz = z - pos.getZ();

                    if (dx * dx + dz * dz <= size * size) {
                        for (int y = pos.getY() - 1; y <= pos.getY() + 1; ++y) {
                            BlockPos blockpos = new BlockPos(x, y, z);
                            Block block = worldIn.getBlockState(blockpos).getBlock();
                            if (block == Blocks.DIRT || block == Blocks.CLAY || block == ElementtimesBlocks.oreSalt) {
                                worldIn.setBlockState(blockpos, ElementtimesBlocks.sulfurOre.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
        return false;
    }

    private BlockPos findSpawnPos(World world, BlockPos pos, int top) {
        BlockPos posPtr = new BlockPos(pos.getX(), 0, pos.getZ());
        int lavaHeight = -1;
        while (posPtr.getY() <= top) {
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
        if (down == Blocks.LAVA || down == Blocks.FLOWING_LAVA) {
            return true;
        }
        return false;
    }
}
