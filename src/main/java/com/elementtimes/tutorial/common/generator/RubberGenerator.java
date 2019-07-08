package com.elementtimes.tutorial.common.generator;

import com.elementtimes.tutorial.common.block.tree.RubberLog;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.BlockLeaves;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenTrees;
import net.minecraft.world.gen.feature.WorldGenerator;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * 用于生成树
 * @author luqin2007
 */
public class RubberGenerator extends WorldGenTrees {

    public RubberGenerator(boolean notify) {
        super(notify, 4,
                ElementtimesBlocks.rubberLog.getDefaultState().withProperty(RubberLog.HAS_RUBBER, false),
                ElementtimesBlocks.rubberLeaf.getDefaultState().withProperty(BlockLeaves.CHECK_DECAY, Boolean.TRUE).withProperty(BlockLeaves.DECAYABLE, Boolean.TRUE), false);
    }

    @Override
    public boolean generate(@Nonnull World worldIn, Random rand, BlockPos position) {
        return super.generate(worldIn, rand, worldIn.getHeight(position));
    }

    /**
     * 橡胶树自然生成
     */
    public static class RubberNatureGenerator extends WorldGenerator {

        private static final int CHUNK_SIZE = 16;
        private static final int TREE_RADIUS = 3;
        private static final int MAX_SPAWN_COUNT = 1;
        private static final float PROBABILITY = 0.05f / 20f;

        @Override
        public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos position) {
            WorldGenerator generator = new RubberGenerator(false);
            int spawnCount = 0;
            int startX = position.getX();
            int startZ = position.getZ();
            for (int x = startX + TREE_RADIUS; spawnCount < MAX_SPAWN_COUNT && x < startX + CHUNK_SIZE - TREE_RADIUS; x += TREE_RADIUS) {
                for (int z = startZ + TREE_RADIUS; spawnCount < MAX_SPAWN_COUNT && z < startZ + CHUNK_SIZE - TREE_RADIUS; z += TREE_RADIUS) {
                    BlockPos pos = new BlockPos(x, worldIn.getHeight(x, z), z);
                    float f = rand.nextFloat();
                    if (f <= PROBABILITY) {
                        if (generator.generate(worldIn, rand, pos)) {
                            System.out.println("Spawn At " + pos);
                            spawnCount++;
                        }
                    }
                }
            }
            return spawnCount > 0;
        }
    }
}
