package com.elementtimes.tutorial.common.creativetabs;

import net.minecraft.creativetab.CreativeTabs;

public enum ModCreativeTabs {
    Main(new ElementTimesTabs.Main()),
    Ore(new ElementTimesTabs.Ore()),
    Chemical(new ElementTimesTabs.Chemical()),
    Industry(new ElementTimesTabs.Industry()),
    None(null);

    ModCreativeTabs(CreativeTabs tab) {
        this.tab = tab;
    }

    public CreativeTabs tab;
}