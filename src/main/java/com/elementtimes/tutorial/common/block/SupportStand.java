package com.elementtimes.tutorial.common.block;

import com.elementtimes.elementcore.api.template.block.interfaces.IDismantleBlock;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.block.interfaces.ISupportStandModule;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
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
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * (铁)架台，不一定是铁
 * TODO:像熔炉一样的光照
 *
 * @author KSGFK create in 2019/6/12
 */
public class SupportStand extends BlockAABB implements ITileEntityProvider, IDismantleBlock {

    public static final List<ISupportStandModule> MODULES = new LinkedList<>();

    public static void registerModule(ISupportStandModule module) {
        if (!MODULES.contains(module)) {
            MODULES.add(module);
        }
    }

    public SupportStand() {
        super(new AxisAlignedBB(0.1D, 0D, 0.1D, 0.9D, 0.88D, 0.9D));
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state,
                                    EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            boolean activated = false;
            ItemStack item = playerIn.getHeldItem(hand);
            Iterator<ISupportStandModule> iterator = MODULES.iterator();
            while (!activated && iterator.hasNext()) {
                ISupportStandModule module = iterator.next();
                if (module.isAdded(worldIn, pos)) {
                    activated = module.onActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
                } else if (module.getModelItem().isItemEqual(item)) {
                    module.addModule(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
                    activated = true;
                }
            }
            if (!activated) {
                TileSupportStand s = (TileSupportStand) worldIn.getTileEntity(pos);
                int guiId = s == null ? -1 : s.getGuiId();
                playerIn.openGui(ElementTimes.instance, guiId, worldIn, pos.getX(), pos.getY(), pos.getZ());
            }
        }
        return true;
    }

    // 方块及掉落
    @Override
    public boolean dismantleBlock(World world, BlockPos pos) {
        for (ISupportStandModule module : MODULES) {
            if (module.isAdded(world, pos)) {
                module.dropModule(world, pos);
                TileSupportStand s = (TileSupportStand) world.getTileEntity(pos);
                assert s != null;
                s.setRender(module.getKey(), false, pos);
                return true;
            }
        }
        return false;
    }

    @Override
    public int getLightValue(IBlockState state, IBlockAccess world, BlockPos pos) {
        if (world instanceof World) {
            return ((ISupportStandModule) ElementtimesBlocks.alcoholLamp).isAdded((World) world, pos) ? 10 : 0;
        }
        return super.getLightValue(state, world, pos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        for (ISupportStandModule module : MODULES) {
            if (module.isAdded(worldIn, pos)) {
                module.randomDisplayTickClient(stateIn, worldIn, pos, rand);
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
