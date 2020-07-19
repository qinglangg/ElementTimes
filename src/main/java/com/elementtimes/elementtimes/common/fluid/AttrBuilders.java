package com.elementtimes.elementtimes.common.fluid;

import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.fluid.Fluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;

/**
 * 流体属性

 */
public class AttrBuilders {
    public static FluidAttributes.Builder steel = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/acetylene_still"), new ResourceLocation(ElementTimes.MODID, "fluid/acetylene_fluid")).color(0xFF3B3B3B);
    public static FluidAttributes.Builder Fe = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/al_still"), new ResourceLocation(ElementTimes.MODID, "fluid/al_fluid")).color(0xFF959595);
    public static FluidAttributes.Builder F2 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/hf_still"), new ResourceLocation(ElementTimes.MODID, "fluid/hf_fluid")).color(0xFF6E4A26);
    public static FluidAttributes.Builder ConcentratedHydrochloricAcid = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/nonghcl_still"), new ResourceLocation(ElementTimes.MODID, "fluid/nonghcl_fluid")).color(0xFF6F6F43);
    public static FluidAttributes.Builder DiluteHydrochloricAcid = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/xihcl_still"), new ResourceLocation(ElementTimes.MODID, "fluid/xihcl_fluid")).color(0xFF979750);
    public static FluidAttributes.Builder I2 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/i2_still"), new ResourceLocation(ElementTimes.MODID, "fluid/i2_fluid")).color(0xFF595928);
    public static FluidAttributes.Builder HI = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/i2_still"), new ResourceLocation(ElementTimes.MODID, "fluid/i2_fluid")).color(0xFF595928);
    public static FluidAttributes.Builder Br2 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/br2_still"), new ResourceLocation(ElementTimes.MODID, "fluid/br2_fluid")).color(0xFF83833F);
    public static FluidAttributes.Builder HBr = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/br2_still"), new ResourceLocation(ElementTimes.MODID, "fluid/br2_fluid")).color(0xFF83833F);
    public static FluidAttributes.Builder HClO = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/hclo_still"), new ResourceLocation(ElementTimes.MODID, "fluid/hclo_fluid")).color(0xFF5F943E);
    public static FluidAttributes.Builder h2so4 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/h2so4_still"), new ResourceLocation(ElementTimes.MODID, "fluid/h2so4_fluid")).color(0xFF969642);
    public static FluidAttributes.Builder h2so3 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/h2so3_still"), new ResourceLocation(ElementTimes.MODID, "fluid/h2so3_fluid")).color(0xFFAEAE53);
    public static FluidAttributes.Builder so3 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/so3_still"), new ResourceLocation(ElementTimes.MODID, "fluid/so3_fluid")).color(0xFF969642);
    public static FluidAttributes.Builder so2 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/so2_still"), new ResourceLocation(ElementTimes.MODID, "fluid/so2_fluid")).color(0xFF969642);
    public static FluidAttributes.Builder FluoroaluminicAcid = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/fluoroaluminicacid_still"), new ResourceLocation(ElementTimes.MODID, "fluid/fluoroaluminicacid_fluid")).color(0xFF969642);
    public static FluidAttributes.Builder hf = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/hf_still"), new ResourceLocation(ElementTimes.MODID, "fluid/hf_fluid")).color(0xFF969642).density(-10);
    public static FluidAttributes.Builder Na = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/na_still"), new ResourceLocation(ElementTimes.MODID, "fluid/na_fluid")).color(0xFF8A8AAF);
    public static FluidAttributes.Builder Mg = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/na_still"), new ResourceLocation(ElementTimes.MODID, "fluid/na_fluid")).color(0xFF8A8AAF);
    public static FluidAttributes.Builder Sicl4 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/sicl4_still"), new ResourceLocation(ElementTimes.MODID, "fluid/sicl4_fluid")).color(0xFF928181);
    public static FluidAttributes.Builder HCl = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/hcl_still"), new ResourceLocation(ElementTimes.MODID, "fluid/hcl_fluid")).color(0xFF969642);
    public static FluidAttributes.Builder NaCl = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/nacl_still"), new ResourceLocation(ElementTimes.MODID, "fluid/nacl_fluid")).color(0xFF1D5B5B);
    public static FluidAttributes.Builder MgCl2 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/nacl_still"), new ResourceLocation(ElementTimes.MODID, "fluid/nacl_fluid")).color(0xFF1D5B5B);
    public static FluidAttributes.Builder AlCl3s = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/alcl3_still"), new ResourceLocation(ElementTimes.MODID, "fluid/alcl3_fluid")).color(0xFF1D5B5B);
    public static FluidAttributes.Builder NaClSolutionConcentrated = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutionconcentrated_still"), new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutionconcentrated_fluid")).color(0xFF3B9595);
    public static FluidAttributes.Builder seawater = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutionconcentrated_still"), new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutionconcentrated_fluid")).color(0xFF3B9595);
    public static FluidAttributes.Builder NaAlO2 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/naalo2_still"), new ResourceLocation(ElementTimes.MODID, "fluid/naalo2_fluid")).color(0xFF3B9595);
    public static FluidAttributes.Builder Cu_no3_2 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/naalo2_still"), new ResourceLocation(ElementTimes.MODID, "fluid/naalo2_fluid")).color(0xFF3B9595);
    public static FluidAttributes.Builder NaClSolutionDilute = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_still"), new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_fluid")).color(0xFF4E9696);
    public static FluidAttributes.Builder MagnesiumChlorideSolution  = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/nh4cl_still"), new ResourceLocation(ElementTimes.MODID, "fluid/nh4cl_fluid")).color(0xFF4E9696);
    public static FluidAttributes.Builder NH4Cl = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/nh4cl_still"), new ResourceLocation(ElementTimes.MODID, "fluid/nh4cl_fluid")).color(0xFF4E9696);
    public static FluidAttributes.Builder Naoh = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_still"), new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_fluid")).color(0xFF4E9696);
    public static FluidAttributes.Builder NaNo3 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_still"), new ResourceLocation(ElementTimes.MODID, "fluid/naclsolutiondilute_fluid")).color(0xFF4E9696);
    public static FluidAttributes.Builder H = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/h_still"), new ResourceLocation(ElementTimes.MODID, "fluid/h_fluid")).color(0xFF709292).density(-10);
    public static FluidAttributes.Builder steam = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/steam_still"), new ResourceLocation(ElementTimes.MODID, "fluid/steam_fluid")).color(0xFF869999).density(-10);
    public static FluidAttributes.Builder waterDistilled = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/waterdistilled_still"), new ResourceLocation(ElementTimes.MODID, "fluid/waterdistilled_fluid")).color(0xFF779B9B).density(-10);
    public static FluidAttributes.Builder butter = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/butter_still"), new ResourceLocation(ElementTimes.MODID, "fluid/butter_fluid")).color(0xFF92925E);
    public static FluidAttributes.Builder acetylene = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/acetylene_still"), new ResourceLocation(ElementTimes.MODID, "fluid/acetylene_fluid")).color(0xFF3B3B3B);
    public static FluidAttributes.Builder ethylene = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_still"), new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_fluid")).color(0xFF5B5B5B).density(-10);
    public static FluidAttributes.Builder co = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_still"), new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_fluid")).color(0xFF5B5B5B).density(-10);
    public static FluidAttributes.Builder ethanol = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/ethanol_still"), new ResourceLocation(ElementTimes.MODID, "fluid/ethanol_fluid")).color(0xFF52889A);
    public static FluidAttributes.Builder calciumHydroxide = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/calciumhydroxide_still"), new ResourceLocation(ElementTimes.MODID, "fluid/calciumhydroxide_fluid")).color(0xFF3B9884);
    public static FluidAttributes.Builder calmogastrin = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/calmogastrin_still"), new ResourceLocation(ElementTimes.MODID, "fluid/calmogastrin_fluid")).color(0xFF3B9884);
    public static FluidAttributes.Builder methane = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/methane_still"), new ResourceLocation(ElementTimes.MODID, "fluid/methane_fluid")).color(0xFF868686).density(-10);
    public static FluidAttributes.Builder ethane = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/methane_still"), new ResourceLocation(ElementTimes.MODID, "fluid/methane_fluid")).color(0xFF868686).density(-10);
    public static FluidAttributes.Builder chlorine = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/chlorine_still"), new ResourceLocation(ElementTimes.MODID, "fluid/chlorine_fluid")).color(0xFF96963B);
    public static FluidAttributes.Builder concentratedSulfuricAcid = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/concentratedsulfuricacid_still"), new ResourceLocation(ElementTimes.MODID, "fluid/concentratedsulfuricacid_fluid")).color(0xFF886440);
    public static FluidAttributes.Builder diluteSulfuricAcid = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/dilutesulfuricacid_still"), new ResourceLocation(ElementTimes.MODID, "fluid/dilutesulfuricacid_fluid")).color(0xFF93823C);
    public static FluidAttributes.Builder nitrogen = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/nitrogen_still"), new ResourceLocation(ElementTimes.MODID, "fluid/nitrogen_fluid")).color(0xFF959595).density(-10);
    public static FluidAttributes.Builder Al = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/al_still"), new ResourceLocation(ElementTimes.MODID, "fluid/al_fluid")).color(0xFF959595);
    public static FluidAttributes.Builder Na3AlF6 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/na3alf6_still"), new ResourceLocation(ElementTimes.MODID, "fluid/na3alf6_fluid")).color(0xFF868686);
    public static FluidAttributes.Builder ammonia = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/ammonia_still"), new ResourceLocation(ElementTimes.MODID, "fluid/ammonia_fluid")).color(0xFF868686).density(-10);
    public static FluidAttributes.Builder oxygen = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/oxygen_still"), new ResourceLocation(ElementTimes.MODID, "fluid/oxygen_fluid")).color(0xFF427676);
    public static FluidAttributes.Builder air = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/air_still"), new ResourceLocation(ElementTimes.MODID, "fluid/air_fluid")).color(0xFF949494);
    public static FluidAttributes.Builder Al2O3_Na3AlF6 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/al2o3_na3alf6_still"), new ResourceLocation(ElementTimes.MODID, "fluid/al2o3_na3alf6_fluid")).color(0xFF949494);
    public static FluidAttributes.Builder rareGases = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/air_still"), new ResourceLocation(ElementTimes.MODID, "fluid/air_fluid")).color(0xFF949494).density(-1);
    public static FluidAttributes.Builder co2 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_still"), new ResourceLocation(ElementTimes.MODID, "fluid/ethylene_fluid")).color(0xFF5B5B5B);
    public static FluidAttributes.Builder hno3 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/hno3_still"), new ResourceLocation(ElementTimes.MODID, "fluid/hno3_fluid")).color(0xFF96603B);
    public static FluidAttributes.Builder nongHNO3 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/no2_still"), new ResourceLocation(ElementTimes.MODID, "fluid/no2_still")).color(0xFF9F3F3F);
    public static FluidAttributes.Builder xiHNO3 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/no_still"), new ResourceLocation(ElementTimes.MODID, "fluid/no_fluid")).color(0xFF967474);
    public static FluidAttributes.Builder no = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/no_still"), new ResourceLocation(ElementTimes.MODID, "fluid/no_fluid")).color(0xFF967474).density(-5);
    public static FluidAttributes.Builder no2 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/no2_still"), new ResourceLocation(ElementTimes.MODID, "fluid/no2_fluid")).color(0xFF9F3F3F);
    public static FluidAttributes.Builder o3 = Attributes.builder(new ResourceLocation(ElementTimes.MODID, "fluid/o3_still"), new ResourceLocation(ElementTimes.MODID, "fluid/o3_fluid")).color(0xFF6F6F92);

    public static class Attributes extends FluidAttributes {

        public Attributes(Builder builder, Fluid fluid) {
            super(builder, fluid);
        }

        public static Builder builder(ResourceLocation stillTexture, ResourceLocation flowingTexture) {
            return new Builder(stillTexture, flowingTexture, Attributes::new) {};
        }
    }
}
