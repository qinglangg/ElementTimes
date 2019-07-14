package com.elementtimes.tutorial.common.block.base;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import com.elementtimes.tutorial.interfaces.block.IDismantleBlock;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
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
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;

/**
 * 需要带有 TileEntity 的方块时继承此类
 *
 * @author KSGFK create in 2019/2/17
 */
public class BlockTileBase<T extends TileEntity> extends BlockContainer implements IDismantleBlock {

    private boolean addFullEnergyBlock;
    private Class<T> mEntityClass;
    private int mGui;

    private BlockTileBase(Material materialIn, int gui, boolean addFullEnergyBlock) {
        super(materialIn);
        setHardness(15.0F);
        setResistance(25.0F);
        mGui = gui;
        this.addFullEnergyBlock = addFullEnergyBlock;
    }

    public BlockTileBase(int gui, Class<T> entityClass, boolean addFullEnergyBlock) {
        this(Material.IRON, gui, addFullEnergyBlock);
        this.mEntityClass = entityClass;
    }

    public <T2 extends BaseMachine> BlockTileBase(Class<T2> entityClass, boolean addFullEnergyBlock) {
        this(Material.IRON, 0, addFullEnergyBlock);
        this.mEntityClass = (Class<T>) entityClass;
    }

    public <T2 extends BaseMachine> BlockTileBase(Class<T2> entityClas) {
        this(Material.IRON, 0, false);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@SuppressWarnings("NullableProblems") World worldIn, int meta) {
        try {
            if (mEntityClass != null) {
                for (Constructor<?> constructor : mEntityClass.getDeclaredConstructors()) {
                    constructor.setAccessible(true);
                    if (constructor.getParameterCount() == 0) {
                        return (TileEntity) constructor.newInstance();
                    }
                }
            }
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            return null;
        }
        return null;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public EnumBlockRenderType getRenderType(IBlockState state) {//渲染类型设为普通方块
        return EnumBlockRenderType.MODEL;
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity e = worldIn.getTileEntity(pos);
            if (e instanceof BaseMachine) {
                playerIn.openGui(ElementTimes.instance, ((BaseMachine) e).getGuiType().id(), worldIn, pos.getX(), pos.getY(), pos.getZ());
            } else {
                playerIn.openGui(ElementTimes.instance, mGui, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        if (!worldIn.isRemote) {
            TileEntity e = worldIn.getTileEntity(pos);
            if (e instanceof BaseMachine && stack.getTagCompound() != null) {
                BaseMachine dyn = (BaseMachine) e;
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
        if (addFullEnergyBlock && !items.isEmpty()) {
            items.stream().filter(itemStack -> itemStack.getItem() == Item.getItemFromBlock(this)).findFirst().ifPresent(itemStack -> {
                ItemStack fullEnergyGenerator = itemStack.copy();
                NBTTagCompound nbt = new NBTTagCompound();
                NBTTagCompound eNBT = new NBTTagCompound();
                eNBT.setBoolean("full", true);
                nbt.setTag("energy", eNBT);
                fullEnergyGenerator.setTagCompound(nbt);
                items.add(fullEnergyGenerator);
            });
        }
    }

//    @Override
//    @SuppressWarnings("NullableProblems")
//    // 不知道要不要删除。使用这个结果是无法用稿子敲下来
//    public void harvestBlock(World worldIn, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack stack) {
//        if (worldIn.isRemote) return;
//        worldIn.setBlockToAir(pos);
//        TileEntity tile = worldIn.getTileEntity(pos);
//        if (tile != null) {
//            worldIn.removeTileEntity(pos);
//        }
//    }
}
