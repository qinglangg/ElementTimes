package com.elementtimes.tutorial.common.block;

import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

/**
 * @author KSGFK create in 2019/6/4
 */
public class RubberLog extends BlockLog {
    public RubberLog() {
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
    }
}
