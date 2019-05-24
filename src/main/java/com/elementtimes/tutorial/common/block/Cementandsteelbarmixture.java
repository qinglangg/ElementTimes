package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Cementandsteelbarmixture extends Block
{
	public Cementandsteelbarmixture()
	{
		super(Material.ROCK);
        setHardness(10000.0F); 
        setResistance(150.0F); 
        setHarvestLevel("pickaxe", 4); 
	}
}
