package com.elementtimes.tutorial.config;

import net.minecraftforge.common.config.Config;

public class ElementGen {

    @Config.Comment("设置发电机最大容量")
    @Config.Name("cache")
    public int capacity = 100000000;

    @Config.Comment("设置发电机最大输出")
    @Config.Name("output")
    public int output = 1000000;

    @Config.Comment("设置发电机发电速度")
    @Config.Name("generate")
    public int generate = 100;

    @Config.Comment("设置火元素发电量")
    @Config.Name("fire")
    public int fire = 2000000;

    @Config.Comment("设置水元素发电量")
    @Config.Name("water")
    public int water = 90000000;

    @Config.Comment("设置木元素发电量")
    @Config.Name("wood")
    public int wood = 1500000;

    @Config.Comment("设置土元素发电量")
    @Config.Name("soil")
    public int soil = 1000000;

    @Config.Comment("设置金元素发电量")
    @Config.Name("gold")
    public int gold = 4000000;

    @Config.Comment("设置五行元素发电量")
    @Config.Name("five")
    public int five = 15000000;

    @Config.Comment("设置光元素发电量")
    @Config.Name("sun")
    public int sun = 15000000;

    @Config.Comment("设置影元素发电量")
    @Config.Name("end")
    public int end = 15000000;
}
