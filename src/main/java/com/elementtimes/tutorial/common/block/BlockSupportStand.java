package com.elementtimes.tutorial.common.block;

import com.elementtimes.tutorial.common.block.base.BlockTileBase;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import com.elementtimes.tutorial.interfaces.tileentity.ITESRSupport;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author KSGFK create in 2019/6/12
 */
public class BlockSupportStand extends BlockTileBase<TileSupportStand> {
    private AxisAlignedBB aabb = new AxisAlignedBB(0.1D, 0D, 0.1D, 0.9D, 0.88D, 0.9D);

    public BlockSupportStand() {
        super(7, TileSupportStand.class, false);
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        //if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            if (!(te instanceof ITESRSupport)) {
                return false;
            }

            ITESRSupport s = (ITESRSupport) te;

            ItemStack held = playerIn.getHeldItem(hand);
            Item heldItem = held.getItem();

            Item al = Item.getItemFromBlock(ElementtimesBlocks.alcoholLamp);
            Item ed = Item.getItemFromBlock(ElementtimesBlocks.evaporatingDish);

            //ElementTimes.getLogger().info("{},{}", held, heldItem == al);

            if (heldItem == al) {
                s.addRenderItem(TileSupportStand.CanPutInItem.AlcoholLamp);
            } else if (heldItem == ed) {
                s.addRenderItem(TileSupportStand.CanPutInItem.EvaporatingDish);
            }
        //}

        return true;
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
