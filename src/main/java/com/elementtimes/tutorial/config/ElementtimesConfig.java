package com.elementtimes.tutorial.config;

import com.elementtimes.tutorial.ElementTimes;
import net.minecraftforge.common.config.Config;

/**
 * 配置文件
 *
 * @author KSGFK create in 2019/2/17
 */
@Config(modid = ElementTimes.MODID)
public class ElementtimesConfig {
    @Config.Name("General")
    @Config.LangKey("elementtimes.config.general")
    public static final General GENERAL = new General();

    @Config.Name("GeneralFuel")
    @Config.LangKey("elementtimes.config.general.fuel")
    public static final FuelGeneral FUEL_GENERAL = new FuelGeneral();

    @Config.Name("Pul")
    @Config.LangKey("elementtimes.config.pul")
    public static final Pul PUL = new Pul();

    @Config.Name("compressor")
    @Config.LangKey("elementtimes.config.compressor")
    public static final Compressor COMPRESSOR = new Compressor();

    @Config.Name("furnace")
    @Config.LangKey("elementtimes.config.furnace")
    public static final Furnace FURNACE = new Furnace();

    @Config.Name("extractor")
    @Config.LangKey("elementtimes.config.extractor")
    public static final Extractor EXTRACTOR = new Extractor();

    @Config.Name("rebuild")
    @Config.LangKey("elementtimes.config.rebuild")
    public static final Rebuild REBUILD = new Rebuild();

    @Config.Name("forming")
    @Config.LangKey("elementtimes.config.forming")
    public static final Forming FORMING = new Forming();

    public static final class General {
        @Config.Comment("设置发电机最大容量")
        @Config.Name("generaterMaxEnergy")
        public int generaterMaxEnergy = 100000000;

        @Config.Comment("设置发电机最大输出")
        @Config.RequiresWorldRestart
        @Config.Name("generaterMaxExtract")
        public int generaterMaxExtract = 500000;

        @Config.Comment("设置发电机发电速度")
        @Config.Name("generaterMaxReceive")
        public int generaterMaxReceive = 1000000;

        @Config.Comment("设置火元素发电量")
        @Config.Name("generaterFireGen")
        public int generaterFireGen = 2000000;
        @Config.Comment("设置水元素发电量")
        @Config.Name("generaterWaterGen")
        public int generaterWaterGen = 90000000;
        @Config.Comment("设置木元素发电量")
        @Config.Name("generaterWoodGen")
        public int generaterWoodGen = 1500000;
        @Config.Comment("设置土元素发电量")
        @Config.Name("generaterSoilGen")
        public int generaterSoilGen = 1000000;
        @Config.Comment("设置金元素发电量")
        @Config.Name("generaterGoldGen")
        public int generaterGoldGen = 4000000;
        @Config.Comment("设置五行元素发电量")
        @Config.Name("generaterFive")
        public int generaterFive = 15000000;
        @Config.Comment("设置光元素发电量")
        @Config.Name("generaterSun")
        public int generaterSun = 15000000;
        @Config.Comment("设置影元素发电量")
        @Config.Name("generaterEnd")
        public int generaterEnd = 15000000;
        @Config.Comment("设置橡胶树生成时,自带橡胶的概率(最大100)")
        @Config.Name("rubberTreeGenRubberProbability")
        public int rubberTreeGenRubberProbability = 50;
    }

    public static final class FuelGeneral {
        @Config.Comment("设置火力发电机最大容量")
        @Config.Name("generaterMaxEnergy")
        public int generaterMaxEnergy = 100000;

        @Config.Comment("设置火力发电机最大输出")
        @Config.Name("generaterMaxExtract")
        public int generaterMaxExtract = 1000;

        @Config.Comment("设置火力发电机发电速度")
        @Config.Name("generaterMaxReceive")
        public int generaterMaxReceive = 1000;
    }

    public static final class Pul {
        @Config.Comment("设置打粉机机最大电容量")
        @Config.Name("pulMaxEnergy")
        public int pulMaxEnergy = 320000;

        @Config.Comment("设置打粉机每Tick消耗电量(相当于打粉速度")
        @Config.Name("pulMaxExtract")
        public int pulMaxExtract = 40;

        @Config.Comment("设置打粉机最大输入")
        @Config.Name("pulMaxReceive")
        public int pulMaxReceive = 40;

        @Config.Comment("设置打粉机每个矿物耗电量")
        @Config.Name("pulPowderEnergy")
        public int pulPowderEnergy = 8000;

        @Config.Comment("设置打粉机每个矿物产粉量")
        @Config.Name("pulPowderCount")
        public int pulPowderCount = 5;
    }

    public static final class Compressor {
        @Config.Comment("设置压缩机机最大电容量")
        @Config.Name("compressorMaxEnergy")
        public int maxEnergy = 320000;

        @Config.Comment("设置压缩机每Tick消耗电量(相当于打粉速度")
        @Config.Name("compressorMaxExtract")
        public int maxExtract = 40;

        @Config.Comment("设置压缩机最大输入")
        @Config.Name("compressorMaxReceive")
        public int maxReceive = 40;

        @Config.Comment("设置压缩机每个矿物耗电量")
        @Config.Name("compressorPowderEnergy")
        public int powderEnergy = 8000;

        @Config.Comment("设置压缩机每个矿物产粉量")
        @Config.Name("compressorPowderCount")
        public int powderCount = 1;

//        @Config.Comment("设置压缩机可放入的矿石")
//        @Config.Name("compressorCanPutIn")
//        public String[] canPutIn = new String[]{
//                "oreIron",
//                "oreRedstone",
//                "oreGold",
//                "oreDiamond",
//                "oreLapis",
//                "oreEmerald",
//                "oreCopper",
//                "oreCoal",
//                "orePlatinum"};
    }

    public static final class Furnace {
        @Config.Comment("设置熔炉最大电容量")
        @Config.Name("maxEnergy")
        public int maxEnergy = 100000;

        @Config.Comment("设置熔炉每Tick消耗电量")
        @Config.Name("maxExtract")
        public int maxExtract = 20;

        @Config.Comment("设置熔炉最大输入")
        @Config.Name("maxReceive")
        public int maxReceive = 20;
    }

    public static final class Extractor {
        @Config.Comment("设置提取机最大电容量")
        @Config.Name("maxEnergy")
        public int maxEnergy = 320000;

        @Config.Comment("设置提取机每Tick消耗电量")
        @Config.Name("maxExtract")
        public int maxExtract = 15;

        @Config.Comment("设置提取机最大输入")
        @Config.Name("maxReceive")
        public int maxReceive = 15;
    }

    public static final class Rebuild {
        @Config.Comment("设置重构机最大电容量")
        @Config.Name("maxEnergy")
        public int maxEnergy = 10000000;

        @Config.Comment("设置重构机每Tick消耗电量")
        @Config.Name("maxExtract")
        public int maxExtract = 2000;

        @Config.Comment("设置重构机最大输入")
        @Config.Name("maxReceive")
        public int maxReceive = 2000;
    }

    public static final class Forming {
        @Config.Comment("设置整形机最大电容量")
        @Config.Name("maxEnergy")
        public int maxEnergy = 320000;

        @Config.Comment("设置整形机每Tick消耗电量")
        @Config.Name("maxExtract")
        public int maxExtract = 20;

        @Config.Comment("设置整形机最大输入")
        @Config.Name("maxReceive")
        public int maxReceive = 20;
    }
}
