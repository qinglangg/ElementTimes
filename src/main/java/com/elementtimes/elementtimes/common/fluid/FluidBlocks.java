package com.elementtimes.elementtimes.common.fluid;

import com.elementtimes.elementcore.api.annotation.ModElement;
import net.minecraft.block.Block;
import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;

/**
 * 流体方块

 */
@ModElement
public class FluidBlocks {
    public static FlowingFluidBlock fluidSteel = new FlowingFluidBlock(() -> Sources.Steel, prop());
    public static FlowingFluidBlock fluidFe = new FlowingFluidBlock(() -> Sources.Fe, prop());
    public static FlowingFluidBlock fluidF2 = new FlowingFluidBlock(() -> Sources.F2, prop());
    public static FlowingFluidBlock fluidConcentratedHydrochloricAcid = new FlowingFluidBlock(() -> Sources.ConcentratedHydrochloricAcid, prop());
    public static FlowingFluidBlock fluidDiluteHydrochloricAcid = new FlowingFluidBlock(() -> Sources.DiluteHydrochloricAcid, prop());
    public static FlowingFluidBlock fluidHCl = new FlowingFluidBlock(() -> Sources.HCl, prop());
    public static FlowingFluidBlock fluidI2 = new FlowingFluidBlock(() -> Sources.I2, prop());
    public static FlowingFluidBlock fluidHI = new FlowingFluidBlock(() -> Sources.HI, prop());
    public static FlowingFluidBlock fluidBr2 = new FlowingFluidBlock(() -> Sources.Br2, prop());
    public static FlowingFluidBlock fluidHBr = new FlowingFluidBlock(() -> Sources.HBr, prop());
    public static FlowingFluidBlock fluidHClO = new FlowingFluidBlock(() -> Sources.HClO, prop());
    public static FlowingFluidBlock fluidH2SO4 = new FlowingFluidBlock(() -> Sources.H2SO4, prop());
    public static FlowingFluidBlock fluidH2SO3 = new FlowingFluidBlock(() -> Sources.H2SO3, prop());
    public static FlowingFluidBlock fluidSO3 = new FlowingFluidBlock(() -> Sources.SO3, prop());
    public static FlowingFluidBlock fluidSO2 = new FlowingFluidBlock(() -> Sources.SO2, prop());
    public static FlowingFluidBlock fluidFluoroaluminicAcid = new FlowingFluidBlock(() -> Sources.FluoroaluminicAcid, prop());
    public static FlowingFluidBlock fluidHF = new FlowingFluidBlock(() -> Sources.HF, prop());
    public static FlowingFluidBlock fluidNa = new FlowingFluidBlock(() -> Sources.Na, prop());
    public static FlowingFluidBlock fluidMg = new FlowingFluidBlock(() -> Sources.Mg, prop());
    public static FlowingFluidBlock fluidSiCl4 = new FlowingFluidBlock(() -> Sources.SiCl4, prop());
    public static FlowingFluidBlock fluidNaCl = new FlowingFluidBlock(() -> Sources.NaCl, prop());
    public static FlowingFluidBlock fluidMgCl2 = new FlowingFluidBlock(() -> Sources.MgCl2, prop());
    public static FlowingFluidBlock fluidAlCl3 = new FlowingFluidBlock(() -> Sources.AlCl3, prop());
    public static FlowingFluidBlock fluidNaClSolutionConcentrated = new FlowingFluidBlock(() -> Sources.NaClSolutionConcentrated, prop());
    public static FlowingFluidBlock fluidSeaWater = new SeaWaterBlock();
    public static FlowingFluidBlock fluidNaAlO2 = new FlowingFluidBlock(() -> Sources.NaAlO2, prop());
    public static FlowingFluidBlock fluidCu_NO3_2 = new FlowingFluidBlock(() -> Sources.Cu_no3_2, prop());
    public static FlowingFluidBlock fluidNaClSolutionDilute = new FlowingFluidBlock(() -> Sources.NaClSolutionDilute, prop());
    public static FlowingFluidBlock fluidMagnesiumChlorideSolution = new FlowingFluidBlock(() -> Sources.MagnesiumChlorideSolution , prop());
    public static FlowingFluidBlock fluidNH4Cl = new FlowingFluidBlock(() -> Sources.NH4Cl, prop());
    public static FlowingFluidBlock fluidNaOH = new FlowingFluidBlock(() -> Sources.NaOH, prop());
    public static FlowingFluidBlock fluidNaNO3 = new FlowingFluidBlock(() -> Sources.NaNO3, prop());
    public static FlowingFluidBlock fluidH = new FlowingFluidBlock(() -> Sources.H, prop());
    public static FlowingFluidBlock fluidSteam = new FlowingFluidBlock(() -> Sources.steam, prop());
    public static FlowingFluidBlock fluidWaterDistilled = new FlowingFluidBlock(() -> Sources.waterDistilled, prop());
    public static FlowingFluidBlock fluidButter = new FlowingFluidBlock(() -> Sources.butter, prop());
    public static FlowingFluidBlock fluidAcetylene = new FlowingFluidBlock(() -> Sources.acetylene, prop());
    public static FlowingFluidBlock fluidEthylene = new FlowingFluidBlock(() -> Sources.ethylene, prop());
    public static FlowingFluidBlock fluidCO = new FlowingFluidBlock(() -> Sources.CO, prop());
    public static FlowingFluidBlock fluidEthanol = new FlowingFluidBlock(() -> Sources.ethanol, prop());
    public static FlowingFluidBlock fluidCalciumHydroxide = new FlowingFluidBlock(() -> Sources.calciumHydroxide, prop());
    public static FlowingFluidBlock fluidCalmogastrin = new FlowingFluidBlock(() -> Sources.calmogastrin, prop());
    public static FlowingFluidBlock fluidMethane = new FlowingFluidBlock(() -> Sources.methane, prop());
    public static FlowingFluidBlock fluidEthane = new FlowingFluidBlock(() -> Sources.ethane, prop());
    public static FlowingFluidBlock fluidChlorine = new FlowingFluidBlock(() -> Sources.chlorine, prop());
    public static FlowingFluidBlock fluidConcentratedSulfuricAcid = new FlowingFluidBlock(() -> Sources.concentratedSulfuricAcid, prop());
    public static FlowingFluidBlock fluidDiluteSulfuricAcid = new FlowingFluidBlock(() -> Sources.diluteSulfuricAcid, prop());
    public static FlowingFluidBlock fluidNitrogen = new FlowingFluidBlock(() -> Sources.nitrogen, prop());
    public static FlowingFluidBlock fluidAl = new FlowingFluidBlock(() -> Sources.Al, prop());
    public static FlowingFluidBlock fluidNa3AlF6 = new FlowingFluidBlock(() -> Sources.Na3AlF6, prop());
    public static FlowingFluidBlock fluidAmmonia = new FlowingFluidBlock(() -> Sources.ammonia, prop());
    public static FlowingFluidBlock fluidOxygen = new FlowingFluidBlock(() -> Sources.oxygen, prop());
    public static FlowingFluidBlock fluidAir = new FlowingFluidBlock(() -> Sources.air, prop());
    public static FlowingFluidBlock fluidAl2O3_Na3AlF6 = new FlowingFluidBlock(() -> Sources.Al2O3_Na3AlF6, prop());
    public static FlowingFluidBlock fluidRareGases = new FlowingFluidBlock(() -> Sources.rareGases, prop());
    public static FlowingFluidBlock fluidCO2 = new FlowingFluidBlock(() -> Sources.CO2, prop());
    public static FlowingFluidBlock fluidHNO3 = new FlowingFluidBlock(() -> Sources.HNO3, prop());
    public static FlowingFluidBlock fluidHNO3Concentrated = new FlowingFluidBlock(() -> Sources.HNO3Concentrated, prop());
    public static FlowingFluidBlock fluidHNO3Dilute = new FlowingFluidBlock(() -> Sources.HNO3Dilute, prop());
    public static FlowingFluidBlock fluidNO = new FlowingFluidBlock(() -> Sources.NO, prop());
    public static FlowingFluidBlock fluidNO2 = new FlowingFluidBlock(() -> Sources.NO2, prop());
    public static FlowingFluidBlock fluidO3 = new FlowingFluidBlock(() -> Sources.O3, prop());
    
    static Block.Properties prop() {
        return Block.Properties.create(Material.WATER).doesNotBlockMovement().hardnessAndResistance(100.0F).noDrops();
    }
}
