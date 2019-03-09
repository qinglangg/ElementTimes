package com.elementtimes.tutorial.config;

import com.elementtimes.tutorial.Elementtimes;
import net.minecraftforge.common.config.Config;

/**
 * 配置文件
 *
 * @author KSGFK create in 2019/2/17
 */
@Config(modid = Elementtimes.MODID)
public class ElementtimesConfig {
    @Config.Name("General")
    @Config.LangKey("oceanheartr.config.general")
    public static final General general = new General();

    public static final class General {
        @Config.Comment("设置发电机最大容量")
        @Config.Name("generaterMaxEnergy")
        public int generaterMaxEnergy = 10000000;

        @Config.Comment("设置发电机最大输出")
        @Config.Name("generaterMaxExtract")
        public int generaterMaxExtract = 10000;

        @Config.Comment("设置发电机最大输入(发电速度等于最大输入)")
        @Config.Name("generaterMaxReceive")
        public int generaterMaxReceive = 10000;

        @Config.Comment("设置火元素发电量")
        @Config.Name("generaterFireGen")
        public int generaterFireGen = 200000;
        @Config.Comment("设置水元素发电量")
        @Config.Name("generaterWaterGen")
        public int generaterWaterGen = 200000;
        @Config.Comment("设置木元素发电量")
        @Config.Name("generaterWoodGen")
        public int generaterWoodGen = 200000;
        @Config.Comment("设置土元素发电量")
        @Config.Name("generaterSoilGen")
        public int generaterSoilGen = 150000;
        @Config.Comment("设置金元素发电量")
        @Config.Name("generaterGoldGen")
        public int generaterGoldGen = 250000;
        @Config.Comment("设置五行元素发电量")
        @Config.Name("generaterFive")
        public int generaterFive = 1000000;
        @Config.Comment("设置影元素发电量")
        @Config.Name("generaterEnd")
        public int generaterEnd = 500000;
    }
}
