package com.elementtimes.tutorial.world.gen;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

import static org.jline.utils.Log.warn;

/**
 * 生成盐
 * @author luqin2007
 */
public class WaterGenerator extends WorldGenerator {

    private final IBlockState mBlock;

    public WaterGenerator(Block block) {
        mBlock = block.getDefaultState();
    }

    @Override
    public boolean generate(World worldIn, @Nonnull Random rand, @Nonnull BlockPos position) {
        // 来自于 WorldGenClay
        if (worldIn.getBlockState(position).getMaterial() == Material.WATER) {
            int i = rand.nextInt(2) + 2;
            int j = 1;

            for (int k = position.getX() - i; k <= position.getX() + i; ++k) {
                for (int l = position.getZ() - i; l <= position.getZ() + i; ++l) {
                    int i1 = k - position.getX();
                    int j1 = l - position.getZ();

                    if (i1 * i1 + j1 * j1 <= i * i) {
                        for (int k1 = position.getY() - 1; k1 <= position.getY() + 1; ++k1) {
                            BlockPos blockpos = new BlockPos(k, k1, l);
                            Block block = worldIn.getBlockState(blockpos).getBlock();

                            if (block == Blocks.DIRT || block == Blocks.CLAY || block == ElementtimesBlocks.oreSalt) {
                                warn("生成成功，位于 {}", blockpos);
                                worldIn.setBlockState(blockpos, mBlock, 2);
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
