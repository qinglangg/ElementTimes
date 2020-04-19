package com.elementtimes.tutorial.common.generator;

import com.elementtimes.tutorial.common.block.Bamboo;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BambooGenerator extends WorldGenerator {

    private BlockPos spawnPos = BlockPos.ORIGIN;

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (!worldIn.isRemote && rand.nextFloat() < .2f && worldIn.isBlockLoaded(position)) {
            int x = rand.nextInt(15);
            int z = rand.nextInt(15);
            if (checkHeight(worldIn, x + position.getX(), z + position.getZ())) {
                if (checkBiome(worldIn, spawnPos)) {
                    if (checkSpawn(worldIn, spawnPos)) {
                        IBlockState bamboo = ElementtimesBlocks.bamboo.getDefaultState();
                        int height = rand.nextInt(((Bamboo) ElementtimesBlocks.bamboo).getMaxHeight() - 1);
                        setBlockAndNotifyAdequately(worldIn, spawnPos, bamboo);
                        for (int i = 0; i < height; i++) {
                            spawnPos = spawnPos.up();
                            if (worldIn.getBlockState(position).getBlock().isReplaceable(worldIn, position)) {
                                setBlockAndNotifyAdequately(worldIn, spawnPos, bamboo);
                            } else {
                                break;
                            }
                        }
                        spawnPos = BlockPos.ORIGIN;
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkHeight(World worldIn, int x, int z) {
        spawnPos = new BlockPos(x, worldIn.getHeight(x, z), z);
        return !worldIn.isOutsideBuildHeight(spawnPos);
    }

    private boolean checkBiome(World worldIn, BlockPos pos) {
        Biome biome = worldIn.getBiome(pos);
        return biome == Biomes.FOREST;
    }

    private boolean checkSpawn(World worldIn, BlockPos pos) {
        return ElementtimesBlocks.bamboo.canPlaceBlockAt(worldIn, pos);
    }
}
