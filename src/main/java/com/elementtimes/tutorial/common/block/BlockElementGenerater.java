package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.tileentity.TileElementGenerater;
import com.elementtimes.tutorial.common.tileentity.TileMachine;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.util.IDismantleBlock;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
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
public class BlockElementGenerater extends BlockTileBase implements IDismantleBlock {

    public BlockElementGenerater() {
        super(Material.ROCK, 0);
        setRegistryName("elementGenerater");
        setUnlocalizedName("elementGenerater");
        //setBlockUnbreakable();
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileElementGenerater();
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

    @Override
    public ItemStack dismantleBlock(World world, BlockPos pos, IBlockState state, boolean returnDrops) {
        if (!world.isRemote) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile != null) {
                NBTTagCompound tag = tile.serializeNBT();
                ItemStack stack = new ItemStack(state.getBlock());
                stack.setCount(1);
                stack.setTagCompound(tag);
                return stack;
            }
        }
        return ItemStack.EMPTY;
    }

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (worldIn.isRemote) return;
        worldIn.setBlockToAir(pos);
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null) {
            worldIn.removeTileEntity(pos);
        }
    }
	
	@Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        super.getSubBlocks(itemIn, items);
        if (items.isEmpty()) return;
        items.stream().filter(itemStack -> itemStack.getItem() == Item.getItemFromBlock(this)).findFirst().ifPresent(itemStack -> {
            ItemStack fullEnergyGenerator = itemStack.copy();
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setInteger("Energy", ElementtimesConfig.general.generaterMaxEnergy);
            fullEnergyGenerator.setTagCompound(nbt);
            items.add(fullEnergyGenerator);
        });
    }
}
