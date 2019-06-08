package com.elementtimes.tutorial.common;

import com.elementtimes.tutorial.annotation.processor.ElementRegister;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesRecipe;
import com.elementtimes.tutorial.plugin.slashblade.BladeElementknife;
import com.elementtimes.tutorial.world.gen.WorldGenElementTimesOres;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

/**
 * 服务器端 Proxy
 * @author KSGFK
 */
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent event) {
        ElementRegister.init();
        String flammPfeil = "flammpfeil.slashblade";
        if (Loader.isModLoaded(flammPfeil)) {
            SlashBlade.InitEventBus.register(new BladeElementknife());
        }
    }

    public void init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new WorldGenElementTimesOres(), 0);
        new ElementtimesGUI().init();
        ElementtimesRecipe.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        ElementRegister.invokeMethod();
    }
}
