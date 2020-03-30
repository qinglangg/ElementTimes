package com.elementtimes.tutorial.config;

import net.minecraftforge.common.config.Config;

public class FluidGen {

    @Config.Comment("设置燃料总发电量与燃烧时间的比例")
    @Config.Name("multiple")
    public int multiple = 10;
}
