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
import com.elementtimes.tutorial.common.tileentity.TileCrucible;
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
 * 坩埚
 * @author luqin2007
 */
public class Crucible extends BlockAABB implements ITileEntityProvider, ISupportStandModule, IDismantleBlock {

    public static final String BIND_CRUCIBLE = "_nbt_crucible_";
    // private static final String BIND_CRUCIBLE_FLUIDS = "_nbt_crucible_f_";
    private static final String BIND_CRUCIBLE_ITEMS = "_nbt_crucible_i_";
    private static final String BIND_CRUCIBLE_RECIPE = "_nbt_crucible_r_";
    private static final String BIND_CRUCIBLE_PROCESS = "_nbt_crucible_p_";

    public Crucible() {
        super(new AxisAlignedBB(0, 0, 0, 1, 1, 1));
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileCrucible();
    }

    @Nonnull
    @Override
    public ItemStack getDismantleItem(World world, BlockPos pos) {
        ItemStack crucible = new ItemStack(this);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileCrucible) {
            NBTTagCompound nbt = ((TileCrucible) te).nbt;
            if (!nbt.hasNoTags()) {
                crucible.getOrCreateSubCompound(BIND_CRUCIBLE).setTag(BIND_CRUCIBLE, nbt);
            }
        }
        return crucible;
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileCrucible tc = (TileCrucible) worldIn.getTileEntity(pos);
        NBTTagCompound nbtCrucible = stack.getOrCreateSubCompound(BIND_CRUCIBLE);
        if (tc != null && nbtCrucible.hasKey(BIND_CRUCIBLE)) {
            tc.nbt = nbtCrucible.getCompoundTag(BIND_CRUCIBLE);
            tc.markDirty();
        }
    }

    @Nonnull
    @Override
    public ItemStack getModelItem() {
        return new ItemStack(this);
    }

    @Nonnull
    @Override
    public String getKey() {
        return BIND_CRUCIBLE;
    }

    @Nullable
    @Override
    public ITileTESR.RenderObject createRender() {
        return new ITileTESR.RenderObject(getModelItem()).translate(.5, .375, .5).scale(3, 3, 3);
    }

    @Override
    public void addModule(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileSupportStand) {
            TileSupportStand tss = (TileSupportStand) te;
            tss.renderModule(this);
            ItemStack heldItem = playerIn.getHeldItem(hand);
            NBTTagCompound tagCompound = heldItem.getOrCreateSubCompound(BIND_CRUCIBLE);
            if (tagCompound.hasKey(BIND_CRUCIBLE)) {
                IItemHandler items = tss.getItemHandler(SideHandlerType.ALL);
                ITankHandler fluids = tss.getTanks(SideHandlerType.ALL);
                NBTTagCompound nbtCrucible = tagCompound.getCompoundTag(BIND_CRUCIBLE);
                ECUtils.item.readFromNBT(items, BIND_CRUCIBLE_ITEMS, nbtCrucible);
                // ECUtils.fluid.readFromNBT(fluids, BIND_CRUCIBLE_FLUIDS, nbtCrucible);
                MachineRecipeCapture recipe = nbtCrucible.hasKey(BIND_CRUCIBLE_RECIPE) ? MachineRecipeCapture.fromNbt(nbtCrucible.getCompoundTag(BIND_CRUCIBLE_RECIPE)) : null;
                int process = nbtCrucible.getInteger(BIND_CRUCIBLE_PROCESS);
                tss.initSubBlock(getKey(), TileCrucible.RECIPE, recipe, process,
                        // ECUtils.fluid.toList(fluids).toArray(new FluidStack[0]),
                        new FluidStack[0],
                        ECUtils.item.toList(items, new IntArraySet()).toArray(new ItemStack[0]));
            }
            heldItem.shrink(1);
        }
    }

    @Override
    public void dropModule(World worldIn, BlockPos pos) {
        ItemStack itemStack = new ItemStack(ElementtimesBlocks.crucible);
        NBTTagCompound nbt = itemStack.getOrCreateSubCompound(BIND_CRUCIBLE);
        // collect
        NBTTagCompound nbtCrucible = new NBTTagCompound();
        TileSupportStand tss = (TileSupportStand) worldIn.getTileEntity(pos);
        assert tss != null;
        ECUtils.item.writeToNBT(tss.getItemHandler(SideHandlerType.ALL), BIND_CRUCIBLE_ITEMS, nbtCrucible);
        // ECUtils.fluid.writeToNBT(tss.getTanks(SideHandlerType.ALL), BIND_CRUCIBLE_FLUIDS, nbtCrucible);
        nbtCrucible.setInteger(BIND_CRUCIBLE_PROCESS, tss.getEnergyProcessed());
        MachineRecipeCapture recipe = tss.getWorkingRecipe();
        if (recipe != null) {
            nbtCrucible.setTag(BIND_CRUCIBLE_RECIPE, recipe.serializeNBT());
        }
        nbt.setTag(BIND_CRUCIBLE, nbtCrucible);
        Block.spawnAsEntity(worldIn, pos, itemStack);
        ((TileSupportStand) Objects.requireNonNull(worldIn.getTileEntity(pos))).clear();
    }

    @Override
    public boolean onActivated(World worldIn, BlockPos pos, IBlockState state,
                               EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                               float hitX, float hitY, float hitZ) {
        return false;
    }

    @Nullable
    @Override
    public IGuiProvider getGuiProvider(World worldIn, BlockPos pos) {
        return new TileCrucible(worldIn, pos);
    }
}