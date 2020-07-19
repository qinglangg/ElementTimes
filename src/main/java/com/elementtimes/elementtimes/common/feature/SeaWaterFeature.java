package com.elementtimes.elementtimes.common.feature;

import com.elementtimes.elementtimes.common.fluid.FluidBlocks;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;



public class SeaWaterFeature extends Feature<NoFeatureConfig> {

    private final int CHUNK = 16;
    private final int HEIGHT = 5;

    public SeaWaterFeature() {
        super(NoFeatureConfig::deserialize);
    }

    public SeaWaterFeature(boolean doBlockNotifyIn) {
        super(NoFeatureConfig::deserialize, doBlockNotifyIn);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        for (int offsetX = 0; offsetX < CHUNK; offsetX++) {
            for (int offsetZ = 0; offsetZ < CHUNK; offsetZ++) {
                if (worldIn.getBiome(pos).getTempCategory() == Biome.TempCategory.OCEAN) {
                    for (int offsetY = 0; offsetY < 3; offsetY++) {
                        if (worldIn.getBlockState(pos).getBlock() == Blocks.WATER) {
                            setBlockState(worldIn, pos.add(offsetX, -offsetY, offsetZ), FluidBlocks.fluidSeaWater.getDefaultState());
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }
}
