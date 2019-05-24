package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Diamondblock extends Block
{
	public Diamondblock()
	{
		super(Material.ROCK);
        setHardness(100.0F); 
        setResistance(30.0F); 
        setHarvestLevel("pickaxe", 3); 
        setLightLevel(100.0F);
	}
}
