package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Platinumblock extends Block
{
	public Platinumblock()
	{
		super(Material.ROCK);
        setHardness(50.0F); 
        setResistance(15.0F); 
        setHarvestLevel("pickaxe", 2); 
        setLightLevel(50.0F);
	}
}
