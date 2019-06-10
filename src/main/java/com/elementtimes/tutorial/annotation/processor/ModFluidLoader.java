package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.annotation.ModFluid;
import com.elementtimes.tutorial.annotation.util.ReflectUtil;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.lang.reflect.AnnotatedElement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ModFluidLoader {

    public static boolean sBucket = false;

    public static void getFluids(Map<Class, ArrayList<AnnotatedElement>> elements, List<Fluid> into) {
        elements.get(ModFluid.class).forEach(element -> buildFluid(element, into));
    }

    private static void buildFluid(AnnotatedElement element, List<Fluid> into) {
        ModFluid info = element.getAnnotation(ModFluid.class);
        Fluid fluid = (Fluid) ReflectUtil.getFromAnnotated(element, null).orElse(null);
        if (fluid == null) {
            ResourceLocation overlay = info.overlayResource().isEmpty() ? null : new ResourceLocation(Elementtimes.MODID, info.overlayResource());
            fluid = new Fluid(info.name(), new ResourceLocation(Elementtimes.MODID, info.stillResource()), new ResourceLocation(Elementtimes.MODID, info.flowingResource()), overlay, info.color());
        }
        fluid.setUnlocalizedName(info.unlocalizedName());
        sBucket = sBucket || info.bucket();

        initBlock(fluid, element);
    }

    private static void initBlock(Fluid fluid, AnnotatedElement annotatedElement) {
        ModFluid.FluidBlock fbInfo = annotatedElement.getAnnotation(ModFluid.FluidBlock.class);
        if (fbInfo != null) {
            
        }
    }
}
