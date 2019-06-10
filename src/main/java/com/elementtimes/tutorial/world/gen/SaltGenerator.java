package com.elementtimes.tutorial.world.gen;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
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
        BlockPos sPosition = worldIn.getTopSolidOrLiquidBlock(position);
        // 来自于 WorldGenClay
        if (worldIn.getBlockState(sPosition).getMaterial() == Material.WATER) {
            int i = rand.nextInt(2) + 2;
            int j = 1;

            for (int k = sPosition.getX() - i; k <= sPosition.getX() + i; ++k) {
                for (int l = sPosition.getZ() - i; l <= sPosition.getZ() + i; ++l) {
                    int i1 = k - sPosition.getX();
                    int j1 = l - sPosition.getZ();

                    if (i1 * i1 + j1 * j1 <= i * i) {
                        for (int k1 = sPosition.getY() - 1; k1 <= sPosition.getY() + 1; ++k1) {
                            BlockPos blockpos = new BlockPos(k, k1, l);
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
