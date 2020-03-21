package com.elementtimes.tutorial.config;

import net.minecraftforge.common.config.Config;

public class Forming {

    @Config.Comment("设置整形机最大电容量")
    @Config.Name("capacity")
    public int capacity = 32000;

    @Config.Comment("设置整形机每Tick消耗电量")
    @Config.Name("extract")
    public int extract = 20;

    @Config.Comment("设置整形机最大输入")
    @Config.Name("input")
    public int input = 200;
}
