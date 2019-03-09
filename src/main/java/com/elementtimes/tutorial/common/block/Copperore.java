package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tab.Elementtimestab;

public class Copperore extends Block
{
	public Copperore()
	{
		super(Material.ROCK);
		setRegistryName("copperore");
		setUnlocalizedName("copperore");
		setCreativeTab(Elementtimestab.tabBlocks);
        setHardness(40.0F); 
        setResistance(10.0F); 
        setHarvestLevel("pickaxe", 2); 
	}
}
