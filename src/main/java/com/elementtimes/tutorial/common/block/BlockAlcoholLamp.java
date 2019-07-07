package com.elementtimes.tutorial.common.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * 酒精灯
 * TODO:像熔炉一样的光照
 *
 * @author KSGFK create in 2019/6/12
 */
public class BlockAlcoholLamp extends Block {
    private final AxisAlignedBB aabb = new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D);
    public static final IProperty<Boolean> IS_BURNING = PropertyBool.create("burning");

    public BlockAlcoholLamp() {
        super(Material.GLASS);
        this.setDefaultState(this.getDefaultState().withProperty(IS_BURNING, Boolean.FALSE));
        this.setLightLevel(0);
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return aabb;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, IS_BURNING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(IS_BURNING) ? 0b0100 : 0b0000;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        boolean burning = (meta & 0b0100) == 0b0100;
        return super.getStateFromMeta(meta).withProperty(IS_BURNING, burning);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (worldIn.isRemote) {
            return true;
        }

        ItemStack held = playerIn.getHeldItem(hand);
        Item heldItem = held.getItem();

        if (heldItem == Items.FLINT_AND_STEEL) {
            //this.setLightLevel(1);
            worldIn.setBlockState(pos, state.withProperty(IS_BURNING, Boolean.TRUE));
        } else {
            //this.setLightLevel(0);
            worldIn.setBlockState(pos, state.withProperty(IS_BURNING, Boolean.FALSE));
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (getMetaFromState(stateIn) != 0b0100) {
            return;
        }

        double d0 = pos.getX();
        double d1 = pos.getY();
        double d2 = pos.getZ();
        worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.5D, d1 + 0.5D, d2 + 0.5D, 0.0D, 0D, 0.0D);
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }
}
