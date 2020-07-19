package com.elementtimes.elementtimes.common.block;

import com.elementtimes.elementcore.api.block.BlockClosable;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.common.block.machine.TileCompressor;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.state.StateContainer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nonnull;

import static net.minecraftforge.common.property.Properties.StaticProperty;



public class Compressor extends BlockClosable {

    public Compressor() {
        super(Industry.machine(), Industry.te(TileCompressor.class));
        setDefaultState(getDefaultState().with(StaticProperty, false));

    }

    @Override
    protected void fillStateContainer(@Nonnull StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        // TODO AnimationProperty ?
        builder.add(StaticProperty);
    }

    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return VoxelShapes.create(0, 0, 0, 1, 1.475, 1);
    }
}
