package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.interfaces.tileentity.ITileFluidHandler;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.util.FluidUtil;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
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
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.templates.FluidHandlerItemStackSimple;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;
import java.util.Map;

/**
 * 可燃的瓶子？
 * @author luqin2007
 */
public class ItemBottleFuel extends Item {

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
            FluidStack fluidStack = FluidUtil.getFluid(bottle);
            if (te != null && te.hasCapability(capability, facing.getOpposite())) {
                IFluidHandler handler;
                if (te instanceof ITileFluidHandler) {
                    handler = ((ITileFluidHandler) te).getTanks(SideHandlerType.INPUT);
                } else {
                    handler = (IFluidHandler) te.getCapability(capability, facing.getOpposite());
                }
                FluidStack resource = new FluidStack(fluidStack, 1000);
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

    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            Fluid fluid = FluidUtil.getFluidNotNull(stack).getFluid();
            if (fluid != null) {
                String name = fluid.getLocalizedName(new FluidStack(fluid, 1000));
                if (name == null) {
                    name = "???";
                }
                return net.minecraft.client.resources.I18n.format(stack.getUnlocalizedName() + ".name", name);
            }
        }
        return "???";
    }

    public static ItemStack createByFluid(Fluid fluid) {
        return createByFluid(new FluidStack(fluid, Fluid.BUCKET_VOLUME));
    }

    public static ItemStack createByFluid(FluidStack fluid) {
        Item bottle = ElementtimesItems.bottle;
        ItemStack itemStack = new ItemStack(bottle);
        FluidHandlerItemStackSimple capability = (FluidHandlerItemStackSimple) itemStack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
        capability.fill(fluid, true);
        return itemStack;
    }

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FluidHandlerItemStackSimple.SwapEmpty(stack, new ItemStack(Items.GLASS_BOTTLE),  Fluid.BUCKET_VOLUME);
    }
}
