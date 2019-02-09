package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tab.Elementtimestab;

public class Cementandsteelbarmixture extends Block
{
	public Cementandsteelbarmixture()
	{
		super(Material.ROCK);
		setRegistryName("cementandsteelbarmixture");
		setUnlocalizedName("cementandsteelbarmixture");
		setCreativeTab(Elementtimestab.tabBlocks);
        setHardness(10000.0F); 
        setResistance(150.0F); 
        setHarvestLevel("Pickaxe", 4); 
	}
}
