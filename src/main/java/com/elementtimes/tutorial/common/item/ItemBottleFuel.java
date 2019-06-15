package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.interfaces.tileentity.ITileFluidHandler;
import com.elementtimes.tutorial.other.SideHandlerType;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBucketMilk;
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
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.*;

import javax.annotation.Nonnull;
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

    @Nullable
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, @Nullable NBTTagCompound nbt) {
        return new FluidWrapper(stack);
    }

    private class FluidWrapper implements IFluidHandlerItem, ICapabilityProvider {
        @Nonnull
        protected ItemStack container;

        public FluidWrapper(@Nonnull ItemStack container) {
            this.container = container;
        }

        @Nonnull
        @Override
        public ItemStack getContainer() {
            return container;
        }

        public boolean canFillFluidType(FluidStack fluidStack) {
            Fluid fluid = fluidStack.getFluid();
            if (fluid == FluidRegistry.WATER || fluid == FluidRegistry.LAVA || fluid.getName().equals("milk")) {
                return true;
            }
            return FluidRegistry.isUniversalBucketEnabled() && FluidRegistry.hasBucket(fluid);
        }

        @Nullable
        public FluidStack getFluid() {
            Item item = container.getItem();
            if (item == Items.WATER_BUCKET) {
                return new FluidStack(FluidRegistry.WATER, Fluid.BUCKET_VOLUME);
            } else if (item == Items.LAVA_BUCKET) {
                return new FluidStack(FluidRegistry.LAVA, Fluid.BUCKET_VOLUME);
            } else if (item == Items.MILK_BUCKET) {
                return FluidRegistry.getFluidStack("milk", Fluid.BUCKET_VOLUME);
            } else if (item == ForgeModContainer.getInstance().universalBucket) {
                return ForgeModContainer.getInstance().universalBucket.getFluid(container);
            } else {
                return null;
            }
        }

        protected void setFluid(@Nullable FluidStack fluidStack) {
            if (fluidStack == null) {
                container = new ItemStack(Items.BUCKET);
            } else {
                container = FluidUtil.getFilledBucket(fluidStack);
            }
        }

        @Override
        public IFluidTankProperties[] getTankProperties() {
            return new FluidTankProperties[] { new FluidTankProperties(getFluid(), Fluid.BUCKET_VOLUME) };
        }

        @Override
        public int fill(FluidStack resource, boolean doFill) {
            if (container.getCount() != 1 || resource == null || resource.amount < Fluid.BUCKET_VOLUME || container.getItem() instanceof ItemBucketMilk || getFluid() != null || !canFillFluidType(resource)) {
                return 0;
            }

            if (doFill) {
                setFluid(resource);
            }

            return Fluid.BUCKET_VOLUME;
        }

        @Nullable
        @Override
        public FluidStack drain(FluidStack resource, boolean doDrain) {
            if (container.getCount() != 1 || resource == null || resource.amount < Fluid.BUCKET_VOLUME) {
                return null;
            }

            FluidStack fluidStack = getFluid();
            if (fluidStack != null && fluidStack.isFluidEqual(resource)) {
                if (doDrain) {
                    setFluid(null);
                }
                return fluidStack;
            }

            return null;
        }

        @Nullable
        @Override
        public FluidStack drain(int maxDrain, boolean doDrain) {
            if (container.getCount() != 1 || maxDrain < Fluid.BUCKET_VOLUME) {
                return null;
            }

            FluidStack fluidStack = getFluid();
            if (fluidStack != null) {
                if (doDrain) {
                    setFluid((FluidStack) null);
                }
                return fluidStack;
            }

            return null;
        }

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
            return capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY;
        }

        @Override
        @Nullable
        public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
            if (capability == CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY) {
                return CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY.cast(this);
            }
            return null;
        }
    }
}
