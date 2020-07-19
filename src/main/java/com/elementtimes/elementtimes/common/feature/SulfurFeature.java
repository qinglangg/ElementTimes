package com.elementtimes.elementtimes.common.feature;

import com.elementtimes.elementtimes.common.init.blocks.Ore;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.GenerationSettings;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;



public class SulfurFeature extends Feature<NoFeatureConfig> {

    private final BlockState sulfur = Ore.oreSulfur.getDefaultState();

    public SulfurFeature() {
        super(NoFeatureConfig::deserialize);
    }

    @Override
    public boolean place(IWorld worldIn, ChunkGenerator<? extends GenerationSettings> generator, Random rand, BlockPos pos, NoFeatureConfig config) {
        int r = rand.nextInt(1) + 1;
        int size = r + 3;
        BlockPos bp = findSpawnPos(worldIn, pos.add(rand.nextInt(16 - 2 * r), 0, rand.nextInt(16 - 2 * r)));
        if (bp != null) {
            // 生成
            for (int x = bp.getX(); x <= bp.getX() + size * r; ++x) {
                for (int z = bp.getZ(); z <= bp.getZ() + size * r; ++z) {
                    for (int y = bp.getY(); y <= bp.getY() + r; ++y) {
                        BlockPos blockpos = new BlockPos(x, y, z);
                        Block block = worldIn.getBlockState(blockpos).getBlock();
                        if (block == Blocks.STONE || block == Blocks.COBBLESTONE) {
                            setBlockState(worldIn, blockpos, sulfur);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    private BlockPos findSpawnPos(IWorld world, BlockPos pos) {
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

    private boolean isLavaBlock(IWorld world, BlockPos pos) {
        Fluid fluid = world.getBlockState(pos).getFluidState().getFluid();
        return fluid == Fluids.LAVA || fluid == Fluids.FLOWING_LAVA;
    }
}
