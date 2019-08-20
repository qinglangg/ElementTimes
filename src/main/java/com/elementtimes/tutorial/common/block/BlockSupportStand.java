package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.block.base.BlockTileBase;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

/**
 * (铁)架台，不一定是铁
 * TODO:像熔炉一样的光照
 *
 * @author KSGFK create in 2019/6/12
 */
public class BlockSupportStand extends BlockTileBase<TileSupportStand> {
    private AxisAlignedBB aabb = new AxisAlignedBB(0.1D, 0D, 0.1D, 0.9D, 0.88D, 0.9D);

    public BlockSupportStand() {
        super(TileSupportStand.class);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (!(te instanceof TileSupportStand)) {
                return false;
            }
            TileSupportStand s = (TileSupportStand) te;
            ItemStack held = playerIn.getHeldItem(hand);
            Item heldItem = held.getItem();
            if (Block.getBlockFromItem(heldItem) == ElementtimesBlocks.alcoholLamp && !s.isRender(s.alcoholLamp)) {
                // 酒精灯
                s.setRender(s.alcoholLamp, true);
                if (!playerIn.isCreative()) {
                    held.setCount(held.getCount() - 1);
                }
            } else if (Block.getBlockFromItem(heldItem) == ElementtimesBlocks.evaporatingDish && !s.isRender(s.evaporatingDish)) {
                // 蒸发皿
                s.setRender(s.evaporatingDish, true);
                if (!playerIn.isCreative()) {
                    held.setCount(held.getCount() - 1);
                }
            } else if (heldItem == Items.FLINT_AND_STEEL && s.isRender(s.alcoholLamp) && !s.isFire()) {
                // 打火石
                if (s.isRender(s.alcoholLamp)) {
                    s.setFire(true);
                    held.damageItem(1, playerIn);
                }
            } else if (held.isEmpty()) {
                if (playerIn.isSneaking() && s.isFire()) {
                    //空手灭火（滑稽
                    Random r = new Random();
                    if (r.nextInt(10) > 5) {
                        if (!playerIn.isCreative()) {
                            playerIn.setFire(3);
                        }
                    }
                    s.setFire(false);
                } else {
                    playerIn.openGui(ElementTimes.instance, s.getGuiType().id(), worldIn, pos.getX(), pos.getY(), pos.getZ());
                }
            } else if (heldItem == Item.getItemFromBlock(Blocks.SAND)) {
                //沙子灭火
                s.setFire(false);
            }
            s.markDirty();
            s.markRenderClient();
        }
        return true;
    }

    @Override
    public void onBlockClicked(World worldIn, BlockPos pos, EntityPlayer playerIn) {
        super.onBlockClicked(worldIn, pos, playerIn);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof TileSupportStand) {
            TileSupportStand s = (TileSupportStand) te;
            if (s.isFire()) {
                double d0 = pos.getX();
                double d1 = pos.getY();
                double d2 = pos.getZ();
                worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.5D, d1 + 0.4D, d2 + 0.5D, 0.0D, 0D, 0.0D);
            }
        }
    }

    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return aabb;
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
}
