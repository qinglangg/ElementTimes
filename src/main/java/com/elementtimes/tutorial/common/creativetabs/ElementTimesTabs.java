package com.elementtimes.tutorial.common.creativetabs;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 所有的 CreateTabs 都被我集中到这里了 便于管理
 *
 * @author luqin2007
 */
public class ElementTimesTabs {

    /**
     * 元素时代
     */
    public static class Main extends CreativeTabs {
        public Main() { super(ElementTimes.MODID + ".Elementtimes"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.fiveElements);
        }
    }

    /**
     * 元素时代-矿物
     */
    public static class Ore extends CreativeTabs {
        public Ore() { super(ElementTimes.MODID + ".Elementtimesore"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.bigHammer);
        }
    }

    /**
     * 元素时代-化学
     */
    public static class Chemical extends CreativeTabs {
        public Chemical() { super(ElementTimes.MODID + ".Elementtimeschemicalindustry"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.bottle);
        }

        @Override
        @SideOnly(Side.CLIENT)
        public void displayAllRelevantItems(NonNullList<ItemStack> itemStacks) {
            super.displayAllRelevantItems(itemStacks);
            for (Fluid fluid : FluidRegistry.getBucketFluids()) {
                if (fluid.getName().startsWith("elementtimes.")) {
                    itemStacks.add(FluidUtil.getFilledBucket(new FluidStack(fluid, Fluid.BUCKET_VOLUME)));
                }
            }
        }
    }

    /**
     * 元素时代-工业
     */
    public static class Industry extends CreativeTabs {
        public Industry() { super(ElementTimes.MODID + ".Industry"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesBlocks.elementGenerator);
        }
    }

    /**
     * 元素时代-农业
     */
    public static class Agriculture extends CreativeTabs {
        public Agriculture() { super(ElementTimes.MODID + ".Agriculture"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.corn);
        }
    }
}
