package com.elementtimes.tutorial.common.block.base;

import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;

/**
 * @author KSGFK create in 2019/6/4
 */
public class BaseLogBlock extends BlockRotatedPillar {
    public BaseLogBlock(Material material) {
        super(material);
        this.setDefaultState(this.blockState.getBaseState().withProperty(AXIS, EnumFacing.Axis.Y));
    }
}
