package com.elementtimes.tutorial.common.block.base;

import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * 拥有 on/off 两种状态的机器
 * @author luqin2007
 */
public class BaseClosableMachine<T extends BaseMachine> extends BlockTileBase<T> {

    public BaseClosableMachine(Class<T> entityClass, boolean addFullEnergyBlock) {
        super(entityClass, addFullEnergyBlock);

        setDefaultState(getDefaultState().withProperty(FACING, EnumFacing.NORTH).withProperty(IS_RUNNING, false));
    }

    public BaseClosableMachine(Class<T> entityClass) {
        this(entityClass, false);
    }

    public static IProperty<EnumFacing> FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static IProperty<Boolean> IS_RUNNING = PropertyBool.create("running");

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, IS_RUNNING);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        int facing = state.getValue(FACING).getHorizontalIndex() & 0b0011;
        int burning = state.getValue(IS_RUNNING) ? 0b0100 : 0b0000;
        return facing | burning;
    }

    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.getHorizontal(meta & 0b0011);
        boolean burning = (meta & 0b0100) == 0b0100;
        return super.getStateFromMeta(meta).withProperty(FACING, facing).withProperty(IS_RUNNING, burning);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        IBlockState s = worldIn.getBlockState(pos);
        IBlockState s2 = s.withProperty(FACING, placer.getHorizontalFacing());
        worldIn.setBlockState(pos, s2);
    }
}
