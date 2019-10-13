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
 * 生成盐
 * @author luqin2007
 */
public class SaltGenerator extends WorldGenerator {

    @Override
    public boolean generate(World worldIn, @Nonnull Random rand, @Nonnull BlockPos position) {
        BlockPos pos = worldIn.getTopSolidOrLiquidBlock(position);
        // 来自于 WorldGenClay
        if (worldIn.getBlockState(pos).getMaterial() == Material.WATER) {
            int size = rand.nextInt(2) + 2;
            // 防止递归加载区块
            ChunkPos chunkPos = worldIn.getChunkFromBlockCoords(pos).getPos();
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
                                worldIn.setBlockState(blockpos, ElementtimesBlocks.oreSalt.getDefaultState(), 2);
                            }
                        }
                    }
                }
            }

            return true;
        }
        return false;
    }
}
