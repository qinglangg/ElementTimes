package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

/**
 * @author KSGFK
 */
public class Calciumfluoride extends Block {
	public Calciumfluoride() {
		super(Material.ROCK);
        setHardness(20.0F); 
        setResistance(10.0F); 
        setHarvestLevel("pickaxe", 2); 
	}
}
