package com.elementtimes.tutorial.annotation;

import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;

public class FluidUtil {

    public static void register(Fluid fluid) {
        if (FluidRegistry.isFluidRegistered(fluid)) {
            return;
        }
        FluidRegistry.registerFluid(fluid);
    }
}
