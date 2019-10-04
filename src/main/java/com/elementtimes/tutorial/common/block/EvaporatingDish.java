package com.elementtimes.tutorial.common.block;

import com.elementtimes.elementcore.api.template.block.interfaces.IDismantleBlock;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileTESR;
import com.elementtimes.tutorial.common.block.interfaces.ISupportStandModule;
import com.elementtimes.tutorial.common.tileentity.TileEvaporatingDish;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.Supplier;

/**
 * 蒸发皿
 * @author luqin2007
 */
public class EvaporatingDish extends BlockAABB implements ITileEntityProvider, IDismantleBlock, ISupportStandModule {

    public static final String BIND_EVAPORATING_DISH = "_nbt_dish_";

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
    public Supplier getActiveObject(World worldIn, BlockPos pos) {
        return () -> new TileEvaporatingDish(worldIn, pos);
    }

    @Override
    public boolean isActive(World worldIn, BlockPos pos) {
        return true;
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
    public boolean onActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return false;
    }

}