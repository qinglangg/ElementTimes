package com.elementtimes.tutorial.common.block.tree;

import com.elementtimes.tutorial.world.gen.ElementTreeGenerator;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.TerrainGen;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * 元素树
 * @author luqin2007
 */
public class EssenceSapling extends BlockBush implements IGrowable {

    @Override
    public boolean canGrow(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return worldIn.rand.nextFloat() < 0.15D;
    }

    @Override
    public void grow(World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!worldIn.isRemote && TerrainGen.saplingGrowTree(worldIn, rand, pos)) {
            WorldGenerator worldgenerator = new ElementTreeGenerator();

            IBlockState air = Blocks.AIR.getDefaultState();
            worldIn.setBlockState(pos, air, 4);
            if (!worldgenerator.generate(worldIn, rand, pos)) {
                worldIn.setBlockState(pos, state, 4);
            }
        }
    }
}
