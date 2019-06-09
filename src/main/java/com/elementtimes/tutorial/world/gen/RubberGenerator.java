package com.elementtimes.tutorial.world.gen;

import com.elementtimes.tutorial.common.block.RubberLog;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.BlockLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;

import java.util.Random;

/**
 * 用于生成树
 * @author luqin2007
 */
public class RubberGenerator extends WorldGenTrees {

    public RubberGenerator() {
        this(false);
    }

    public RubberGenerator(boolean notify) {
        super(notify, 4,
                ElementtimesBlocks.rubberLog.getDefaultState().withProperty(RubberLog.HAS_RUBBER, false),
                ElementtimesBlocks.rubberLeaf.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, Boolean.TRUE).withProperty(BlockLeaves.DECAYABLE, Boolean.TRUE), false);
    }

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        BlockPos spawn = worldIn.getHeight(position);
        return super.generate(worldIn, rand, spawn);
    }
}
