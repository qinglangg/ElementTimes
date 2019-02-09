package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tab.Elementtimestab;

public class Cement extends Block
{
	public Cement()
	{
		super(Material.ROCK);
		setRegistryName("cement");
		setUnlocalizedName("cement");
		setCreativeTab(Elementtimestab.tabBlocks);
        setHardness(1000.0F); 
        setResistance(30.0F); 
        setHarvestLevel("Pickaxe", 3); 
	}
}
