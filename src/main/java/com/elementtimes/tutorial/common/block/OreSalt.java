package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

/**
 * @author 盐矿
 */
public class OreSalt extends Block {
    public OreSalt() {
        super(Material.CLAY);
    }

    @Override
    public boolean canSilkHarvest(World world, BlockPos pos, @Nonnull IBlockState state, EntityPlayer player) {
        return true;
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ElementtimesItems.salt;
    }

    @Override
    public int quantityDropped(IBlockState state, int fortune, Random random) {
        int nextInt = random.nextInt(4);
        float r = 4f - nextInt;
        return (int) (2 + nextInt + r * fortune / Enchantments.FORTUNE.getMaxLevel());
    }
}
