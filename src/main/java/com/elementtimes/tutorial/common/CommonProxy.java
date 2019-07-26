package com.elementtimes.tutorial.common;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.annotation.AnnotationInitializer;
import com.elementtimes.tutorial.common.block.Pipeline;
import com.elementtimes.tutorial.common.event.PipelineEvent;
import com.elementtimes.tutorial.common.event.RecipeRemove;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesRecipe;
import com.elementtimes.tutorial.plugin.slashblade.BladeElementknife;
import com.elementtimes.tutorial.test.ComponentHandler;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * 服务器端 Proxy
 *
 * @author KSGFK
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        // 管道
        MinecraftForge.EVENT_BUS.register(PipelineEvent.getInstance());
        Pipeline.ALL_TYPES.add(Pipeline.TYPE_ITEM);
        Pipeline.ALL_TYPES.add(Pipeline.TYPE_ITEM_IN);
        Pipeline.ALL_TYPES.add(Pipeline.TYPE_ITEM_OUT);
        Pipeline.ALL_TYPES.add(Pipeline.TYPE_FLUID);
        Pipeline.ALL_TYPES.add(Pipeline.TYPE_FLUID_IN);
        Pipeline.ALL_TYPES.add(Pipeline.TYPE_FLUID_OUT);
        Pipeline.ALL_TYPES.add(Pipeline.TYPE_ENERGY);
        // 注解
        AnnotationInitializer.onPreInit(event, ElementTimes.MODID, "com.elementtimes.tutorial");
        if (ElementTimes.DEBUG) {
            MinecraftForge.EVENT_BUS.register(new ComponentHandler());
        }
        String flammPfeil = "flammpfeil.slashblade";
        if (Loader.isModLoaded(flammPfeil)) {
            SlashBlade.InitEventBus.register(new BladeElementknife());
        }
    }

    public void init(FMLInitializationEvent event) {
        AnnotationInitializer.onInit(event);
        new ElementtimesGUI().init();
        ElementtimesRecipe.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        AnnotationInitializer.onPostInit(event);
         new RecipeRemove();
    }
}
