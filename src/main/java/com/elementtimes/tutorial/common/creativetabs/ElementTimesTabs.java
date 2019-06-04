package com.elementtimes.tutorial.common.creativetabs;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

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
        public Main() { super(Elementtimes.MODID + ".Elementtimes"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.fiveElements);
        }
    }

    /**
     * 元素时代-矿物
     */
    public static class Ore extends CreativeTabs {
        public Ore() { super(Elementtimes.MODID + ".Elementtimesore"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.bigHammer);
        }
    }

    /**
     * 元素时代-化学
     */
    public static class Chemical extends CreativeTabs {
        public Chemical() { super(Elementtimes.MODID + ".Elementtimeschemicalindustry"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.sodiumSulfiteSolution);
        }
    }

    /**
     * 元素时代-工业
     */
    public static class Industry extends CreativeTabs {
        public Industry() { super(Elementtimes.MODID + ".Industry"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesBlocks.elementGenerator);
        }
    }
}
