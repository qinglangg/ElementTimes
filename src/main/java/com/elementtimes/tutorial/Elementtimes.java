package com.elementtimes.tutorial;

import com.elementtimes.tutorial.common.CommonProxy;
import com.elementtimes.tutorial.common.event.BreakBlockListener;
import com.elementtimes.tutorial.common.init.*;

import com.elementtimes.tutorial.common.slashblade.BladeElementknife;
import com.elementtimes.tutorial.util.PowderDictionary;
import com.elementtimes.tutorial.world.gen.WorldGenETOres;
import mods.flammpfeil.slashblade.SlashBlade;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(modid = Elementtimes.MODID, name = "Element Times", version = "@version@")
public class Elementtimes {
    public static final String MODID = "elementtimes";

    @SidedProxy(serverSide = "com.elementtimes.tutorial.common.CommonProxy", clientSide = "com.elementtimes.tutorial.client.ClientProxy")
    public static CommonProxy proxy;

    private static SimpleNetworkWrapper network;

    public static SimpleNetworkWrapper getNetwork() {
        return network;
    }

    @Mod.Instance(Elementtimes.MODID)
    public static Elementtimes instance;

    private static ElementtimesGUI gui = new ElementtimesGUI();

    public static ElementtimesGUI getGui() {
        return gui;
    }

    private static Logger logger = LogManager.getLogger("ElementsTime");

    public static Logger getLogger() {
        return logger;
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MinecraftForge.EVENT_BUS.register(proxy);
        MinecraftForge.EVENT_BUS.register(new BreakBlockListener());
        ElementtimesTile.init();
        if (Loader.isModLoaded("flammpfeil.slashblade")) {
            SlashBlade.InitEventBus.register(new BladeElementknife());
        }
    }

    @EventHandler
    public void Init(FMLInitializationEvent event) {
        GameRegistry.registerWorldGenerator(new WorldGenETOres(), 0);
        ElementtimesRecipes.init();
        network = NetworkRegistry.INSTANCE.newSimpleChannel(MODID);
        ElementtimesNetwork.init();
        gui.init();
        ElementtimesFuels.init();
        ElementtimesOreDict.Init();
        ElementtimesRecipe.Init(event);
        PowderDictionary.getInstance();
    }
}
