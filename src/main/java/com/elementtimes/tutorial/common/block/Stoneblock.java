package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Stoneblock extends Block
{
	public Stoneblock()
	{
		super(Material.ROCK);
        setHardness(100.0F); 
        setResistance(15.0F); 
        setHarvestLevel("pickaxe", 2); 
	}
}
