package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import tab.Elementtimestab;

public class Woodessence extends Block
{
	public Woodessence()
	{
		super(Material.WOOD);
		setRegistryName("woodessence");
		setUnlocalizedName("woodessence");
		setCreativeTab(Elementtimestab.tabBlocks);
        setHardness(1000.0F); 
        setResistance(20.0F); 
        setHarvestLevel("axe", 4); 
	}
}
