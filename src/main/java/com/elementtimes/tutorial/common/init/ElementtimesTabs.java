package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.ModTab;
import com.elementtimes.elementcore.api.annotation.part.Method;
import com.elementtimes.elementcore.api.annotation.tools.ModTabEditor;
import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.elementcore.api.template.tabs.CreativeTabDynamic;
import com.elementtimes.elementcore.api.template.tabs.CreativeTabStatic;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.plugin.GuideBook;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * @author luqin2007 create in 2019/2/17
 */
public class ElementtimesTabs {

    public static final String MAIN = "main";
    public static final String AGRICULTURE = "agriculture";
    public static final String CHEMICAL = "chemical";
    public static final String INDUSTRY = "industry";
    public static final String ORE = "ore";

    @ModTab(MAIN)
    public static CreativeTabs Main = new CreativeTabDynamic(ElementTimes.MODID + ".Elementtimes", 20,
            ElementtimesItems.fireElement, ElementtimesItems.waterElement, ElementtimesItems.endElement,
            ElementtimesItems.photoElement, ElementtimesItems.woodElement, ElementtimesItems.goldElement,
            ElementtimesItems.soilElement, ElementtimesItems.fiveElements) {

        @Override
        @SideOnly(Side.CLIENT)
        public void displayAllRelevantItems(@Nonnull NonNullList<ItemStack> itemStacks) {
            super.displayAllRelevantItems(itemStacks);
            itemStacks.addAll(GuideBook.getAllGuideBook());
        }
    };

    @ModTab(AGRICULTURE)
    public static CreativeTabs Agriculture = new CreativeTabDynamic(ElementTimes.MODID + ".Agriculture", 20,
            ElementtimesItems.corn, ElementtimesItems.bakedCorn, ElementtimesItems.puremeat);

    @ModTab(CHEMICAL)
    public static CreativeTabs Chemical = new CreativeTabDynamic(ElementTimes.MODID + ".Elementtimeschemicalindustry", 20,
            ElementtimesItems.SiC, ElementtimesItems.Mg, ElementtimesItems.Al, ElementtimesItems.AgBr,
            ElementtimesItems.AgCl, ElementtimesItems.AgI, ElementtimesItems.Silicon, ElementtimesItems.stoneIngot,
            ElementtimesItems.Al2O3_Na3AlF6, ElementtimesItems.CaSo4, ElementtimesItems.Na3AlF6,
            ElementtimesItems.calciumCarbonate, ElementtimesItems.calciumacetylide, ElementtimesItems.calciumOxide,
            ElementtimesItems.concrete, ElementtimesItems.salt) {
        @Override
        public void displayAllRelevantItems(NonNullList<ItemStack> list) {
            FluidRegistry.getBucketFluids().stream()
                    .filter(f -> f.getName().startsWith("elementtimes"))
                    .map(f -> FluidUtil.getFilledBucket(new FluidStack(f, Fluid.BUCKET_VOLUME)))
                    .forEach(list::add);
            super.displayAllRelevantItems(list);
        }
    };

    @ModTab(INDUSTRY)
    public static CreativeTabs Industry = new CreativeTabStatic(ElementTimes.MODID + ".Industry", ElementtimesItems.spanner);

    @ModTab(ORE)
    public static CreativeTabs Ore = new CreativeTabDynamic(ElementTimes.MODID + ".Elementtimesore", 20,
            ElementtimesBlocks.oreSalt, ElementtimesBlocks.copperOre, ElementtimesBlocks.platinumOre,
            ElementtimesBlocks.sulfurOre, ElementtimesBlocks.uraniumOre, ElementtimesBlocks.leadOre, ElementtimesBlocks.tinOre);

    @ModTabEditor(@Method(value = ElementtimesTabs.class, name = "editMisc"))
    public static CreativeTabs misc = CreativeTabs.MISC;

    public static void editMisc(NonNullList<ItemStack> items) {
        items.removeIf(itemStack -> {
            Item item = itemStack.getItem();
            if (item == ForgeModContainer.getInstance().universalBucket) {
                FluidStack f = ECUtils.fluid.getFluid(itemStack);
                return f.getFluid().getName().startsWith("elementtimes.");
            }
            return false;
        });
    }
}
