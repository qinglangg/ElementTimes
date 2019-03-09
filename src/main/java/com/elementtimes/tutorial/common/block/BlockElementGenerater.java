package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.tileentity.TileElementGenerater;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 发电机
 *
 * @author KSGFK create in 2019/2/17
 */
public class BlockElementGenerater extends BlockTileBase {

    public BlockElementGenerater() {
        super(Material.IRON, 0);
        setRegistryName("elementGenerater");
        setUnlocalizedName("elementGenerater");
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        NBTTagCompound nbt = new NBTTagCompound();
        TileElementGenerater egen = new TileElementGenerater();
        egen.readFromNBT(nbt);
        return egen;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity e = worldIn.getTileEntity(pos);
            if (e instanceof TileElementGenerater) {
                ((TileElementGenerater) e).setPlayer((EntityPlayerMP) playerIn);
                playerIn.openGui(Elementtimes.instance, 0, worldIn, pos.getX(), pos.getY(), pos.getZ());
                ((TileElementGenerater) e).setOpenGui(true);
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (!worldIn.isRemote) {
            TileEntity e = worldIn.getTileEntity(pos);
            if (e instanceof TileElementGenerater && stack.getTagCompound() != null) {
                TileElementGenerater dyn = (TileElementGenerater) e;
                dyn.readFromNBT(stack.getTagCompound());
            }
        }
    }
    /*
    @Override
    public ArrayList<ItemStack> dismantleBlock(World world, BlockPos pos, IBlockState state, EntityPlayer player, boolean returnDrops) {
        TileEntity tile = world.getTileEntity(pos);
        NBTTagCompound retTag = null;
        if (tile instanceof TileElementGenerater) {
            TileElementGenerater dyn = (TileElementGenerater) tile;
            retTag = dyn.writeToNBT(dyn.getNbtTagCompound());
            dyn.inventory = new ItemStack[dyn.inventory.length];
            Arrays.fill(dyn.inventory, ItemStack.EMPTY);
        }
        return dismantleDelegate(retTag, world, pos, player, returnDrops, false);
    }
    */
}
