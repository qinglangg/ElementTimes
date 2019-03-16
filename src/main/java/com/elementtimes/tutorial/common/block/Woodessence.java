package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.Elementtimestab;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

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
        setLightLevel(100.0F); //设置方块光源
	}
}
