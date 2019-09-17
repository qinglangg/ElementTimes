package com.elementtimes.tutorial.common.block;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.elementcore.api.template.block.interfaces.IDismantleBlock;
import com.elementtimes.elementcore.api.template.capability.fluid.ITankHandler;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileTESR;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeCapture;
import com.elementtimes.tutorial.common.block.interfaces.ISupportStandModule;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.TileEvaporatingDish;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import it.unimi.dsi.fastutil.ints.IntArraySet;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

/**
 * 蒸发皿
 * @author luqin2007
 */
public class EvaporatingDish extends BlockAABB implements ITileEntityProvider, IDismantleBlock, ISupportStandModule {

    public static final String BIND_EVAPORATING_DISH = "_nbt_dish_";
    private static final String BIND_EVAPORATING_DISH_FLUIDS = "_nbt_dish_f_";
    // private static final String BIND_EVAPORATING_DISH_ITEMS = "_nbt_dish_i_";
    private static final String BIND_EVAPORATING_DISH_RECIPE = "_nbt_dish_r_";
    private static final String BIND_EVAPORATING_DISH_PROCESS = "_nbt_dish_p_";

    public EvaporatingDish() {
        super(new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.25D, 0.75D));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileEvaporatingDish();
    }

    @Nonnull
    @Override
    public ItemStack getModelItem() {
        return new ItemStack(this);
    }

    @Nonnull
    @Override
    public String getKey() {
        return BIND_EVAPORATING_DISH;
    }

    @Nullable
    @Override
    public ITileTESR.RenderObject createRender() {
        return new ITileTESR.RenderObject(getModelItem()).translate(.5, .375, .5).scale(3, 3, 3);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEvaporatingDish ted = (TileEvaporatingDish) worldIn.getTileEntity(pos);
        NBTTagCompound nbtDish = stack.getOrCreateSubCompound(BIND_EVAPORATING_DISH);
        if (ted != null && nbtDish.hasKey(BIND_EVAPORATING_DISH)) {
            ted.nbt = nbtDish.getCompoundTag(BIND_EVAPORATING_DISH);
            ted.markDirty();
        }
    }

    @Nonnull
    @Override
    public ItemStack getDismantleItem(World world, BlockPos pos) {
        ItemStack dish = new ItemStack(this);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileEvaporatingDish) {
            NBTTagCompound nbt = ((TileEvaporatingDish) te).nbt;
            if (!nbt.hasNoTags()) {
                dish.getOrCreateSubCompound(BIND_EVAPORATING_DISH).setTag(BIND_EVAPORATING_DISH, nbt);
            }
        }
        return dish;
    }

    @Override
    public void addModule(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileSupportStand) {
            TileSupportStand tss = (TileSupportStand) te;
            tss.renderModule(this);
            ItemStack stack = playerIn.getHeldItem(hand);
            NBTTagCompound tagCompound = stack.getOrCreateSubCompound(BIND_EVAPORATING_DISH);
            if (tagCompound.hasKey(BIND_EVAPORATING_DISH)) {
                NBTTagCompound nbtDish = tagCompound.getCompoundTag(BIND_EVAPORATING_DISH);
                // IItemHandler items = tss.getItemHandler(SideHandlerType.ALL);
                // ECUtils.item.readFromNBT(items, BIND_EVAPORATING_DISH_ITEMS, nbtDish);
                ITankHandler fluids = tss.getTanks(SideHandlerType.ALL);
                ECUtils.fluid.readFromNBT(fluids, BIND_EVAPORATING_DISH_FLUIDS, nbtDish);
                int process = nbtDish.getInteger(BIND_EVAPORATING_DISH_PROCESS);
                MachineRecipeCapture recipe = nbtDish.hasKey(BIND_EVAPORATING_DISH_RECIPE) ? MachineRecipeCapture.fromNbt(nbtDish.getCompoundTag(BIND_EVAPORATING_DISH_RECIPE)) : null;
                tss.initSubBlock(getKey(), TileEvaporatingDish.RECIPE, recipe, process,
                        ECUtils.fluid.toList(fluids).toArray(new FluidStack[0]),
                        // ECUtils.item.toList(items, new IntArraySet()).toArray(new ItemStack[0]));
                        new ItemStack[0]);
            }
        }
    }

    @Override
    public void dropModule(World worldIn, BlockPos pos) {
        ItemStack itemStack = new ItemStack(ElementtimesBlocks.crucible);
        NBTTagCompound nbt = itemStack.getOrCreateSubCompound(BIND_EVAPORATING_DISH);
        // collect
        NBTTagCompound nbtEvaporatingDish = new NBTTagCompound();
        TileSupportStand tss = (TileSupportStand) worldIn.getTileEntity(pos);
        assert tss != null;
        // ECUtils.item.writeToNBT(tss.getItemHandler(SideHandlerType.ALL), BIND_EVAPORATING_DISH_ITEMS, nbtEvaporatingDish);
        ECUtils.fluid.writeToNBT(tss.getTanks(SideHandlerType.ALL), BIND_EVAPORATING_DISH_FLUIDS, nbtEvaporatingDish);
        nbtEvaporatingDish.setInteger(BIND_EVAPORATING_DISH_PROCESS, tss.getEnergyProcessed());
        MachineRecipeCapture recipe = tss.getWorkingRecipe();
        if (recipe != null) {
            nbtEvaporatingDish.setTag(BIND_EVAPORATING_DISH_RECIPE, recipe.serializeNBT());
        }
        nbt.setTag(BIND_EVAPORATING_DISH, nbtEvaporatingDish);
        Block.spawnAsEntity(worldIn, pos, itemStack);
        ((TileSupportStand) Objects.requireNonNull(worldIn.getTileEntity(pos))).clear();
    }

    @Override
    public boolean onActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Nullable
    @Override
    public IGuiProvider getGuiProvider(World worldIn, BlockPos pos) {
        return new TileEvaporatingDish(worldIn, pos);
    }
}