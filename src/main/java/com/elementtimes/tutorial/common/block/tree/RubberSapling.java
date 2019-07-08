package com.elementtimes.tutorial.common.block.tree;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.interfaces.tileentity.IConfigApply;
import com.elementtimes.tutorial.common.generator.RubberGenerator;
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
 * @author KSGFK create in 2019/6/4
 */
public class RubberSapling extends BlockBush implements IGrowable, IConfigApply {
    private int rubberProbability;

    public RubberSapling() {
        applyConfig();
    }

    @Override
    public boolean canGrow(@Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        return worldIn.rand.nextFloat() < 0.45D;
    }

    @Override
    public void grow(World worldIn, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull IBlockState state) {
        if (!worldIn.isRemote && TerrainGen.saplingGrowTree(worldIn, rand, pos)) {
            WorldGenerator worldgenerator = new RubberGenerator(true);

            IBlockState air = Blocks.AIR.getDefaultState();
            worldIn.setBlockState(pos, air, 4);
            if (!worldgenerator.generate(worldIn, rand, pos.add(0, 0, 0))) {
                worldIn.setBlockState(pos, state, 4);
            } else {
                int high = 0;
                while (worldIn.getBlockState(pos.up(high)).getBlock() == ElementtimesBlocks.rubberLog) {
                    high++;
                }
                for (int a = 0; a < high; a++) {
                    if (rand.nextInt(100) < rubberProbability) {
                        IBlockState s = worldIn.getBlockState(pos.up(a));
                        worldIn.setBlockState(pos.up(a), s.withProperty(RubberLog.HAS_RUBBER, true));
                    }
                }
            }
        }
    }

    @Override
    public void applyConfig() {
        rubberProbability = ElementtimesConfig.GENERAL.rubberTreeGenRubberProbability;
    }
}
