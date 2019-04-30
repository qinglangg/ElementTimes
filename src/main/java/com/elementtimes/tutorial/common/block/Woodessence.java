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
        setHardness(50.0F); 
        setResistance(15.0F); 
        setHarvestLevel("axe", 2); 
        setLightLevel(50.0F); //设置方块光源
	}
}
