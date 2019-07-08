package com.elementtimes.tutorial.common.generator;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraftforge.common.IPlantable;

import java.util.Random;
import java.util.function.Predicate;

/**
 * 用于元素树的生成
 * @author luqin
 */
public class ElementTreeGenerator extends WorldGenAbstractTree {

    private IBlockState mLog;
    private IBlockState mLeaf;

    public ElementTreeGenerator() {
        super(true);
        mLog = ElementtimesBlocks.woodesSence.getDefaultState();
        mLeaf = ElementtimesBlocks.leafesSence.getDefaultState();
    }

    private final int mTreeHeight = 5;
    private final int mTreeRadius = 2;
    private final int mTreeLeafUpper = 2;

    @Override
    public boolean generate(World worldIn, Random rand, BlockPos position) {
        boolean canSpawn = true;
        if (position.getY() >= 1 && position.getY() + mTreeHeight + mTreeLeafUpper < worldIn.getHeight()) {
            // 检查是否可以替换
            canSpawn = testSpawn(position, pos -> isReplaceable(worldIn, pos));
            // 检查是否可以生成
            BlockPos spawnPos = position.down();
            IBlockState spawnOn = worldIn.getBlockState(spawnPos);
            if (spawnOn.getBlock().canSustainPlant(spawnOn, worldIn, spawnPos, EnumFacing.UP, (IPlantable) Blocks.SAPLING)) {
                // 生成
                spawn(position, worldIn);
                return true;
            }
        }
        return false;
    }

    private boolean testSpawn(BlockPos pos, Predicate<BlockPos> tester) {
        BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos(pos);
        for (int pointerY = pos.getY(); pointerY <= pos.getY() + mTreeHeight + mTreeLeafUpper; pointerY++) {
            boolean result = true;
            position.setY(pointerY);
            if (pointerY < pos.getY() + 2) {
                // 只有树干
                result = tester.test(position);
            } else if (pointerY == pos.getY() + 2) {
                // 树叶的特殊形状
                result = tester.test(position.setPos(pos.getX(), pointerY, pos.getZ()))
                        && tester.test(position.setPos(pos.getX() + 1, pointerY, pos.getZ()))
                        && tester.test(position.setPos(pos.getX() - 1, pointerY, pos.getZ()))
                        && tester.test(position.setPos(pos.getX(), pointerY, pos.getZ() + 1))
                        && tester.test(position.setPos(pos.getX(), pointerY, pos.getZ() - 1))
                        && tester.test(position.setPos(pos.getX() + 2, pointerY, pos.getZ() + 2))
                        && tester.test(position.setPos(pos.getX() - 2, pointerY, pos.getZ() + 2))
                        && tester.test(position.setPos(pos.getX() + 2, pointerY, pos.getZ() - 2))
                        && tester.test(position.setPos(pos.getX() - 2, pointerY, pos.getZ() - 2));
            } else if (pointerY < pos.getY() + mTreeHeight) {
                // 树叶的正常形态
                for (int dx = -mTreeRadius; result && dx <= mTreeRadius; dx++) {
                    for (int dz = -mTreeRadius; result && dz <= mTreeRadius; dz++) {
                        result = tester.test(position.setPos(pos.getX() + dx, pointerY, pos.getZ() + dz));
                    }
                }
            } else if (pointerY == pos.getY() + mTreeHeight) {
                // top 0
                for (int dx = -mTreeRadius + 1; result && dx <= mTreeRadius - 1; dx++) {
                    for (int dz = -mTreeRadius + 1; result && dz <= mTreeRadius - 1; dz++) {
                        result = tester.test(position.setPos(pos.getX() + dx, pointerY, pos.getZ() + dz));
                    }
                }
            } else {
                // top 1
                result = tester.test(position.setPos(pos.getX(), pointerY, pos.getZ()));
            }

            if (!result) {
                return false;
            }
        }
        return true;
    }

    private void spawn(BlockPos pos, World worldIn) {
        BlockPos.MutableBlockPos position = new BlockPos.MutableBlockPos(pos);
        for (int pointerY = pos.getY(); pointerY < pos.getY() + mTreeHeight + mTreeLeafUpper; pointerY++) {
            position.setY(pointerY);
            if (pointerY < pos.getY() + 2) {
                // 只有树干
                setBlockAndNotifyAdequately(worldIn, position, mLog);
            } else if (pointerY == pos.getY() + 2) {
                // 树叶的特殊形状
                setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX(), pointerY, pos.getZ()), mLog);
                setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX() + 1, pointerY, pos.getZ()), mLeaf);
                setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX() - 1, pointerY, pos.getZ()), mLeaf);
                setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX(), pointerY, pos.getZ() + 1), mLeaf);
                setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX(), pointerY, pos.getZ() - 1), mLeaf);
                setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX() + 2, pointerY, pos.getZ() + 2), mLeaf);
                setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX() - 2, pointerY, pos.getZ() + 2), mLeaf);
                setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX() + 2, pointerY, pos.getZ() - 2), mLeaf);
                setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX() - 2, pointerY, pos.getZ() - 2), mLeaf);
            } else if (pointerY < pos.getY() + mTreeHeight) {
                // 树叶的正常形态
                for (int dx = -mTreeRadius; dx <= mTreeRadius; dx++) {
                    for (int dz = -mTreeRadius; dz <= mTreeRadius; dz++) {
                        setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX() + dx, pointerY, pos.getZ() + dz), mLeaf);
                    }
                }
                setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX(), pointerY, pos.getZ()), mLog);
            } else if (pointerY == pos.getY() + mTreeHeight) {
                // top 0
                for (int dx = -mTreeRadius + 1; dx <= mTreeRadius - 1; dx++) {
                    for (int dz = -mTreeRadius + 1; dz <= mTreeRadius - 1; dz++) {
                        setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX() + dx, pointerY, pos.getZ() + dz), mLeaf);
                    }
                }
            } else {
                // top 1
                setBlockAndNotifyAdequately(worldIn, position.setPos(pos.getX(), pointerY, pos.getZ()), mLeaf);
            }
        }
    }
}
