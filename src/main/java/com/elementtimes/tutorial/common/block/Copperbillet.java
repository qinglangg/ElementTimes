package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tab.Elementtimestab;

public class Copperbillet extends Block
{
	public Copperbillet()
	{
		super(Material.ROCK);
		setRegistryName("copperbillet");
		setUnlocalizedName("copperbillet");
		setCreativeTab(Elementtimestab.tabBlocks);
        setHardness(100.0F); 
        setResistance(15.0F); 
        setHarvestLevel("pickaxe", 2); 
	}
}
