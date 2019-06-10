package com.elementtimes.tutorial.common.creativetabs;

import net.minecraft.creativetab.CreativeTabs;

/**
 * 所有可用的 CreateTabs
 * 用于注解注册
 *
 * @author luqin2007
 */
public enum ModCreativeTabs {
    /**
     * 主标签
     */
    Main(new ElementTimesTabs.Main()),

    /**
     * 元素时代-矿物
     */
    Ore(new ElementTimesTabs.Ore()),

    /**
     * 元素时代-化学
     */
    Chemical(new ElementTimesTabs.Chemical()),

    /**
     * 元素时代-工业
     */
    Industry(new ElementTimesTabs.Industry()),

    /**
     * 不显示
     */
    None(null);

    ModCreativeTabs(CreativeTabs tab) {
        this.tab = tab;
    }

    public CreativeTabs tab;
}