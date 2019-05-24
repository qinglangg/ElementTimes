package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Cement extends Block
{
	public Cement()
	{
		super(Material.ROCK);
        setHardness(1000.0F); 
        setResistance(30.0F); 
        setHarvestLevel("pickaxe", 3); 
	}
}
