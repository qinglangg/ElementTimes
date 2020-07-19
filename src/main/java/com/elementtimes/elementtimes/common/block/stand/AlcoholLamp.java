package com.elementtimes.elementtimes.common.block.stand;

import com.elementtimes.elementtimes.common.block.stand.te.TileAlcoholLamp;
import com.elementtimes.elementtimes.common.block.stand.module.ModuleAlcoholLamp;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

/**
 * 酒精灯

 */
public class AlcoholLamp extends BaseModuleBlock<ModuleAlcoholLamp, TileAlcoholLamp> {

    public AlcoholLamp() {
        super(ModuleAlcoholLamp.KEY, TileAlcoholLamp.class, 0.25, 0, 0.25, 0.75, 0.5, 0.75);
        this.setDefaultState(this.getDefaultState().with(BlockStateProperties.ENABLED, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(BlockStateProperties.ENABLED);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (!worldIn.isRemote) {
            return getModule(worldIn, pos).onBlockActivated(state, worldIn, pos, player, handIn, hit);
        }
        return false;
    }
}