package com.elementtimes.elementtimes.common.fluid;

import com.elementtimes.elementtimes.common.init.Groups;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

/**
 * 流体桶

 */
public class Buckets {
    public static ChemicalBucket steel = new ChemicalBucket(() -> Sources.Steel, 0);
    public static ChemicalBucket Fe = new ChemicalBucket(() -> Sources.Fe, 0);
    public static ChemicalBucket F2 = new ChemicalBucket(() -> Sources.F2, 0);
    public static ChemicalBucket ConcentratedHydrochloricAcid = new ChemicalBucket(() -> Sources.ConcentratedHydrochloricAcid, 0);
    public static ChemicalBucket DiluteHydrochloricAcid = new ChemicalBucket(() -> Sources.DiluteHydrochloricAcid, 0);
    public static ChemicalBucket I2 = new ChemicalBucket(() -> Sources.I2, 0);
    public static ChemicalBucket HI = new ChemicalBucket(() -> Sources.HI, 0);
    public static ChemicalBucket Br2 = new ChemicalBucket(() -> Sources.Br2, 0);
    public static ChemicalBucket HBr = new ChemicalBucket(() -> Sources.HBr, 0);
    public static ChemicalBucket HClO = new ChemicalBucket(() -> Sources.HClO, 0);
    public static ChemicalBucket h2so4 = new ChemicalBucket(() -> Sources.H2SO4, 0);
    public static ChemicalBucket h2so3 = new ChemicalBucket(() -> Sources.H2SO3, 0);
    public static ChemicalBucket so3 = new ChemicalBucket(() -> Sources.SO3, 0);
    public static ChemicalBucket so2 = new ChemicalBucket(() -> Sources.SO2, 0);
    public static ChemicalBucket FluoroaluminicAcid = new ChemicalBucket(() -> Sources.FluoroaluminicAcid, 0);
    public static ChemicalBucket hf = new ChemicalBucket(() -> Sources.HF, 0);
    public static ChemicalBucket Na = new ChemicalBucket(() -> Sources.Na, 0);
    public static ChemicalBucket Mg = new ChemicalBucket(() -> Sources.Mg, 0);
    public static ChemicalBucket Sicl4 = new ChemicalBucket(() -> Sources.SiCl4, 0);
    public static ChemicalBucket HCl = new ChemicalBucket(() -> Sources.HCl, 0);
    public static ChemicalBucket NaCl = new ChemicalBucket(() -> Sources.NaCl, 0);
    public static ChemicalBucket MgCl2 = new ChemicalBucket(() -> Sources.MgCl2, 0);
    public static ChemicalBucket AlCl3s = new ChemicalBucket(() -> Sources.AlCl3, 0);
    public static ChemicalBucket NaClSolutionConcentrated = new ChemicalBucket(() -> Sources.NaClSolutionConcentrated, 0);
    public static ChemicalBucket seawater = new ChemicalBucket(() -> Sources.seaWater, 0);
    public static ChemicalBucket NaAlO2 = new ChemicalBucket(() -> Sources.NaAlO2, 0);
    public static ChemicalBucket Cu_no3_2 = new ChemicalBucket(() -> Sources.Cu_no3_2, 0);
    public static ChemicalBucket NaClSolutionDilute = new ChemicalBucket(() -> Sources.NaClSolutionDilute, 0);
    public static ChemicalBucket MagnesiumChlorideSolution  = new ChemicalBucket(() -> Sources.MagnesiumChlorideSolution , 0);
    public static ChemicalBucket NH4Cl = new ChemicalBucket(() -> Sources.NH4Cl, 0);
    public static ChemicalBucket Naoh = new ChemicalBucket(() -> Sources.NaOH, 0);
    public static ChemicalBucket NaNo3 = new ChemicalBucket(() -> Sources.NaNO3, 0);
    public static ChemicalBucket H = new ChemicalBucket(() -> Sources.H, 1600);
    public static ChemicalBucket steam = new ChemicalBucket(() -> Sources.steam, 0);
    public static ChemicalBucket waterDistilled = new ChemicalBucket(() -> Sources.waterDistilled, 0);
    public static ChemicalBucket butter = new ChemicalBucket(() -> Sources.butter, 0);
    public static ChemicalBucket acetylene = new ChemicalBucket(() -> Sources.acetylene, 5000);
    public static ChemicalBucket ethylene = new ChemicalBucket(() -> Sources.ethylene, 7000);
    public static ChemicalBucket co = new ChemicalBucket(() -> Sources.CO, 800);
    public static ChemicalBucket ethanol = new ChemicalBucket(() -> Sources.ethanol, 0);
    public static ChemicalBucket calciumHydroxide = new ChemicalBucket(() -> Sources.calciumHydroxide, 0);
    public static ChemicalBucket calmogastrin = new ChemicalBucket(() -> Sources.calmogastrin, 0);
    public static ChemicalBucket methane = new ChemicalBucket(() -> Sources.methane, 0);
    public static ChemicalBucket ethane = new ChemicalBucket(() -> Sources.ethane, 0);
    public static ChemicalBucket chlorine = new ChemicalBucket(() -> Sources.chlorine, 0);
    public static ChemicalBucket concentratedSulfuricAcid = new ChemicalBucket(() -> Sources.concentratedSulfuricAcid, 0);
    public static ChemicalBucket diluteSulfuricAcid = new ChemicalBucket(() -> Sources.diluteSulfuricAcid, 0);
    public static ChemicalBucket nitrogen = new ChemicalBucket(() -> Sources.nitrogen, 0);
    public static ChemicalBucket Al = new ChemicalBucket(() -> Sources.Al, 0);
    public static ChemicalBucket Na3AlF6 = new ChemicalBucket(() -> Sources.Na3AlF6, 0);
    public static ChemicalBucket ammonia = new ChemicalBucket(() -> Sources.ammonia, 0);
    public static ChemicalBucket oxygen = new ChemicalBucket(() -> Sources.oxygen, 0);
    public static ChemicalBucket air = new ChemicalBucket(() -> Sources.air, 0);
    public static ChemicalBucket Al2O3_Na3AlF6 = new ChemicalBucket(() -> Sources.Al2O3_Na3AlF6, 0);
    public static ChemicalBucket rareGases = new ChemicalBucket(() -> Sources.rareGases, 0);
    public static ChemicalBucket co2 = new ChemicalBucket(() -> Sources.CO2, 0);
    public static ChemicalBucket hno3 = new ChemicalBucket(() -> Sources.HNO3, 0);
    public static ChemicalBucket nongHNO3 = new ChemicalBucket(() -> Sources.HNO3Concentrated, 0);
    public static ChemicalBucket xiHNO3 = new ChemicalBucket(() -> Sources.HNO3Dilute, 0);
    public static ChemicalBucket no = new ChemicalBucket(() -> Sources.NO, 0);
    public static ChemicalBucket no2 = new ChemicalBucket(() -> Sources.NO2, 0);
    public static ChemicalBucket o3 = new ChemicalBucket(() -> Sources.O3, 0);

    public static class ChemicalBucket extends BucketItem {

        private final int mBurnTime;

        public ChemicalBucket(Supplier<? extends Fluid> supplier, int burnTime) {
            super(supplier, new Properties().group(Groups.Chemical));
            mBurnTime = burnTime;
        }

        @Override
        public int getBurnTime(ItemStack itemStack) {
            return mBurnTime;
        }
    }
}
