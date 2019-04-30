package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.Elementtimesoretab;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Diamondblock extends Block
{
	public Diamondblock()
	{
		super(Material.ROCK);
		setRegistryName("diamondblock");
		setUnlocalizedName("diamondblock");
		setCreativeTab(Elementtimesoretab.tabBlocks);
        setHardness(100.0F); 
        setResistance(30.0F); 
        setHarvestLevel("pickaxe", 3); 
        setLightLevel(100.0F);
	}
}
