package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.Elementtimesoretab;
import com.elementtimes.tutorial.common.init.ElementtimesItems;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;

public class Sulfurore extends Block
{
	public Sulfurore()
	{
		super(Material.ROCK);
		setRegistryName("sulfurore");
		setUnlocalizedName("sulfurore");
		setCreativeTab(Elementtimesoretab.tabBlocks);
        setHardness(20.0F); 
        setResistance(10.0F); 
        setHarvestLevel("pickaxe", 2); 
	}
	@Override
	public Item getItemDropped(IBlockState state, Random rand, int fortune){
		return ElementtimesItems.Sulfurpowder; //返回物品
	}

	@Override
	public int quantityDropped(Random rand){
		//return 1; 返回数量1	
		int max = 3; //最多获取数量
		int min = 2; //最少获取数量
		return rand.nextInt(max) + min; //随机数量
	}
}
