package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

import java.util.Random;

public class Sulfurore extends Block
{
	public Sulfurore()
	{
		super(Material.ROCK);
        setHardness(20.0F); 
        setResistance(10.0F); 
        setHarvestLevel("pickaxe", 2); 
	}
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return ElementtimesItems.sulfurPowder;
	}

	@Override
	public int quantityDropped(Random rand){
		return rand.nextInt(3) + 2;
	}
}
