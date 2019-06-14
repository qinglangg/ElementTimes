package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.interfaces.tileentity.ITileFluidHandler;
import com.elementtimes.tutorial.other.SideHandlerType;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.*;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;

/**
 * 可燃的瓶子？
 * @author luqin2007
 */
public class ItemBottleFuel extends ItemGlassBottle {

    public ItemBottleFuel() {
        setContainerItem(Items.GLASS_BOTTLE);
        setMaxStackSize(1);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (isInCreativeTab(tab)) {
            FluidRegistry.getRegisteredFluids().entrySet().stream()
                    .filter(set -> set.getKey().startsWith(ElementTimes.MODID))
                    .map(Map.Entry::getValue)
                    .map(ItemBottleFuel::createByFluid)
                    .forEach(items::add);
        }
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            TileEntity te = worldIn.getTileEntity(pos);
            Capability capability = CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
            ItemStack bottle = player.getHeldItem(hand);
            Fluid fluid = ItemBottleFuel.getFluid(bottle);
            if (te != null && te.hasCapability(capability, facing.getOpposite())) {
                IFluidHandler handler;
                if (te instanceof ITileFluidHandler) {
                    handler = ((ITileFluidHandler) te).getTanks(SideHandlerType.INPUT);
                } else {
                    handler = (IFluidHandler) te.getCapability(capability, facing.getOpposite());
                }
                FluidStack resource = new FluidStack(fluid, 1000);
                assert handler != null;
                FluidStack drain = handler.drain(resource, false);
                if (drain != null && drain.containsFluid(resource)) {
                    handler.drain(resource, true);
                    player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
                }
            }
        }
        return EnumActionResult.PASS;
    }

    public static ItemStack createByFluid(Fluid fluid) {
        Item bottle = ElementtimesItems.bottle;
        ItemStack itemStack = new ItemStack(bottle);
        itemStack.setStackDisplayName(I18n.format(bottle.getUnlocalizedName() + ".name", I18n.format(fluid.getUnlocalizedName())));
        NBTTagCompound fNBT = getFluidNBT(itemStack);
        fNBT.setInteger(fluid.getName(), fluid.getColor());
        return itemStack;
    }

    public static NBTTagCompound getFluidNBT(ItemStack itemStack) {
       if (itemStack == null || itemStack.isEmpty() || !(itemStack.getItem() instanceof ItemGlassBottle)) {
           return new NBTTagCompound();
        } else {
           return itemStack.getOrCreateSubCompound("fluid");
       }
    }

    public static Fluid getFluid(ItemStack itemStack) {
        return getFluidNBT(itemStack).getKeySet().stream()
                .map(FluidRegistry::getFluid)
                .findFirst()
                .orElse(null);
    }
}
