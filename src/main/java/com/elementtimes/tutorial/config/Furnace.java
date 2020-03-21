package com.elementtimes.tutorial.config;

import net.minecraftforge.common.config.Config;

public class Furnace {

    @Config.Comment("设置熔炉最大电容量")
    @Config.Name("capacity")
    public int capacity = 32000;

    @Config.Comment("设置熔炉每Tick消耗电量")
    @Config.Name("extract")
    public int extract = 20;

    @Config.Comment("设置熔炉最大输入")
    @Config.Name("input")
    public int input = 20;
}
