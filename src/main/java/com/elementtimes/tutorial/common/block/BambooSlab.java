package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.BlockPurpurSlab;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.minecraft.block.BlockPurpurSlab.VARIANT;

/**
 * 竹半阶
 * @author luqin2007
 */
public abstract class BambooSlab extends BlockSlab {

    public static BambooSlab half() {
        return (BambooSlab) new Half().setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("woodSlab");
    }

    public static BambooSlab double_() {
        return (BambooSlab) new Double().setHardness(2.0F).setResistance(5.0F).setUnlocalizedName("woodSlab");
    }

    public BambooSlab() {
        super(Material.WOOD);
        setSoundType(SoundType.WOOD);
        IBlockState defaultState = getDefaultState().withProperty(VARIANT, BlockPurpurSlab.Variant.DEFAULT);
        if (!isDouble()) {
            defaultState = defaultState.withProperty(HALF, BlockSlab.EnumBlockHalf.BOTTOM);
        }
        setDefaultState(defaultState);
    }

    @Nonnull
    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(ElementtimesBlocks.bambooSlab);
    }

    @Nonnull
    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, @Nonnull IBlockState state) {
        return new ItemStack(ElementtimesBlocks.bambooSlab);
    }

    @Nonnull
    @Override
    public IBlockState getStateFromMeta(int meta) {
        IBlockState state = getDefaultState().withProperty(VARIANT, BlockPurpurSlab.Variant.DEFAULT);
        if (!this.isDouble()) {
            state = state.withProperty(HALF, (meta & 0b0001) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        if (!this.isDouble() && state.getValue(HALF) == BlockSlab.EnumBlockHalf.TOP) {
            return 1;
        }
        return 0;
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
    }

    @Override
    public String getUnlocalizedName(int meta) {
        return super.getUnlocalizedName();
    }

    @Override
    public IProperty<?> getVariantProperty() {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack stack) {
        return BlockPurpurSlab.Variant.DEFAULT;
    }

    public static class Half extends BambooSlab {

        @Override
        public boolean isDouble() {
            return false;
        }
    }

    public static class Double extends BambooSlab {

        @Override
        public boolean isDouble() {
            return true;
        }

        @Override
        public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
            return BlockFaceShape.SOLID;
        }
    }
}
