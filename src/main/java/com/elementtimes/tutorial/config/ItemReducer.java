package com.elementtimes.tutorial.config;

import net.minecraftforge.common.config.Config;

public class ItemReducer {

    @Config.Comment("设置还原机最大电容量")
    @Config.Name("capacity")
    public int capacity = 32000;

    @Config.Comment("设置还原机每Tick消耗电量(速度)")
    @Config.Name("extract")
    public int extract = 40;

    @Config.Comment("设置还原机最大输入")
    @Config.Name("input")
    public int input = 40;

    @Config.Comment("设置还原机每个矿物耗电量")
    @Config.Name("energy")
    public int energy = 8000;
}
