package com.elementtimes.elementtimes.common.feature;

import com.elementtimes.elementtimes.common.block.RubberLog;
import com.elementtimes.elementtimes.common.init.blocks.Agriculture;
import com.elementtimes.elementtimes.config.Config;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.Set;



public class RubberFeature extends AbstractTreeFeature<NoFeatureConfig> {

    public RubberFeature() {
        super(NoFeatureConfig::deserialize, true);
    }

    public RubberFeature(boolean doBlockNotifyOnPlace) {
        super(NoFeatureConfig::deserialize, doBlockNotifyOnPlace);
    }

    @Override
    public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox unknown) {
        int minHeight = 4;
        int height = minHeight + rand.nextInt(2);
        // check y
        if (position.getY() <= 0 || position.getY() + height + 1 > worldIn.getMaxHeight()) {
            return false;
        }
        // check block
        for(int y = position.getY(); y <= position.getY() + 1 + height; ++y) {
            int leafWidth;
            if (y == position.getY()) {
                leafWidth = 0;
            } else if (y >= position.getY() + height - 1) {
                leafWidth = 2;
            } else {
                leafWidth = 1;
            }

            BlockPos.MutableBlockPos blocks = new BlockPos.MutableBlockPos();

            for(int x = position.getX() - leafWidth; x <= position.getX() + leafWidth; ++x) {
                for(int z = position.getZ() - leafWidth; z <= position.getZ() + leafWidth; ++z) {
                    // can spawn?
                    if (!func_214587_a(worldIn, blocks.setPos(x, y, z))) {
                        return false;
                    }
                }
            }
        }
        // place
        if (isSoil(worldIn, position.down(), getSapling())) {
            this.setDirtAt(worldIn, position.down(), position);
            for(int y = position.getY() - 3 + height; y <= position.getY() + height; ++y) {
                int dTop = y - (position.getY() + height);
                int w = 1 - dTop / 2;
                for(int x = position.getX() - w; x <= position.getX() + w; ++x) {
                    int dx = x - position.getX();
                    for(int z = position.getZ() - w; z <= position.getZ() + w; ++z) {
                        int dz = z - position.getZ();
                        if (Math.abs(dx) != w || Math.abs(dz) != w || rand.nextInt(2) != 0 && dTop != 0) {
                            BlockPos blockpos = new BlockPos(x, y, z);
                            if (isAirOrLeaves(worldIn, blockpos) || func_214576_j(worldIn, blockpos)) {
                                this.setLogState(changedBlocks, worldIn, blockpos, Agriculture.leavesRubber.getDefaultState(), unknown);
                            }
                        }
                    }
                }
            }

            BlockState log = Agriculture.logRubber.getDefaultState();
            for(int h = 0; h < height; ++h) {
                if (isAirOrLeaves(worldIn, position.up(h)) || func_214576_j(worldIn, position.up(h))) {
                    if (doBlockNotify && rand.nextFloat() <= Config.rubberProb.get()) {
                        setLogState(changedBlocks, worldIn, position.up(h), log.with(RubberLog.HAS_RUBBER, true), unknown);
                    } else {
                        setLogState(changedBlocks, worldIn, position.up(h), log, unknown);
                    }
                }
            }
            return true;
        }
        return false;
    }


}
