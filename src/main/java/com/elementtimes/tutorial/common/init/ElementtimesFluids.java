package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.annotation.annotations.ModFluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

/**
 * 流体注册
 * @author luqin2007
 */
public class ElementtimesFluids {

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid NaCl = new Fluid("elementtimes.nacl",
            new ResourceLocation(ElementTimes.MODID, "fluid/nacl_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/nacl_fluid"), 0xFF1D5B5B);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid NaClSolutionConcentrated = new Fluid("elementtimes.naclsolutionconcentrated",
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutionconcentrated_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutionconcentrated_fluid"), 0xFF3B9595);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid NaClSolutionDilute = new Fluid("elementtimes.naclsolutiondilute",
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_fluid"), 0xFF4E9696);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid Naoh = new Fluid("elementtimes.naoh",
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_fluid"), 0xFF4E9696);
    
    @ModFluid(density = -10, burningTime = 4000)
    @ModFluid.FluidBlock
    public static Fluid H = new Fluid("elementtimes.h",
            new ResourceLocation(ElementTimes.MODID, "fluid/h_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/h_fluid"), 0xFF709292);

    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid steam = new Fluid("elementtimes.steam",
            new ResourceLocation(ElementTimes.MODID, "fluid/steam_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/steam_fluid"), 0xFF869999);

    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid waterDistilled = new Fluid("elementtimes.waterdistilled",
            new ResourceLocation(ElementTimes.MODID, "fluid/waterdistilled_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/waterdistilled_fluid"), 0xFF779B9B);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid butter = new Fluid("elementtimes.butter",
            new ResourceLocation(ElementTimes.MODID, "fluid/butter_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/butter_fluid"), 0xFF92925E);

    @ModFluid(density = -10, burningTime = 5000)
    @ModFluid.FluidBlock
    public static Fluid acetylene = new Fluid("elementtimes.acetylene",
            new ResourceLocation(ElementTimes.MODID, "fluid/acetylene_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/acetylene_fluid"), 0xFF3B3B3B);

    @ModFluid(density = -10, burningTime = 10000)
    @ModFluid.FluidBlock
    public static Fluid ethylene = new Fluid("elementtimes.ethylene",
            new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_fluid"), 0xFF5B5B5B);

    @ModFluid(density = -10, burningTime = 10000)
    @ModFluid.FluidBlock
    public static Fluid co = new Fluid("elementtimes.co",
            new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_fluid"), 0xFF5B5B5B);
    
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid ethanol = new Fluid("elementtimes.ethanol",
            new ResourceLocation(ElementTimes.MODID, "fluid/ethanol_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/ethanol_fluid"), 0xFF52889A);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid calciumHydroxide = new Fluid("elementtimes.calciumhydroxide",
            new ResourceLocation(ElementTimes.MODID, "fluid/calciumhydroxide_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/calciumhydroxide_fluid"), 0xFF3B9884);

    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid methane = new Fluid("elementtimes.methane",
            new ResourceLocation(ElementTimes.MODID, "fluid/methane_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/methane_fluid"), 0xFF868686);

    @ModFluid(density = -10, burningTime = 16000)
    @ModFluid.FluidBlock
    public static Fluid ethane = new Fluid("elementtimes.ethane",
            new ResourceLocation(ElementTimes.MODID, "fluid/methane_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/methane_fluid"), 0xFF868686);

    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid chlorine = new Fluid("elementtimes.chlorine",
            new ResourceLocation(ElementTimes.MODID, "fluid/chlorine_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/chlorine_fluid"), 0xFF96963B);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid concentratedSulfuricAcid = new Fluid("elementtimes.concentratedsulfuricacid",
            new ResourceLocation(ElementTimes.MODID, "fluid/concentratedsulfuricacid_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/concentratedsulfuricacid_fluid"), 0xFF886440);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid diluteSulfuricAcid = new Fluid("elementtimes.dilutesulfuricacid",
            new ResourceLocation(ElementTimes.MODID, "fluid/dilutesulfuricacid_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/dilutesulfuricacid_fluid"), 0xFF93823C);

    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid nitrogen = new Fluid("elementtimes.nitrogen",
            new ResourceLocation(ElementTimes.MODID, "fluid/nitrogen_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/nitrogen_fluid"), 0xFF959595);

    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid ammonia = new Fluid("elementtimes.ammonia",
            new ResourceLocation(ElementTimes.MODID, "fluid/ammonia_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/ammonia_fluid"), 0xFF868686);

    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid oxygen = new Fluid("elementtimes.oxygen",
            new ResourceLocation(ElementTimes.MODID, "fluid/oxygen_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/oxygen_fluid"), 0xFF427676);
}
