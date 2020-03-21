package com.elementtimes.tutorial.config;

import net.minecraftforge.common.config.Config;

public class Rebuild {

    @Config.Comment("设置重构机最大电容量")
    @Config.Name("capacity")
    public int capacity = 10000000;

    @Config.Comment("设置重构机每Tick消耗电量最大值")
    @Config.Name("extract")
    public int extract = 2000;

    @Config.Comment("设置重构机最大输入")
    @Config.Name("input")
    public int input = 5000;
}
