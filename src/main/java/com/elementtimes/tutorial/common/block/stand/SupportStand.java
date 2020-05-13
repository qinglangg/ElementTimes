package com.elementtimes.tutorial.common.block.stand;

import com.elementtimes.elementcore.api.template.block.interfaces.IDismantleBlock;
import com.elementtimes.tutorial.common.block.BlockAABB;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.stand.TileSupportStand;
import com.elementtimes.tutorial.common.tileentity.stand.module.ISupportStandModule;
import net.minecraft.block.Block;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Random;

/**
 * (铁)架台，不一定是铁
 * TODO:像熔炉一样的光照
 *
 * @author KSGFK create in 2019/6/12
 */
public class SupportStand extends BlockAABB implements ITileEntityProvider, IDismantleBlock {

    public SupportStand() {
        super(new AxisAlignedBB(0.1D, 0D, 0.1D, 0.9D, 0.88D, 0.9D));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
                                    EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileSupportStand s = (TileSupportStand) worldIn.getTileEntity(pos);
            assert s != null;
            ItemStack stack = playerIn.getHeldItem(hand);
            Block block = Block.getBlockFromItem(stack.getItem());
            if (block instanceof BaseModuleBlock) {
                if (((BaseModuleBlock) block).applyTo(worldIn, s, state, playerIn, hand, facing, hitX, hitY, hitZ)) {
                    return true;
                }
            }
            for (ISupportStandModule module : s.getModules()) {
                if (module != null) {
                    if (module.onBlockActivated(playerIn, hand, facing, hitX, hitY, hitZ)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    // 方块及掉落
    @Override
    public boolean dismantleBlock(World world, BlockPos pos) {
        if (!world.isRemote) {
            TileSupportStand s = (TileSupportStand) world.getTileEntity(pos);
            assert s != null;
            for (String key : new ArrayList<>(s.getModuleKeys())) {
                ItemStack stack = s.removeToItem(key);
                Block.spawnAsEntity(world, pos, stack);
            }
            Block.spawnAsEntity(world, pos, new ItemStack(ElementtimesBlocks.supportStand));
        }
        return false;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        int light = super.getLightValue(state, world, pos);
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof TileSupportStand) {
            for (ISupportStandModule module : ((TileSupportStand) te).getModules()) {
                light = module.getLight(light);
            }
        }
        return light;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileSupportStand) {
            for (ISupportStandModule module : ((TileSupportStand) te).getModules()) {
                module.randomDisplayTick(stateIn, worldIn, pos, rand);
            }
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public TileEntity createNewTileEntity(@Nonnull World worldIn, int meta) {
        return new TileSupportStand();
    }
}
