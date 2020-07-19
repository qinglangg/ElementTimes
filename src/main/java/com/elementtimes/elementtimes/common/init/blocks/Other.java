package com.elementtimes.elementtimes.common.init.blocks;

import com.elementtimes.elementcore.api.annotation.ModElement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.GlassBlock;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.Random;



@ModElement
public class Other {

    public static Block fr = new GlassBlock(Block.Properties.create(Material.GLASS).hardnessAndResistance(0).tickRandomly()) {

        @Override
        public void tick(BlockState state, World worldIn, BlockPos pos, Random random) {
            super.tick(state, worldIn, pos, random);
            if (!worldIn.isRemote) {
                boolean canRemove = true;
                for (Direction direction : Direction.values()) {
                    if (worldIn.getBlockState(pos.offset(direction)).getBlock() instanceof FlowingFluidBlock) {
                        canRemove = false;
                        break;
                    }
                }
                if (canRemove) {
                    worldIn.removeBlock(pos, false);
                }
            }
        }
    };
}
