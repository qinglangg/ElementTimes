package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.tileentity.TilePulverizer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * @author KSGFK create in 2019/5/6
 */
public class BlockPulverizer extends BlockTileBase {
    public BlockPulverizer() {
        super(Material.IRON, 1);
        setRegistryName("pulverizer");
        setUnlocalizedName("pulverizer");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TilePulverizer();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity e = worldIn.getTileEntity(pos);
            if (e instanceof TilePulverizer) {
                ((TilePulverizer) e).setPlayer((EntityPlayerMP) playerIn);
                playerIn.openGui(Elementtimes.instance, 1, worldIn, pos.getX(), pos.getY(), pos.getZ());
                ((TilePulverizer) e).setOpenGui(true);
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!worldIn.isRemote) {
            TileEntity e = worldIn.getTileEntity(pos);
            if (e instanceof TilePulverizer && stack.getTagCompound() != null) {
                TilePulverizer dyn = (TilePulverizer) e;
                dyn.readFromNBT(stack.getTagCompound());
            }
        }
    }
}
