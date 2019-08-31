package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.annotations.ModCreativeTabs;
import com.elementtimes.tutorial.ElementTimes;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ElementtimesTabs {

    public static final String MAIN = "main";
    public static final String AGRICULTURE = "agriculture";
    public static final String CHEMICAL = "chemical";
    public static final String INDUSTRY = "industry";
    public static final String ORE = "ore";

    @ModCreativeTabs(value = MAIN)
    public static CreativeTabs Main = new CreativeTabs(ElementTimes.MODID + ".Elementtimes") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.fiveElements);
        }
    };

    @ModCreativeTabs(value = AGRICULTURE)
    public static CreativeTabs Agriculture = new CreativeTabs(ElementTimes.MODID + ".Agriculture") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.corn);
        }
    };

    @ModCreativeTabs(value = CHEMICAL)
    public static CreativeTabs Chemical = new CreativeTabs(ElementTimes.MODID + ".Elementtimeschemicalindustry") {
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
    };

    @ModCreativeTabs(value = INDUSTRY)
    public static CreativeTabs Industry = new CreativeTabs(ElementTimes.MODID + ".Industry") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesBlocks.elementGenerator);
        }
    };

    @ModCreativeTabs(value = ORE)
    public static CreativeTabs Ore = new CreativeTabs(ElementTimes.MODID + ".Elementtimesore") {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.bigHammer);
        }
    };
}
