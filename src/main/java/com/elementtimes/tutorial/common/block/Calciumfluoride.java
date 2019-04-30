package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.Elementtimesoretab;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Calciumfluoride extends Block
{
	public Calciumfluoride()
	{
		super(Material.ROCK);
		setRegistryName("calciumfluoride");
		setUnlocalizedName("calciumfluoride");
		setCreativeTab(Elementtimesoretab.tabBlocks);
        setHardness(20.0F); 
        setResistance(10.0F); 
        setHarvestLevel("pickaxe", 2); 
	}
}
