package com.elementtimes.tutorial.common.creativetabs;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class ElementTimesTabs {

    public static class Main extends CreativeTabs {
        public Main() { super("Elementtimes"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.fiveElements);
        }
    }

    public static class Ore extends CreativeTabs {
        public Ore() { super("Elementtimesore"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.bigHammer);
        }
    }

    public static class Chemical extends CreativeTabs {
        public Chemical() { super("Elementtimeschemicalindustry"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesItems.sodiumSulfiteSolution);
        }
    }

    public static class Industry extends CreativeTabs {
        public Industry() { super("Industry"); }
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ElementtimesBlocks.elementGenerator);
        }
    }
}
