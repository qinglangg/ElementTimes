package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import java.util.Random;

/**
 * @author KSGFK create in 2019/6/4
 */
public class RubberSapling extends BlockBush implements IGrowable {

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        if (worldIn.isRemote) return;
        if (!TerrainGen.saplingGrowTree(worldIn, rand, pos)) return;

        WorldGenerator worldgenerator = new WorldGenTrees(
                true,
                4,
                ElementtimesBlocks.rubberLog.getDefaultState(),
                ElementtimesBlocks.rubberLeaf.getDefaultState(),
                false);
        IBlockState iblockstate2 = Blocks.AIR.getDefaultState();
        int i = 0;
        int j = 0;

        worldIn.setBlockState(pos, iblockstate2, 4);
        if (!worldgenerator.generate(worldIn, rand, pos.add(i, 0, j))) {
            worldIn.setBlockState(pos, state, 4);
        }
    }
}
