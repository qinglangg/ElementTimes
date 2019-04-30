package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.Elementtimesoretab;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Platinumore extends Block
{
	public Platinumore()
	{
		super(Material.ROCK);
		setRegistryName("platinumore");
		setUnlocalizedName("platinumore");
		setCreativeTab(Elementtimesoretab.tabBlocks);
        setHardness(20.0F); 
        setResistance(10.0F); 
        setHarvestLevel("pickaxe", 2); 
	}
}
