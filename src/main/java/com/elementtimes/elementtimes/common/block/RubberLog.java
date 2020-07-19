package com.elementtimes.elementtimes.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.LogBlock;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.IProperty;
import net.minecraft.state.StateContainer;



public class RubberLog extends LogBlock {

    public static IProperty<Boolean> HAS_RUBBER = BooleanProperty.create("rubber");

    public RubberLog() {
        super(Material.WOOD.getColor(), Properties.create(Material.WOOD).hardnessAndResistance(2).sound(SoundType.WOOD));
        setDefaultState(getDefaultState().with(HAS_RUBBER, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(HAS_RUBBER);
    }
}
