package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

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
    public int getItemBurnTime(ItemStack itemStack) {
        NBTTagCompound fluidNBT = getFluidNBT(itemStack);
        for (String name : fluidNBT.getKeySet()) {
            if (name.equals(ElementtimesFluids.steam.getName())) {
                return 1600;
            }
        }
        return 0;
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
