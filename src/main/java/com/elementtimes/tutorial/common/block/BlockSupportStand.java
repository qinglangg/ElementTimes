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
        if (!worldIn.isRemote) {
            return true;
        }

        TileEntity te = worldIn.getTileEntity(pos);
        if (!(te instanceof TileSupportStand)) {
            return false;
        }

        TileSupportStand s = (TileSupportStand) te;

        ItemStack held = playerIn.getHeldItem(hand);
        Item heldItem = held.getItem();

        Item al = Item.getItemFromBlock(ElementtimesBlocks.alcoholLamp);
        Item ed = Item.getItemFromBlock(ElementtimesBlocks.evaporatingDish);

        if (heldItem == al) {//手里是酒精灯
            s.setRender(0, true);
            if (!playerIn.isCreative()) {
                held.setCount(held.getCount() - 1);
            }
            return true;
        }
        if (heldItem == ed) {//手里是蒸发皿
            s.setRender(1, true);
            if (!playerIn.isCreative()) {
                held.setCount(held.getCount() - 1);
            }
            return true;
        }
        if (heldItem == Items.FLINT_AND_STEEL) {//手里是打火石
            if (s.isRender(0)) {
                //s.setWorking(true);
                //s.setPause(false);
                s.isFire = true;
                //held.onItemUse(playerIn, worldIn, pos, hand, facing, hitX, hitY, hitZ);//emm直接调用这个会让周围着火...我想让打火石使用后扣耐久，但不引燃周围
            }
            return true;
        }

        /*
        FluidStack fluid = FluidUtil.getFluid(held);
        if (fluid == null) {
            return false;
        }
         */

        //TODO:放流体未完成
        //NULL!!!!!!!!!!!!!!!N U L L  P O I N T E R!!!!
        /*
        SideHandlerType side = s.getTankType(facing);
        TankHandler slot = s.getTanksMap().get(side);
        int re = slot.fill(0, fluid, false);
        if (re == 0) {
            ElementTimes.getLogger().info("放入流体结果:{}", false);
        } else {
            slot.fill(0, fluid, true);
        }
        //FluidActionResult result = net.minecraftforge.fluids.FluidUtil.tryFillContainer(held, slot, 16000, playerIn, true);
         */

        if (!s.isFire) {//酒精灯没点燃
            return true;
        }
        if (!s.isRender(0)) {//没酒精灯
            return true;
        }
        if (held.isEmpty()) {//空手灭火（滑稽
            Random r = new Random();
            if (r.nextInt(10) > 5) {
                if (!playerIn.isCreative()) {
                    playerIn.setFire(3);
                }
            }
            //s.setWorking(false);
            s.isFire = false;
            return true;
        }
        if (heldItem == Item.getItemFromBlock(Blocks.SAND)) {//沙子灭火
            //s.setPause(true);
            s.isFire = false;
            return true;
        }
/*
        if (!playerIn.isSneaking()) {//接下来的逻辑需要shift+右键
            return true;
        }
 */

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
        if (!(te instanceof TileSupportStand)) {
            return;
        }

        TileSupportStand s = (TileSupportStand) te;
        //ElementTimes.getLogger().info(s.isWorking());
        if (s.isFire) {
            double d0 = pos.getX();
            double d1 = pos.getY();
            double d2 = pos.getZ();
            worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.5D, d1 + 0.4D, d2 + 0.5D, 0.0D, 0D, 0.0D);
            //this.setLightLevel(0.75F);
        } //else {
        //this.setLightLevel(0);
        //}
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
