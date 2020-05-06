package com.elementtimes.tutorial.common.block.stand;

import com.elementtimes.tutorial.common.block.stand.module.ModuleAlcoholLamp;
import com.elementtimes.tutorial.common.tileentity.stand.TileAlcoholLamp;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static com.elementtimes.elementcore.api.template.block.Properties.IS_BURNING;

/**
 * 酒精灯
 * @author KSGFK create in 2019/6/12
 */
public class AlcoholLamp extends BaseModuleBlock<ModuleAlcoholLamp, TileAlcoholLamp> {

    public AlcoholLamp() {
        super(ModuleAlcoholLamp.KEY, new AxisAlignedBB(0.25D, 0.0D, 0.25D, 0.75D, 0.5D, 0.75D));
        this.setDefaultState(this.getDefaultState().withProperty(IS_BURNING, false));
    }

    @Nonnull
    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, IS_BURNING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return 0;
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        IBlockState actualState = super.getActualState(state, worldIn, pos);
        ModuleAlcoholLamp module = getModule(worldIn, pos);
        if (module != null) {
            return actualState.withProperty(IS_BURNING, module.isFire());
        }
        return actualState;
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ModuleAlcoholLamp module = getModule(worldIn, pos);
            if (module != null) {
                return module.onBlockActivated(playerIn, hand, facing, hitX, hitY, hitZ);
            }
        }
        return false;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileAlcoholLamp();
    }
}