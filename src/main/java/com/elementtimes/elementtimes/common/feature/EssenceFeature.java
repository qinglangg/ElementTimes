package com.elementtimes.elementtimes.common.feature;

import com.elementtimes.elementtimes.common.init.blocks.Agriculture;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MutableBoundingBox;
import net.minecraft.world.gen.IWorldGenerationReader;
import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import java.util.Random;
import java.util.Set;



public class EssenceFeature extends AbstractTreeFeature<NoFeatureConfig> {

    private final int mTreeHeight = 5;
    private final int mTreeRadius = 2;
    private final int mTreeLeafUpper = 2;

    public EssenceFeature() {
        super(NoFeatureConfig::deserialize, true);
    }

    @Override
    public boolean place(Set<BlockPos> changedBlocks, IWorldGenerationReader worldIn, Random rand, BlockPos position, MutableBoundingBox unknown) {
        if (testSpawn(worldIn, position)) {
            spawn(changedBlocks, unknown, worldIn, position);
            return true;
        }
        return false;
    }

    private boolean testSpawn(IWorldGenerationReader world, BlockPos pos) {
        BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos(pos);
        for (int pointerY = pos.getY(); pointerY <= pos.getY() + mTreeHeight + mTreeLeafUpper; pointerY++) {
            position.setY(pointerY);
            if (pointerY < pos.getY() + 2) {
                // 只有树干
                if (!func_214587_a(world, position)) {
                    return false;
                }
            } else if (pointerY == pos.getY() + 2) {
                // 树叶的特殊形状
                if (!func_214587_a(world, position.setPos(pos.getX(),     pointerY, pos.getZ()))) { return false; }
                if (!func_214587_a(world, position.setPos(pos.getX() + 1, pointerY, pos.getZ()))) { return false; }
                if (!func_214587_a(world, position.setPos(pos.getX() - 1, pointerY, pos.getZ()))) { return false; }
                if (!func_214587_a(world, position.setPos(pos.getX(),     pointerY, pos.getZ() + 1))) { return false; }
                if (!func_214587_a(world, position.setPos(pos.getX(),     pointerY, pos.getZ() - 1))) { return false; }
                if (!func_214587_a(world, position.setPos(pos.getX() + 2, pointerY, pos.getZ() + 2))) { return false; }
                if (!func_214587_a(world, position.setPos(pos.getX() - 2, pointerY, pos.getZ() + 2))) { return false; }
                if (!func_214587_a(world, position.setPos(pos.getX() + 2, pointerY, pos.getZ() - 2))) { return false; }
                if (!func_214587_a(world, position.setPos(pos.getX() - 2, pointerY, pos.getZ() - 2))) { return false; }
            } else if (pointerY < pos.getY() + mTreeHeight) {
                // 树叶的正常形态
                for (int dx = -mTreeRadius; dx <= mTreeRadius; dx++) {
                    for (int dz = -mTreeRadius; dz <= mTreeRadius; dz++) {
                        if (!func_214587_a(world, position.setPos(pos.getX() + dx, pointerY, pos.getZ() + dz))) {
                            return false;
                        }
                    }
                }
            } else if (pointerY == pos.getY() + mTreeHeight) {
                // top 0
                for (int dx = -mTreeRadius + 1; dx <= mTreeRadius - 1; dx++) {
                    for (int dz = -mTreeRadius + 1; dz <= mTreeRadius - 1; dz++) {
                        if (!func_214587_a(world, position.setPos(pos.getX() + dx, pointerY, pos.getZ() + dz))) {
                            return false;
                        }
                    }
                }
            } else {
                // top 1
                return func_214587_a(world, position.setPos(pos.getX(), pointerY, pos.getZ()));
            }
        }
        return true;
    }

    private void spawn(Set<BlockPos> changedBlocks, MutableBoundingBox unknown, IWorldGenerationReader worldIn, BlockPos pos) {
        BlockState log = Agriculture.logEssence.getDefaultState();
        BlockState leaf = Agriculture.leavesEssence.getDefaultState();

        BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos(pos);
        for (int pointerY = pos.getY(); pointerY < pos.getY() + mTreeHeight + mTreeLeafUpper; pointerY++) {
            position.setY(pointerY);
            if (pointerY < pos.getY() + 2) {
                // 只有树干
                setLogState(changedBlocks, worldIn, position, log, unknown);
            } else if (pointerY == pos.getY() + 2) {
                // 树叶的特殊形状
                setLogState(changedBlocks, worldIn, position.setPos(pos.getX(), pointerY, pos.getZ()), log, unknown);
                setLogState(changedBlocks, worldIn, position.setPos(pos.getX() + 1, pointerY, pos.getZ()), leaf, unknown);
                setLogState(changedBlocks, worldIn, position.setPos(pos.getX() - 1, pointerY, pos.getZ()), leaf, unknown);
                setLogState(changedBlocks, worldIn, position.setPos(pos.getX(), pointerY, pos.getZ() + 1), leaf, unknown);
                setLogState(changedBlocks, worldIn, position.setPos(pos.getX(), pointerY, pos.getZ() - 1), leaf, unknown);
                setLogState(changedBlocks, worldIn, position.setPos(pos.getX() + 2, pointerY, pos.getZ() + 2), leaf, unknown);
                setLogState(changedBlocks, worldIn, position.setPos(pos.getX() - 2, pointerY, pos.getZ() + 2), leaf, unknown);
                setLogState(changedBlocks, worldIn, position.setPos(pos.getX() + 2, pointerY, pos.getZ() - 2), leaf, unknown);
                setLogState(changedBlocks, worldIn, position.setPos(pos.getX() - 2, pointerY, pos.getZ() - 2), leaf, unknown);
            } else if (pointerY < pos.getY() + mTreeHeight) {
                // 树叶的正常形态
                for (int dx = -mTreeRadius; dx <= mTreeRadius; dx++) {
                    for (int dz = -mTreeRadius; dz <= mTreeRadius; dz++) {
                        setLogState(changedBlocks, worldIn, position.setPos(pos.getX() + dx, pointerY, pos.getZ() + dz), leaf, unknown);
                    }
                }
                setLogState(changedBlocks, worldIn, position.setPos(pos.getX(), pointerY, pos.getZ()), log, unknown);
            } else if (pointerY == pos.getY() + mTreeHeight) {
                // top 0
                for (int dx = -mTreeRadius + 1; dx <= mTreeRadius - 1; dx++) {
                    for (int dz = -mTreeRadius + 1; dz <= mTreeRadius - 1; dz++) {
                        setLogState(changedBlocks, worldIn, position.setPos(pos.getX() + dx, pointerY, pos.getZ() + dz), leaf, unknown);
                    }
                }
            } else {
                // top 1
                setLogState(changedBlocks, worldIn, position.setPos(pos.getX(), pointerY, pos.getZ()), leaf, unknown);
            }
        }
    }

}
