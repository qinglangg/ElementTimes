package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.init.Elementtimestab;
import com.elementtimes.tutorial.common.tileentity.TileMachine;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * 需要带有箱子的方块时继承此类
 *
 * @author KSGFK create in 2019/2/17
 */
public abstract class BlockTileBase extends BlockContainer {
    protected int gui;

    protected BlockTileBase(Material materialIn, int gui) {
        super(materialIn);
        this.setCreativeTab(Elementtimestab.tabBlocks);
        this.setHardness(15.0F);
        this.setResistance(25.0F);
        this.gui = gui;
    }

    @Nullable
    @Override
    public abstract TileEntity createNewTileEntity(World worldIn, int meta);

    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {//渲染类型设为普通方块
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null) {
            worldIn.removeTileEntity(pos);
        }
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity e = worldIn.getTileEntity(pos);
            if (e instanceof TileMachine) {
                TileMachine machine = (TileMachine) e;
                machine.setPlayer((EntityPlayerMP) playerIn);
                playerIn.openGui(Elementtimes.instance, gui, worldIn, pos.getX(), pos.getY(), pos.getZ());
                machine.setOpenGui(true);
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!worldIn.isRemote) {
            TileEntity e = worldIn.getTileEntity(pos);
            if (e instanceof TileMachine && stack.getTagCompound() != null) {
                TileMachine dyn = (TileMachine) e;
                dyn.readFromNBT(stack.getTagCompound());
            }
        }
    }
}
