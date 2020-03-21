package com.elementtimes.tutorial.config;

import net.minecraftforge.common.config.Config;

public class FuelGen {

    @Config.Comment("设置火力发电机最大容量")
    @Config.Name("capacity")
    public int capacity = 100000;

    @Config.Comment("设置火力发电机最大输出")
    @Config.Name("output")
    public int output = 10000;

    @Config.Comment("设置火力发电机发电速度")
    @Config.Name("generate")
    public int generate = 1000;

    @Config.Comment("设置燃料总发电量与燃烧时间的比例")
    @Config.Name("multiple")
    public int multiple = 10;
}
