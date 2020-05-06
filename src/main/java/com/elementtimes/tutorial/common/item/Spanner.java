package com.elementtimes.tutorial.common.item;

import com.elementtimes.elementcore.api.template.block.interfaces.IDismantleBlock;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileEnergyHandler;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileFluidHandler;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileItemHandler;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * 扳手
 * @author KSGFK
 */
public class Spanner extends Item {

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        if (!worldIn.isRemote && playerIn.isSneaking()) {
            ItemStack heldItem = playerIn.getHeldItem(handIn);
            setWorkType(playerIn, heldItem, getWorkType(heldItem).next());
            return new ActionResult<>(EnumActionResult.SUCCESS, heldItem);
        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            switch (getWorkType(stack)) {
                case Dismantle: return dismantleBlock(worldIn, pos);
                case Energy: return cycleEnergyType(player, worldIn, pos, facing);
                case Fluid: return cycleFluidType(player, worldIn, pos, facing);
                case Item: return cycleItemType(player, worldIn, pos, facing);
                default: return EnumActionResult.PASS;
            }
        }
        return EnumActionResult.PASS;
    }

    public Type getWorkType(ItemStack stack) {
        int thisType;
        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();
            assert compound != null;
            thisType = compound.getInteger("et.type");
        } else {
            thisType = 0;
            NBTTagCompound compound = new NBTTagCompound();
            compound.setInteger("et.type", thisType);
            stack.setTagCompound(compound);
        }
        return Type.valueOf(thisType);
    }

    public void setWorkType(EntityPlayer player, ItemStack stack, Type type) {
        NBTTagCompound compound;
        if (stack.hasTagCompound()) {
            compound = stack.getTagCompound();
        } else {
            compound = new NBTTagCompound();
            stack.setTagCompound(compound);
        }
        ITextComponent msg;
        switch (type) {
            case Item: msg = new TextComponentTranslation("chat.elementtimes.spanner.type.item"); break;
            case Fluid: msg = new TextComponentTranslation("chat.elementtimes.spanner.type.fluid"); break;
            case Energy: msg = new TextComponentTranslation("chat.elementtimes.spanner.type.energy"); break;
            default: msg = new TextComponentTranslation("chat.elementtimes.spanner.type.dismantle"); break;
        }
        player.sendMessage(msg);
        compound.setInteger("et.type", type.type);
    }

    public EnumActionResult dismantleBlock(World world, BlockPos pos) {
        IBlockState blockState = world.getBlockState(pos);
        Block block = blockState.getBlock();
        if (block instanceof IDismantleBlock) {
            ((IDismantleBlock) block).dismantleBlock(world, pos);
            return EnumActionResult.SUCCESS;
        }
        return EnumActionResult.PASS;
    }

    public EnumActionResult cycleItemType(EntityPlayer player, World world, BlockPos pos, EnumFacing facing) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof ITileItemHandler) {
            ITileItemHandler handler = (ITileItemHandler) te;
            SideHandlerType type = handler.getItemType(facing);
            SideHandlerType next = getNextTypeAndChat(player, type);
            handler.setItemType(facing, next);
        }
        return EnumActionResult.PASS;
    }

    public EnumActionResult cycleFluidType(EntityPlayer player, World world, BlockPos pos, EnumFacing facing) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof ITileFluidHandler) {
            ITileFluidHandler handler = (ITileFluidHandler) te;
            SideHandlerType type = handler.getTankType(facing);
            SideHandlerType next = getNextTypeAndChat(player, type);
            handler.setTankType(facing, next);
        }
        return EnumActionResult.PASS;
    }

    public EnumActionResult cycleEnergyType(EntityPlayer player, World world, BlockPos pos, EnumFacing facing) {
        TileEntity te = world.getTileEntity(pos);
        if (te instanceof ITileEnergyHandler) {
            ITileEnergyHandler handler = (ITileEnergyHandler) te;
            SideHandlerType type = handler.getEnergyType(facing);
            SideHandlerType next = getNextTypeAndChat(player, type);
            handler.setEnergyType(facing, next);
        }
        return EnumActionResult.PASS;
    }

    protected SideHandlerType getNextTypeAndChat(EntityPlayer player, SideHandlerType type) {
        SideHandlerType next;
        ITextComponent msg;
        switch (type) {
            case INPUT:
                next = SideHandlerType.OUTPUT;
                msg = new TextComponentTranslation("chat.elementtimes.spanner.io.out");
                break;
            case OUTPUT:
                next = SideHandlerType.ALL;
                msg = new TextComponentTranslation("chat.elementtimes.spanner.io.all");
                break;
            case ALL:
                next = SideHandlerType.NONE;
                msg = new TextComponentTranslation("chat.elementtimes.spanner.io.none");
                break;
            case NONE:
                next = SideHandlerType.IN_OUT;
                msg = new TextComponentTranslation("chat.elementtimes.spanner.io.io");
                break;
            case IN_OUT:
                next = SideHandlerType.READONLY;
                msg = new TextComponentTranslation("chat.elementtimes.spanner.io.readonly");
                break;
            default:
                next = SideHandlerType.INPUT;
                msg = new TextComponentTranslation("chat.elementtimes.spanner.io.in");
                break;
        }
        player.sendMessage(msg);
        return next;
    }

    enum Type {

        /**
         * 拆卸
         */
        Dismantle(0),
        /**
         * 物品 io
         */
        Item(1),
        /**
         * 流体 io
         */
        Fluid(2),
        /**
         * 能量 io
         */
        Energy(3);

        public final int type;

        Type(int type) {
            this.type = type;
        }

        public Type next() {
            switch (this) {
                case Item: return Fluid;
                case Fluid: return Energy;
                case Energy: return Dismantle;
                default: return Item;
            }
        }

        public static Type valueOf(int type) {
            switch (type) {
                case 1: return Item;
                case 2: return Fluid;
                case 3: return Energy;
                default: return Dismantle;
            }
        }
    }
}
