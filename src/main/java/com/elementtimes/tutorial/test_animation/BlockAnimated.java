package com.elementtimes.tutorial.test_animation;

import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.model.TRSRTransformation;
import net.minecraftforge.common.property.ExtendedBlockState;
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.common.property.IUnlistedProperty;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;

import static net.minecraftforge.common.property.Properties.AnimationProperty;
import static net.minecraftforge.common.property.Properties.StaticProperty;

@SideOnly(Side.CLIENT)
public class BlockAnimated extends Block implements ITileEntityProvider {

    public BlockAnimated() {
        super(Material.ROCK);
        setDefaultState(getDefaultState().withProperty(StaticProperty, false));
    }


    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileBlockAnimated();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new ExtendedBlockState(this, new IProperty[]{StaticProperty}, new IUnlistedProperty[]{AnimationProperty});
    }


    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(StaticProperty) ? 0b0000 : 0b0001;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        return getDefaultState().withProperty(StaticProperty, (meta & 0b0001) == 0b0000);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            IBlockState oldState = worldIn.getBlockState(pos);
            IBlockState newState = oldState.withProperty(StaticProperty, !oldState.getValue(StaticProperty));
            worldIn.setBlockState(pos, newState, 3);
            worldIn.markBlockRangeForRenderUpdate(pos, pos);
        }
        return true;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }


}
