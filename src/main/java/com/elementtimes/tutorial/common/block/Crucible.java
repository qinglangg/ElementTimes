package com.elementtimes.tutorial.common.block;

import com.elementtimes.elementcore.api.template.block.interfaces.IDismantleBlock;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileTESR;
import com.elementtimes.tutorial.interfaces.ISupportStandModule;
import com.elementtimes.tutorial.common.tileentity.TileCrucible;
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
    public boolean onActivated(World worldIn, BlockPos pos, IBlockState state,
                               EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                               float hitX, float hitY, float hitZ) {
        return false;
    }

    @Nonnull
    @Override
    public Supplier getActiveObject(World worldIn, BlockPos pos) {
        return () -> new TileCrucible(worldIn, pos);
    }

    @Override
    public boolean isActive(World worldIn, BlockPos pos) {
        return true;
    }
}