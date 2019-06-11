package com.elementtimes.tutorial.annotation.processor;

import com.elementtimes.tutorial.annotation.ModFluid;
import com.elementtimes.tutorial.annotation.other.ModInfo;
import com.elementtimes.tutorial.annotation.util.ReflectUtil;
import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.Fluid;

import java.lang.reflect.AnnotatedElement;
import java.util.*;
import java.util.function.Function;

/**
 * 用于加载流体
 * @author luqin2007
 */
public class ModFluidLoader {

    public static List<Fluid> HAS_BUCKET = new LinkedList<>();
    public static Map<Fluid, CreativeTabs> FLUID_TAB = new HashMap<>();
    public static Map<Fluid, Function<Fluid, Block>> FLUID_BLOCK = new HashMap<>();
    public static Map<Fluid, String> FLUID_BLOCK_STATE = new HashMap<>();
    public static List<Fluid> FLUID_RESOURCES = new LinkedList<>();

    public static void getFluids(Map<Class, ArrayList<AnnotatedElement>> elements, List<Fluid> into) {
        elements.get(ModFluid.class).forEach(element -> buildFluid(element, into));
    }

    private static void buildFluid(AnnotatedElement element, List<Fluid> into) {
        ModFluid info = element.getAnnotation(ModFluid.class);
        Fluid fluid = (Fluid) ReflectUtil.getFromAnnotated(element, null).orElse(null);
        if (fluid == null) {
            ResourceLocation overlay = info.overlayResource().isEmpty() ? null : new ResourceLocation(ModInfo.MODID, info.overlayResource());
            fluid = new Fluid(info.name(), new ResourceLocation(ModInfo.MODID, info.stillResource()), new ResourceLocation(ModInfo.MODID, info.flowingResource()), overlay, info.color());
        }

        String name = ReflectUtil.getName(element).orElse("").toLowerCase();

        initFluid(fluid, info, name);
        initFluidBlock(fluid, element, name);

        into.add(fluid);
    }

    private static void initFluid(Fluid fluid, ModFluid info, String name) {
        if (info.bucket()) {
            HAS_BUCKET.add(fluid);
        }
        FLUID_TAB.put(fluid, info.creativeTab().tab);
    }

    private static void initFluidBlock(Fluid fluid, AnnotatedElement element, String name) {
        ModFluid.FluidBlock fbInfo = element.getAnnotation(ModFluid.FluidBlock.class);
        if (fbInfo != null) {
            Function<Fluid, Block> fluidBlock = null;
            if (!fbInfo.className().isEmpty()) {
                Optional<Object> o = ReflectUtil.create(fbInfo.className(), new Object[]{fluid});
                Block block = (Block) o.filter(obj -> obj instanceof Block).orElse(null);
                decorateFluidBlock(fluid, block, fbInfo, name);
                if (block != null) {
                    fluidBlock = (f) -> block;
                }
            }

            if (fluidBlock == null) {
                fluidBlock = f -> {
                    Block block = fbInfo.type().create(f);
                    decorateFluidBlock(f, block, fbInfo, name);
                    return block;
                };
            }


            if (!fbInfo.resource().isEmpty()) {
                FLUID_BLOCK_STATE.put(fluid, fbInfo.resource());
            }

            if (fbInfo.loadTexture()) {
                FLUID_RESOURCES.add(fluid);
            }

            FLUID_BLOCK.put(fluid, fluidBlock);
        }
    }

    private static void decorateFluidBlock(Fluid fluid, Block fluidBlock, ModFluid.FluidBlock fbInfo, String name) {
        if (fluidBlock != null) {
            String registerName = fbInfo.registerName().isEmpty() ? name : fbInfo.registerName();
            if (fluidBlock.getRegistryName() == null) {
                fluidBlock.setRegistryName(ModInfo.MODID, registerName);
            }
            registerName = fluidBlock.getRegistryName().getResourcePath();
            fluidBlock.setCreativeTab(fbInfo.creativeTab().tab);

            String unlocalizedName = fbInfo.unlocalizedName().isEmpty() ? registerName.toLowerCase() : fbInfo.unlocalizedName();
            if ("tile.null".equals(fluidBlock.getUnlocalizedName())) {
                fluidBlock.setUnlocalizedName(ModInfo.MODID + "." + unlocalizedName);
            }
        }
    }
}
