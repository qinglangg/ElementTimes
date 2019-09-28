package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.ModCreativeTabs;
import com.elementtimes.elementcore.api.template.tabs.CreativeTabDynamic;
import com.elementtimes.elementcore.api.template.tabs.CreativeTabStatic;
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

/**
 * @author luqin2007 create in 2019/2/17
 */
public class ElementtimesTabs {

    public static final String MAIN = "main";
    public static final String AGRICULTURE = "agriculture";
    public static final String CHEMICAL = "chemical";
    public static final String INDUSTRY = "industry";
    public static final String ORE = "ore";

    @ModCreativeTabs(value = MAIN)
    public static CreativeTabs Main = new CreativeTabDynamic(ElementTimes.MODID + ".Elementtimes", 20,
            ElementtimesItems.fireElement, ElementtimesItems.waterElement, ElementtimesItems.endElement,
            ElementtimesItems.photoElement, ElementtimesItems.woodElement, ElementtimesItems.goldElement,
            ElementtimesItems.soilElement, ElementtimesItems.fiveElements);

    @ModCreativeTabs(value = AGRICULTURE)
    public static CreativeTabs Agriculture = new CreativeTabDynamic(ElementTimes.MODID + ".Agriculture", 20,
            ElementtimesItems.corn, ElementtimesItems.bakedCorn, ElementtimesItems.puremeat);

    @ModCreativeTabs(value = CHEMICAL)
    public static CreativeTabs Chemical = new CreativeTabDynamic(ElementTimes.MODID + ".Elementtimeschemicalindustry",
            FluidRegistry.getBucketFluids().stream()
                    .filter(f -> f.getName().startsWith("elementtimes"))
                    .map(f -> FluidUtil.getFilledBucket(new FluidStack(f, Fluid.BUCKET_VOLUME)))
                    .toArray(ItemStack[]::new)) {
        @Override
        public void displayAllRelevantItems(NonNullList<ItemStack> list) {
            FluidRegistry.getBucketFluids().stream()
                    .filter(f -> f.getName().startsWith("elementtimes"))
                    .map(f -> FluidUtil.getFilledBucket(new FluidStack(f, Fluid.BUCKET_VOLUME)))
                    .forEach(list::add);
            super.displayAllRelevantItems(list);
        }
    };

    @ModCreativeTabs(value = INDUSTRY)
    public static CreativeTabs Industry = new CreativeTabStatic(ElementTimes.MODID + ".Industry", ElementtimesItems.spanner);

    @ModCreativeTabs(value = ORE)
    public static CreativeTabs Ore = new CreativeTabDynamic(ElementTimes.MODID + ".Elementtimesore", 20,
            ElementtimesBlocks.oreSalt, ElementtimesBlocks.copperOre, ElementtimesBlocks.platinumOre,
            ElementtimesBlocks.sulfurOre, ElementtimesBlocks.uraniumOre, ElementtimesBlocks.leadOre, ElementtimesBlocks.tinOre);
}
