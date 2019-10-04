package com.elementtimes.tutorial.common.block;

import com.elementtimes.elementcore.api.template.block.interfaces.IDismantleBlock;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileTESR;
import com.elementtimes.tutorial.common.block.interfaces.ISupportStandModule;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.TileBeaker;
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
 * 烧杯
 * @author luqin2007
 */
public class Beaker extends BlockAABB implements ITileEntityProvider, ISupportStandModule, IDismantleBlock {

    public static final String BIND_BEAKER = "_nbt_beaker_";
    
    public Beaker() {
        super(new AxisAlignedBB(0.3125, 0, 0.3125, 0.6875, 0.4375, 0.6875));
    }

    @Nonnull
    @Override
    public String getKey() {
        return BIND_BEAKER;
    }

    @Nullable
    @Override
    public ITileTESR.RenderObject createRender() {
        return new ITileTESR.RenderObject(new ItemStack(ElementtimesBlocks.beaker)).translate(.5, -.13, .5).scale(3, 3, 3);
    }

    @Override
    public boolean onActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return false;
    }

    @Override
    public ItemStack getModelItem() {
        return new ItemStack(this);
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileBeaker();
    }

    @Nonnull
    @Override
    public Supplier getActiveObject(World worldIn, BlockPos pos) {
        return () -> new TileBeaker(worldIn, pos);
    }

    @Override
    public boolean isActive(World worldIn, BlockPos pos) {
        return true;
    }
}
