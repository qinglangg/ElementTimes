package com.elementtimes.elementtimes.common.fluid;

import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.capability.CapabilitySeaWater;
import com.elementtimes.elementtimes.common.potion.Salt;
import net.minecraft.block.BlockState;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 海水
 * 主要增加浸入方块的玩家的药水效果

 */
public class SeaWaterBlock extends FlowingFluidBlock {

    public SeaWaterBlock() {
        super(() -> Sources.seaWater, FluidBlocks.prop());
        setRegistryName(ElementTimes.MODID, "seawater");
    }

    @Override
    public void onEntityCollision(BlockState state, World worldIn, BlockPos pos, Entity entityIn) {
        super.onEntityCollision(state, worldIn, pos, entityIn);
        if (!worldIn.isRemote && entityIn instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entityIn;
            player.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER).ifPresent(cap -> {
                Salt.effectOn(player, cap);
                cap.collidedInSea();
            });
        }
    }
}
