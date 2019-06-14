package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.block.base.BlockTileBase;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
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
        super(7, TileSupportStand.class, false);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TileSupportStand)) {
            return true;
        }

        TileSupportStand s = (TileSupportStand) te;

        ItemStack held = playerIn.getHeldItem(hand);
        Item heldItem = held.getItem();

        Item al = Item.getItemFromBlock(ElementtimesBlocks.alcoholLamp);
        Item ed = Item.getItemFromBlock(ElementtimesBlocks.evaporatingDish);

        if (heldItem == al) {
            if (s.addRenderItem(TileSupportStand.CanPutInItem.AlcoholLamp)) {
                held.setCount(held.getCount() - 1);
            }
        } else if (heldItem == ed) {
            if (s.addRenderItem(TileSupportStand.CanPutInItem.EvaporatingDish)) {
                held.setCount(held.getCount() - 1);
            }
        }

        if (heldItem == Items.FLINT_AND_STEEL) {
            if (s.containsRenderItem(TileSupportStand.CanPutInItem.AlcoholLamp)) {
                s.setWorking(true);
                //held.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);//emm直接调用这个会让周围着火...我想让打火石使用后扣耐久，但不引燃周围
            }
        } else {
            if (!s.isWorking()) {
                return true;
            }
            if (!s.containsRenderItem(TileSupportStand.CanPutInItem.AlcoholLamp)) {
                return true;
            }
            if (held.isEmpty()) {
                Random r = new Random();
                if (r.nextInt(10) > 5) {
                    playerIn.setFire(3);
                }
                s.setWorking(false);
            } else if (heldItem == Item.getItemFromBlock(Blocks.SAND)) {
                s.setWorking(false);
            }
        }

        return true;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TileSupportStand)) {
            return;
        }

        TileSupportStand s = (TileSupportStand) te;
        if (s.isWorking()) {
            double d0 = pos.getX();
            double d1 = pos.getY();
            double d2 = pos.getZ();
            worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.5D, d1 + 0.4D, d2 + 0.5D, 0.0D, 0D, 0.0D);
            this.setLightLevel(0.75F);
        } else {
            this.setLightLevel(0);
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
