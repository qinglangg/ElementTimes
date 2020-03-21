package com.elementtimes.tutorial.config;

import net.minecraftforge.common.config.Config;

public class Pulverize {

    @Config.Comment("设置打粉机机最大电容量")
    @Config.Name("capacity")
    public int capacity = 320000;

    @Config.Comment("设置打粉机每Tick消耗电量(相当于打粉速度")
    @Config.Name("extract")
    public int extract = 40;

    @Config.Comment("设置打粉机最大输入")
    @Config.Name("input")
    public int input = 100000;

    @Config.Comment("设置打粉机每个矿物耗电量")
    @Config.Name("energy")
    public int energy = 8000;

    @Config.Comment("设置打粉机每个矿物产粉量")
    @Config.Name("count")
    @Config.RangeInt(min = 0, max = 64)
    public int count = 5;
}
