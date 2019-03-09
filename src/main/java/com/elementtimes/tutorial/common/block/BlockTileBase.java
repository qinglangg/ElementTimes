package com.elementtimes.tutorial.common.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.world.World;
import tab.Elementtimestab;

import javax.annotation.Nullable;

/**
 * 需要带有箱子的方块时继承此类
 *
 * @author KSGFK create in 2019/2/17
 */
public abstract class BlockTileBase extends BlockContainer {
    protected BlockTileBase(Material materialIn) {
        super(materialIn);
        this.setCreativeTab(Elementtimestab.tabBlocks);
        this.setHardness(15.0F);
        this.setResistance(25.0F);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return null;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {//渲染类型设为普通方块
        return EnumBlockRenderType.MODEL;
    }
}
