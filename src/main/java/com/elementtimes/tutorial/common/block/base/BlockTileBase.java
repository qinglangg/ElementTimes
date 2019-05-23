package com.elementtimes.tutorial.common.block.base;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.init.Elementtimestab;
import com.elementtimes.tutorial.common.tileentity.base.TileMachine;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.util.IDismantleBlock;
import net.minecraft.block.BlockContainer;
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
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

/**
 * 需要带有箱子的方块时继承此类
 *
 * @author KSGFK create in 2019/2/17
 */
public class BlockTileBase<T extends TileEntity> extends BlockContainer implements IDismantleBlock {
    protected int gui;
    private boolean addFullEnergyBlock;
    private Class<T> mEntityClass;

    protected BlockTileBase(String registryName, String unlocalizedName, Material materialIn, int gui, boolean addFullEnergyBlock) {
        super(materialIn);
        setCreativeTab(Elementtimestab.tabBlocks);
        setHardness(15.0F);
        setResistance(25.0F);
        setRegistryName(registryName);
        setUnlocalizedName(unlocalizedName);
        this.gui = gui;
        this.addFullEnergyBlock = addFullEnergyBlock;
    }

    public BlockTileBase(String registryName, String unlocalizedName, int gui, Class<T> entityClass, Material material, boolean addFullEnergyBlock) {
        this(registryName, unlocalizedName, material, gui, addFullEnergyBlock);
        this.mEntityClass = entityClass;
    }

    public BlockTileBase(String registryName, String unlocalizedName, int gui, Class<T> entityClass, boolean addFullEnergyBlock) {
        this(registryName, unlocalizedName, gui, entityClass, Material.IRON, addFullEnergyBlock);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        try {
            return mEntityClass.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
            return null;
        }
    }

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
                // fix: x, y, z
                NBTTagCompound tagCompound = stack.getTagCompound().copy();
                tagCompound.setInteger("x", pos.getX());
                tagCompound.setInteger("y", pos.getY());
                tagCompound.setInteger("z", pos.getZ());

                dyn.readFromNBT(tagCompound);
            }
        }
    }

    @Override
    public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) {
        super.getSubBlocks(itemIn, items);
        if (addFullEnergyBlock) {
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

    @Override
    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
        if (worldIn.isRemote) return;
        worldIn.setBlockToAir(pos);
        TileEntity tile = worldIn.getTileEntity(pos);
        if (tile != null) {
            worldIn.removeTileEntity(pos);
        }
    }
}
