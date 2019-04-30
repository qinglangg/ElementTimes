package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.Elementtimesoretab;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Copperbillet extends Block
{
	public Copperbillet()
	{
		super(Material.ROCK);
		setRegistryName("copperbillet");
		setUnlocalizedName("copperbillet");
		setCreativeTab(Elementtimesoretab.tabBlocks);
        setHardness(50.0F); 
        setResistance(15.0F); 
        setHarvestLevel("pickaxe", 2); 
	}
}
