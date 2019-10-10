package com.elementtimes.tutorial.common.generator;

import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * 海底海水替换
 * @author luqin2007
 */
public class OceanGenerator extends WorldGenerator {

    private final int CHUNK = 16;
    private final int HEIGHT = 5;

    @Override
    public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos position) {
        for (int offsetX = 0; offsetX < CHUNK; offsetX++) {
            for (int offsetZ = 0; offsetZ < CHUNK; offsetZ++) {
                BlockPos pos = worldIn.getTopSolidOrLiquidBlock(position.add(offsetX, 0 , offsetZ));
                if (worldIn.getBiome(pos).getTempCategory() == Biome.TempCategory.OCEAN) {
                    for (int offsetY = 0; offsetY < HEIGHT; offsetY++) {
                        if (worldIn.getBlockState(pos).getBlock() == Blocks.WATER) {
                            setBlockAndNotifyAdequately(worldIn, pos, ElementtimesFluids.seawater.getBlock().getDefaultState());
                        }
                        pos = pos.up();
                    }
                }
            }
        }
        return false;
    }
}
