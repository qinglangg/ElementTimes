package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Stoneblock extends Block
{
	public Stoneblock()
	{
		super(Material.ROCK);
		setRegistryName("stoneblock");
		setUnlocalizedName("stoneblock");
		setCreativeTab(Elementtimestab.tabBlocks);
        setHardness(100.0F); 
        setResistance(15.0F); 
        setHarvestLevel("pickaxe", 2); 
	}
}
