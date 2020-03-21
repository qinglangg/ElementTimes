package com.elementtimes.tutorial.config;

import com.elementtimes.tutorial.ElementTimes;
import net.minecraftforge.common.config.Config;

/**
 * 配置文件
 * @author KSGFK create in 2019/2/17
 */
@Config(modid = ElementTimes.MODID)
public class ETConfig {

    @Config.Name("ElementGen")
    @Config.LangKey("elementtimes.config.general_element")
    public static final ElementGen GENERATOR_ELE = new ElementGen();

    @Config.Name("ItemReducer")
    @Config.LangKey("elementtimes.config.general.item_reducer")
    public static final ItemReducer ITEM_REDUCER = new ItemReducer();

    @Config.Name("GeneralFuel")
    @Config.LangKey("elementtimes.config.general.fuel")
    public static final FuelGen FUEL_GENERAL = new FuelGen();

    @Config.Name("Pul")
    @Config.LangKey("elementtimes.config.pul")
    public static final Pulverize PULVERIZE = new Pulverize();

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

    @Config.Name("natural")
    @Config.LangKey("elementtimes.config.natural")
    public static final Natural NATURAL = new Natural();
}
