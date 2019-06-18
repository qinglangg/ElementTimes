package com.elementtimes.tutorial.annotation.other;

import com.elementtimes.tutorial.annotation.annotations.ModBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * 用于矿物生成
 * @author luqin2007
 */
public class DefaultOreGenerator extends WorldGenerator {
    private final ModBlock.WorldGen mWorldGen;
    private final WorldGenMinable mWorldGenerator;

    public DefaultOreGenerator(ModBlock.WorldGen wgInfo, IBlockState block) {
        this.mWorldGen = wgInfo;
        this.mWorldGenerator = new WorldGenMinable(block, mWorldGen.count());
    }

    @Override
    public boolean generate(@Nonnull World worldIn, @Nonnull Random rand, @Nonnull BlockPos position) {
        if (canGenerator(worldIn.provider.getDimension())) {
            for (int i = 0; i < mWorldGen.times(); i++) {
                int x = position.getX() + 8;
                int y = mWorldGen.YMin() + rand.nextInt(mWorldGen.YRange());
                int z = position.getZ() + 8;
                if (rand.nextFloat() <= mWorldGen.probability()) {
                    mWorldGenerator.generate(worldIn, rand, new BlockPos(x, y, z));
                }
            }
        }
        return true;
    }

    private boolean canGenerator(int dimId) {
        int[] w = mWorldGen.dimWhiteList();
        int[] b = mWorldGen.dimBlackList();

        boolean canGenerator = true;

        if (w.length > 0) {
            canGenerator = ArrayUtils.contains(w, dimId);
        }

        if (canGenerator && b.length > 0) {
            canGenerator = !ArrayUtils.contains(b, dimId);
        }

        return canGenerator;
    }
}
