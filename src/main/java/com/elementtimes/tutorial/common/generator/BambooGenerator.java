package com.elementtimes.tutorial.common.generator;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.init.Biomes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

public class BambooGenerator extends WorldGenerator {

    private BlockPos spawnPos = BlockPos.ORIGIN;

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        if (!worldIn.isRemote && rand.nextFloat() < .2f) {
            int x = rand.nextInt(15);
            int z = rand.nextInt(15);
            if (checkHeight(worldIn, x + position.getX(), z + position.getZ())) {
                if (checkBiome(worldIn, spawnPos)) {
                    if (checkSpawn(worldIn, spawnPos)) {
                        setBlockAndNotifyAdequately(worldIn, spawnPos, ElementtimesBlocks.bamboo.getDefaultState());
                        System.out.println("spawn at " + spawnPos);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean checkHeight(World worldIn, int x, int z) {
        BlockPos pos = new BlockPos(x, worldIn.getHeight(x, z), z);
        return !worldIn.isOutsideBuildHeight(pos);
    }

    private boolean checkBiome(World worldIn, BlockPos pos) {
        ResourceLocation biome = worldIn.getBiome(pos).getRegistryName();
        return Biomes.JUNGLE.getRegistryName().equals(biome)
                || Biomes.JUNGLE_EDGE.getRegistryName().equals(biome)
                || Biomes.JUNGLE_HILLS.getRegistryName().equals(biome)
                || Biomes.MUTATED_JUNGLE.getRegistryName().equals(biome)
                || Biomes.MUTATED_JUNGLE_EDGE.getRegistryName().equals(biome);
    }

    private boolean checkSpawn(World worldIn, BlockPos pos) {
        return ElementtimesBlocks.bamboo.canPlaceBlockAt(worldIn, pos);
    }
}
