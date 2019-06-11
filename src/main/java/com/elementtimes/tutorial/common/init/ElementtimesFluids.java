package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.annotation.ModFluid;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

/**
 * 流体注册
 * @author luqin2007
 */
public class ElementtimesFluids {

    @ModFluid
    @ModFluid.FluidBlock
    public static Fluid naCl = new Fluid("elementtimes.nacl",
            new ResourceLocation(ElementTimes.MODID, "fluid/nacl_still"),
            new ResourceLocation(ElementTimes.MODID, "fluid/nacl_fluid"), 0xFF719595);
}
