package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockRenderLayer;
import tab.Elementtimestab;

public class Leafessence extends Block
{
	public Leafessence()
	{
		super(Material.WOOD);
		setRegistryName("leafessence");
		setUnlocalizedName("leafessence");
		setCreativeTab(Elementtimestab.tabBlocks);
        setHardness(5.0F); 
        setResistance(10.0F); 
	}
	
	
	@Override
	public BlockRenderLayer getBlockLayer()
	{
		return BlockRenderLayer.CUTOUT;
	}
	
	
	@Override
    public boolean isOpaqueCube(IBlockState state)
	{
        return false;
    }
	
}
