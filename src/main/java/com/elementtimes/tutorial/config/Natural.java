package com.elementtimes.tutorial.config;

import net.minecraftforge.common.config.Config;

public class Natural {

    @Config.Comment("设置橡胶树生成时,自带橡胶的概率")
    @Config.Name("probabilityRubber")
    @Config.RangeInt(min = 0, max = 100)
    @Config.RequiresWorldRestart
    public int probabilityRubber = 50;
}
