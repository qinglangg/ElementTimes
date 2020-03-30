package com.elementtimes.tutorial.config;

import net.minecraftforge.common.config.Config;

public class Storage {

    @Config.Comment("能量容器最大容量 (RF)")
    @Config.Name("energy")
    public int energy = 100000;
}
