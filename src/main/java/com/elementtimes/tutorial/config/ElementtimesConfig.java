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
    @Config.LangKey("elementtimes.config.general")
    public static final General general = new General();

    @Config.Name("Pul")
    @Config.LangKey("elementtimes.config.pul")
    public static final Pul pul = new Pul();

    @Config.Name("compressor")
    @Config.LangKey("elementtimes.config.compressor")
    public static final Compressor compressor = new Compressor();

    public static final class General {
        @Config.Comment("设置发电机最大容量")
        @Config.Name("generaterMaxEnergy")
        public int generaterMaxEnergy = 1000000000;

        @Config.Comment("设置发电机最大输出")
        @Config.Name("generaterMaxExtract")
        public int generaterMaxExtract = 1000000;

        @Config.Comment("设置发电机最大输入(发电速度等于最大输入)")
        @Config.Name("generaterMaxReceive")
        public int generaterMaxReceive = 1000000;

        @Config.Comment("设置火元素发电量")
        @Config.Name("generaterFireGen")
        public int generaterFireGen = 90000000;
        @Config.Comment("设置水元素发电量")
        @Config.Name("generaterWaterGen")
        public int generaterWaterGen = 90000000;
        @Config.Comment("设置木元素发电量")
        @Config.Name("generaterWoodGen")
        public int generaterWoodGen = 30000000;
        @Config.Comment("设置土元素发电量")
        @Config.Name("generaterSoilGen")
        public int generaterSoilGen = 30000000;
        @Config.Comment("设置金元素发电量")
        @Config.Name("generaterGoldGen")
        public int generaterGoldGen = 60000000;
        @Config.Comment("设置五行元素发电量")
        @Config.Name("generaterFive")
        public int generaterFive = 324000000;
        @Config.Comment("设置光元素发电量")
        @Config.Name("generaterFive")
        public int generaterSun = 324000000;
        @Config.Comment("设置影元素发电量")
        @Config.Name("generaterEnd")
        public int generaterEnd = 324000000;
    }

    public static final class Pul {
        @Config.Comment("设置打粉机机最大电容量")
        @Config.Name("pulMaxEnergy")
        public int pulMaxEnergy = 3200000;

        @Config.Comment("设置打粉机每Tick消耗电量(相当于打粉速度")
        @Config.Name("pulMaxExtract")
        public int pulMaxExtract = 20;

        @Config.Comment("设置打粉机最大输入")
        @Config.Name("pulMaxReceive")
        public int pulMaxReceive = 1000000;

        @Config.Comment("设置打粉机每个矿物耗电量")
        @Config.Name("pulPowderEnergy")
        public int pulPowderEnergy = 2000;

        @Config.Comment("设置打粉机每个矿物产粉量")
        @Config.Name("pulPowderCount")
        public int pulPowderCount = 5;

        @Config.Comment("设置打粉机可放入的矿石")
        @Config.Name("pulCanPutIn")
        public String[] pulCanPutIn = new String[]{
                "oreIron",
                "oreRedstone",
                "oreGold",
                "oreDiamond",
                "oreLapis",
                "oreEmerald",
                "oreCopper",
                "oreCoal",
                "orePlatinum"};
    }

    public static final class Compressor {
        @Config.Comment("设置压缩机机最大电容量")
        @Config.Name("compressorMaxEnergy")
        public int compressorMaxEnergy = 3200000;

        @Config.Comment("设置压缩机每Tick消耗电量(相当于打粉速度")
        @Config.Name("compressorMaxExtract")
        public int compressorMaxExtract = 20;

        @Config.Comment("设置压缩机最大输入")
        @Config.Name("compressorMaxReceive")
        public int compressorMaxReceive = 1000000;

        @Config.Comment("设置压缩机每个矿物耗电量")
        @Config.Name("compressorPowderEnergy")
        public int compressorPowderEnergy = 2000;

        @Config.Comment("设置压缩机每个矿物产粉量")
        @Config.Name("compressorPowderCount")
        public int compressorPowderCount = 5;

        @Config.Comment("设置压缩机可放入的矿石")
        @Config.Name("compressorCanPutIn")
        public String[] compressorCanPutIn = new String[]{
                "oreIron",
                "oreRedstone",
                "oreGold",
                "oreDiamond",
                "oreLapis",
                "oreEmerald",
                "oreCopper",
                "oreCoal",
                "orePlatinum"};
    }
}
