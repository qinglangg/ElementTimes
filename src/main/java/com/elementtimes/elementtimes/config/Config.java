package com.elementtimes.elementtimes.config;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.common.ForgeConfigSpec.DoubleValue;
import net.minecraftforge.common.ForgeConfigSpec.IntValue;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.loading.FMLPaths;

/**
 * 配置文件

 */
@Mod.EventBusSubscriber
public class Config {

    public static ForgeConfigSpec COMMON;
    public static CommentedFileConfig CONFIG_DATA;

    public static DoubleValue rubberProb;

    public static IntValue genFuelCapacity;
    public static IntValue genFuelOutput;
    public static IntValue genFuelSpeed;
    public static DoubleValue genFuelMultiple;
    public static IntValue genElemCapacity;
    public static IntValue genElemOutput;
    public static IntValue genElemSpeed;
    public static IntValue genFluidCapacity;
    public static IntValue genFluidOutput;
    public static IntValue genFluidSpeed;
    public static DoubleValue genFluidMultiple;
    public static IntValue genSunCapacity;
    public static IntValue genSunOutput;
    public static IntValue genSunSpeed;

    public static IntValue furnaceCapacity;
    public static IntValue furnaceInput;
    public static IntValue furnaceSpeed;
    public static DoubleValue furnaceMultiple;
    public static IntValue compressorCapacity;
    public static IntValue compressorInput;
    public static IntValue compressorSpeed;
    public static IntValue extractorCapacity;
    public static IntValue extractorInput;
    public static IntValue extractorSpeed;
    public static IntValue formingCapacity;
    public static IntValue formingInput;
    public static IntValue formingSpeed;
    public static IntValue reducerCapacity;
    public static IntValue reducerInput;
    public static IntValue reducerSpeed;
    public static IntValue rebuildCapacity;
    public static IntValue rebuildInput;
    public static IntValue rebuildSpeed;
    public static IntValue pulverizerCapacity;
    public static IntValue pulverizerInput;
    public static IntValue pulverizerSpeed;

    public static IntValue storageEnergy;
    public static IntValue storageFluid;

