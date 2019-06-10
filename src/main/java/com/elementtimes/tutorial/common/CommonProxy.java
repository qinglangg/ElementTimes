package com.elementtimes.tutorial.common;

import com.elementtimes.tutorial.annotation.register.ElementRegister;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesRecipe;
import com.elementtimes.tutorial.plugin.slashblade.BladeElementknife;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

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
        new ElementtimesGUI().init();
        ElementtimesRecipe.init(event);
    }

    public void postInit(FMLPostInitializationEvent event) {
        ElementRegister.invokeMethod();
    }
}
