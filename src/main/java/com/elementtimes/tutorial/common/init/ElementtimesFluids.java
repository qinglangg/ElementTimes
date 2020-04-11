package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.ModFluid;
import com.elementtimes.elementcore.api.annotation.part.Method;
import com.elementtimes.elementcore.api.annotation.tools.ModBurnTime;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.block.fluid.SeaWaterBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

/**
 * 流体注册
 * @author 卿岚
 */
public class ElementtimesFluids {
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid steel = new Fluid("elementtimes.steel",
            new ResourceLocation(ElementTimes.MODID, "fluid/acetylene_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/acetylene_fluid"), 0xFF3B3B3B);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid Fe = new Fluid("elementtimes.fe",
            new ResourceLocation(ElementTimes.MODID, "fluid/al_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/al_fluid"), 0xFF959595);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid F2 = new Fluid("elementtimes.f2",
            new ResourceLocation(ElementTimes.MODID, "fluid/hf_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/hf_fluid"), 0xFF6E4A26);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid ConcentratedHydrochloricAcid = new Fluid("elementtimes.concentratedhydrochloricacid",
            new ResourceLocation(ElementTimes.MODID, "fluid/nonghcl_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/nonghcl_fluid"), 0xFF6F6F43);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid DiluteHydrochloricAcid = new Fluid("elementtimes.dilutehydrochloricacid",
            new ResourceLocation(ElementTimes.MODID, "fluid/xihcl_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/xihcl_fluid"), 0xFF979750);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid I2 = new Fluid("elementtimes.i2",
            new ResourceLocation(ElementTimes.MODID, "fluid/i2_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/i2_fluid"), 0xFF595928);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid HI = new Fluid("elementtimes.HI",
            new ResourceLocation(ElementTimes.MODID, "fluid/i2_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/i2_fluid"), 0xFF595928);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid Br2 = new Fluid("elementtimes.br2",
            new ResourceLocation(ElementTimes.MODID, "fluid/br2_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/br2_fluid"), 0xFF83833F);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid HBr = new Fluid("elementtimes.HBr",
            new ResourceLocation(ElementTimes.MODID, "fluid/br2_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/br2_fluid"), 0xFF83833F);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid HClO = new Fluid("elementtimes.hclo",
            new ResourceLocation(ElementTimes.MODID, "fluid/hclo_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/hclo_fluid"), 0xFF5F943E);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid h2so4 = new Fluid("elementtimes.h2so4",
            new ResourceLocation(ElementTimes.MODID, "fluid/h2so4_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/h2so4_fluid"), 0xFF969642);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid h2so3 = new Fluid("elementtimes.h2so3",
            new ResourceLocation(ElementTimes.MODID, "fluid/h2so3_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/h2so3_fluid"), 0xFFAEAE53);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid so3 = new Fluid("elementtimes.so3",
            new ResourceLocation(ElementTimes.MODID, "fluid/so3_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/so3_fluid"), 0xFF969642);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid so2 = new Fluid("elementtimes.so2",
            new ResourceLocation(ElementTimes.MODID, "fluid/so2_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/so2_fluid"), 0xFF969642);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid FluoroaluminicAcid = new Fluid("elementtimes.fluoroaluminicacid",
            new ResourceLocation(ElementTimes.MODID, "fluid/fluoroaluminicacid_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/fluoroaluminicacid_fluid"), 0xFF969642);

    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid hf = new Fluid("elementtimes.hf",
            new ResourceLocation(ElementTimes.MODID, "fluid/hf_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/hf_fluid"), 0xFF969642);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid Na = new Fluid("elementtimes.na",
            new ResourceLocation(ElementTimes.MODID, "fluid/na_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/na_fluid"), 0xFF8A8AAF);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid Mg = new Fluid("elementtimes.mg",
            new ResourceLocation(ElementTimes.MODID, "fluid/na_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/na_fluid"), 0xFF8A8AAF);  
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid Sicl4 = new Fluid("elementtimes.sicl4",
            new ResourceLocation(ElementTimes.MODID, "fluid/sicl4_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/sicl4_fluid"), 0xFF928181);
            
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid HCl = new Fluid("elementtimes.hcl",
            new ResourceLocation(ElementTimes.MODID, "fluid/hcl_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/hcl_fluid"), 0xFF969642);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid NaCl = new Fluid("elementtimes.nacl",
            new ResourceLocation(ElementTimes.MODID, "fluid/nacl_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/nacl_fluid"), 0xFF1D5B5B);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid MgCl2 = new Fluid("elementtimes.mgcl2",
            new ResourceLocation(ElementTimes.MODID, "fluid/nacl_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/nacl_fluid"), 0xFF1D5B5B);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid AlCl3 = new Fluid("elementtimes.alcl3",
            new ResourceLocation(ElementTimes.MODID, "fluid/alcl3_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/alcl3_fluid"), 0xFF1D5B5B);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid NaClSolutionConcentrated = new Fluid("elementtimes.naclsolutionconcentrated",
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutionconcentrated_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutionconcentrated_fluid"), 0xFF3B9595);
    
    @ModFluid
    @ModFluid.FluidBlockFunc(@Method(SeaWaterBlock.class))
    public static Fluid seawater = new Fluid("elementtimes.seawater",
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutionconcentrated_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutionconcentrated_fluid"), 0xFF3B9595);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid NaAlO2 = new Fluid("elementtimes.naalo2",
            new ResourceLocation(ElementTimes.MODID, "fluid/naalo2_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/naalo2_fluid"), 0xFF3B9595);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid Cu_no3_2 = new Fluid("elementtimes.cu_no3_2",
            new ResourceLocation(ElementTimes.MODID, "fluid/naalo2_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/naalo2_fluid"), 0xFF3B9595);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid NaClSolutionDilute = new Fluid("elementtimes.naclsolutiondilute",
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_fluid"), 0xFF4E9696);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid MagnesiumChlorideSolution  = new Fluid("elementtimes.magnesiumchloridesolution",
            new ResourceLocation(ElementTimes.MODID, "fluid/nh4cl_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/nh4cl_fluid"), 0xFF4E9696);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid NH4Cl = new Fluid("elementtimes.nh4cl",
            new ResourceLocation(ElementTimes.MODID, "fluid/nh4cl_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/nh4cl_fluid"), 0xFF4E9696);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid Naoh = new Fluid("elementtimes.naoh",
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_fluid"), 0xFF4E9696);
    
    
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid NaNo3 = new Fluid("elementtimes.nano3",
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_fluid"), 0xFF4E9696);

    @ModBurnTime(1600)
    @ModFluid(density = -10)
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

    @ModBurnTime(5000)
    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid acetylene = new Fluid("elementtimes.acetylene",
            new ResourceLocation(ElementTimes.MODID, "fluid/acetylene_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/acetylene_fluid"), 0xFF3B3B3B);

    @ModBurnTime(7000)
    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid ethylene = new Fluid("elementtimes.ethylene",
            new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_fluid"), 0xFF5B5B5B);

    @ModBurnTime(800)
    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid co = new Fluid("elementtimes.co",
            new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_fluid"), 0xFF5B5B5B);

    @ModBurnTime(6000)
    @ModFluid()
    @ModFluid.FluidBlock
    public static Fluid ethanol = new Fluid("elementtimes.ethanol",
            new ResourceLocation(ElementTimes.MODID, "fluid/ethanol_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/ethanol_fluid"), 0xFF52889A);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid calciumHydroxide = new Fluid("elementtimes.calciumhydroxide",
            new ResourceLocation(ElementTimes.MODID, "fluid/calciumhydroxide_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/calciumhydroxide_fluid"), 0xFF3B9884);
    
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid calmogastrin = new Fluid("elementtimes.calmogastrin",
            new ResourceLocation(ElementTimes.MODID, "fluid/calmogastrin_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/calmogastrin_fluid"), 0xFF3B9884);

    @ModBurnTime(6000)
    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid methane = new Fluid("elementtimes.methane",
            new ResourceLocation(ElementTimes.MODID, "fluid/methane_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/methane_fluid"), 0xFF868686);

    @ModBurnTime(9000)
    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid ethane = new Fluid("elementtimes.ethane",
            new ResourceLocation(ElementTimes.MODID, "fluid/methane_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/methane_fluid"), 0xFF868686);

    @ModFluid
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

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid Al = new Fluid("elementtimes.al",
            new ResourceLocation(ElementTimes.MODID, "fluid/al_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/al_fluid"), 0xFF959595);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid Na3AlF6 = new Fluid("elementtimes.na3alf6",
            new ResourceLocation(ElementTimes.MODID, "fluid/na3alf6_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/na3alf6_fluid"), 0xFF868686);

    @ModFluid(density = -10)
    @ModFluid.FluidBlock
    public static Fluid ammonia = new Fluid("elementtimes.ammonia",
            new ResourceLocation(ElementTimes.MODID, "fluid/ammonia_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/ammonia_fluid"), 0xFF868686);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid oxygen = new Fluid("elementtimes.oxygen",
            new ResourceLocation(ElementTimes.MODID, "fluid/oxygen_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/oxygen_fluid"), 0xFF427676);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid air = new Fluid("elementtimes.air",
            new ResourceLocation(ElementTimes.MODID, "fluid/air_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/air_fluid"), 0xFF949494);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid Al2O3_Na3AlF6 = new Fluid("elementtimes.al2o3_na3alf6",
            new ResourceLocation(ElementTimes.MODID, "fluid/al2o3_na3alf6_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/al2o3_na3alf6_fluid"), 0xFF949494);

    @ModFluid(density = -1)
    @ModFluid.FluidBlock
    public static Fluid rareGases = new Fluid("elementtimes.raregases",
            new ResourceLocation(ElementTimes.MODID, "fluid/air_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/air_fluid"), 0xFF949494);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid co2 = new Fluid("elementtimes.co2",
            new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_fluid"), 0xFF5B5B5B);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid hno3 = new Fluid("elementtimes.hno3",
            new ResourceLocation(ElementTimes.MODID, "fluid/hno3_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/hno3_fluid"), 0xFF96603B);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid nongHNO3 = new Fluid("elementtimes.nonghno3",
            new ResourceLocation(ElementTimes.MODID, "fluid/no2_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/no2_still"), 0xFF9F3F3F);
    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid xiHNO3 = new Fluid("elementtimes.xihon3",
            new ResourceLocation(ElementTimes.MODID, "fluid/no_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/no_fluid"), 0xFF967474);
    @ModFluid(density = -5)
    @ModFluid.FluidBlock
    public static Fluid no = new Fluid("elementtimes.no",
            new ResourceLocation(ElementTimes.MODID, "fluid/no_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/no_fluid"), 0xFF967474);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid no2 = new Fluid("elementtimes.no2",
            new ResourceLocation(ElementTimes.MODID, "fluid/no2_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/no2_fluid"), 0xFF9F3F3F);

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid o3 = new Fluid("elementtimes.o3",
            new ResourceLocation(ElementTimes.MODID, "fluid/o3_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/o3_fluid"), 0xFF6F6F92);
}