    public static ForgeConfigSpec build() {
        ForgeConfigSpec.Builder builder = new ForgeConfigSpec.Builder();
        pushCommon(builder);
        pushGenerator(builder);
        pushElectricalAppliance(builder);
        pushStorage(builder);
        COMMON = builder.build();
        CONFIG_DATA = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve("elementtimes.toml"))
                .autosave()
                .autoreload()
                .sync()
                .writingMode(WritingMode.REPLACE)
                .build();
        CONFIG_DATA.load();
        COMMON.setConfig(CONFIG_DATA);
        return COMMON;
    }

    private static void pushCommon(ForgeConfigSpec.Builder builder) {
        builder.push("Common");
        rubberProb = define(builder, 0.3, 1, "config.elementtimes.common.rubber_probability", "Rubber Probability");
        builder.pop();
    }

    private static void pushGenerator(ForgeConfigSpec.Builder builder) {
        builder.push("Generator");
        genFuelCapacity = define(builder, 100000, "config.elementtimes.generator.fuel.capacity", "Fuel: Capacity");
        genFuelOutput   = define(builder, 100   , "config.elementtimes.generator.fuel.output"  , "Fuel: Output");
        genFuelSpeed    = define(builder, 100   , "config.elementtimes.generator.fuel.speed"   , "Fuel: Speed");
        genFuelMultiple = define(builder, 10d   , "config.elementtimes.generator.fuel.multiple", "Fuel: Multiple");
        genElemCapacity = define(builder, 100000, "config.elementtimes.generator.element.capacity", "Element: Capacity");
        genElemOutput   = define(builder, 100   , "config.elementtimes.generator.element.output"  , "Element: Output");
        genElemSpeed    = define(builder, 100   , "config.elementtimes.generator.element.speed"   , "Element: Speed");
        genFluidCapacity = define(builder, 100000, "config.elementtimes.generator.fluid.capacity", "Fluid: Capacity");
        genFluidOutput   = define(builder, 100   , "config.elementtimes.generator.fluid.output"  , "Fluid: Output");
        genFluidSpeed    = define(builder, 100   , "config.elementtimes.generator.fluid.speed"   , "Fluid: Speed");
        genFluidMultiple = define(builder, 10d   , "config.elementtimes.generator.fluid.multiple", "Fluid: Multiple");
        genSunCapacity = define(builder, 100000, "config.elementtimes.generator.sun.capacity", "Sun: Capacity");
        genSunOutput   = define(builder, 100   , "config.elementtimes.generator.sun.output"  , "Sun: Output");
        genSunSpeed    = define(builder, 100   , "config.elementtimes.generator.sun.speed"   , "Sun: Speed");
        builder.pop();
    }

    private static void pushElectricalAppliance(ForgeConfigSpec.Builder builder) {
        builder.push("Electrical Appliance");
        compressorCapacity = define(builder, 100000, "config.elementtimes.appliance.compressor.capacity", "Compressor: Capacity");
        compressorInput    = define(builder, 500   , "config.elementtimes.appliance.compressor.input"   , "Compressor: Input");
        compressorSpeed    = define(builder, 100   , "config.elementtimes.appliance.compressor.speed"   , "Compressor: Speed");
        extractorCapacity = define(builder, 100000, "config.elementtimes.appliance.extractor.capacity", "Extractor: Capacity");
        extractorInput    = define(builder, 500   , "config.elementtimes.appliance.extractor.input"   , "Extractor: Input");
        extractorSpeed    = define(builder, 100   , "config.elementtimes.appliance.extractor.speed"   , "Extractor: Speed");
        formingCapacity = define(builder, 100000, "config.elementtimes.appliance.forming.capacity", "Forming: Capacity");
        formingInput    = define(builder, 500   , "config.elementtimes.appliance.forming.input"   , "Forming: Input");
        formingSpeed    = define(builder, 100   , "config.elementtimes.appliance.forming.speed"   , "Forming: Speed");
        furnaceCapacity = define(builder, 100000, "config.elementtimes.appliance.furnace.capacity", "Furnace: Capacity");
        furnaceInput    = define(builder, 500   , "config.elementtimes.appliance.furnace.input"   , "Furnace: Input");
        furnaceSpeed    = define(builder, 100   , "config.elementtimes.appliance.furnace.speed"   , "Furnace: Speed");
        furnaceMultiple = define(builder, 10d   , "config.elementtimes.appliance.furnace.multiple", "Furnace: Multiple");
        pulverizerCapacity = define(builder, 100000, "config.elementtimes.appliance.pulverizer.capacity", "Pulverizer: Capacity");
        pulverizerInput    = define(builder, 500   , "config.elementtimes.appliance.pulverizer.input"   , "Pulverizer: Input");
        pulverizerSpeed    = define(builder, 100   , "config.elementtimes.appliance.pulverizer.speed"   , "Pulverizer: Speed");
        reducerCapacity = define(builder, 100000, "config.elementtimes.appliance.reducer.capacity", "ItemReducer: Capacity");
        reducerInput    = define(builder, 500   , "config.elementtimes.appliance.reducer.input"   , "ItemReducer: Input");
        reducerSpeed    = define(builder, 100   , "config.elementtimes.appliance.reducer.speed"   , "ItemReducer: Speed");
        rebuildCapacity = define(builder, 100000, "config.elementtimes.appliance.rebuild.capacity", "Rebuild: Capacity");
        rebuildInput    = define(builder, 500   , "config.elementtimes.appliance.rebuild.input"   , "Rebuild: Input");
        rebuildSpeed    = define(builder, 100   , "config.elementtimes.appliance.rebuild.speed"   , "Rebuild: Speed");
        builder.pop();
    }

    private static void pushStorage(ForgeConfigSpec.Builder builder) {
        builder.push("Storage");
        storageEnergy = define(builder, 10000000, "config.elementtimes.storage.energy.capacity", "Energy: Capacity");
        storageFluid = define(builder, 160000, "config.elementtimes.storage.fluid.capacity", "Fluid: Capacity");
        builder.pop();
    }

    private static ForgeConfigSpec.IntValue define(ForgeConfigSpec.Builder builder, int defaultValue, String translation, String path) {
        return builder.translation(translation).defineInRange(path, defaultValue, 0, Integer.MAX_VALUE);
    }

    private static ForgeConfigSpec.DoubleValue define(ForgeConfigSpec.Builder builder, double defaultValue, String translation, String path) {
        return builder.translation(translation).defineInRange(path, defaultValue, 0, Double.MAX_VALUE);
    }

    private static ForgeConfigSpec.DoubleValue define(ForgeConfigSpec.Builder builder, double defaultValue, double maxValue, String translation, String path) {
        return builder.translation(translation).defineInRange(path, defaultValue, 0, maxValue);
    }

    @SubscribeEvent
    public static void onReloading(ModConfig.ConfigReloading event) {
    }

    @SubscribeEvent
    public static void onLoading(ModConfig.Loading event) {
    }
}
