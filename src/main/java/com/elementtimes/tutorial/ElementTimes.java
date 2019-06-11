package com.elementtimes.tutorial;

import com.elementtimes.tutorial.common.CommonProxy;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * TODO: 酿造台 1000000-10000(io)-16000(/item)
 * TODO: 整形机测试及 jei 兼容
 * TODO: 树苗精华改为block，并且可以种植。
 * TODO: 1.13.2 1.14 兼容
 * TODO: 1.7.10 兼容（优先度极低）
 * @author KSGFK
 */
@Mod(modid = ElementTimes.MODID, name = "Element Times", version = "@version@")
public class ElementTimes {
    public static final String MODID = "elementtimes";
    public static final boolean DEBUG = true;

    public ElementTimes() {
        FluidRegistry.enableUniversalBucket();
    }

    @SidedProxy(serverSide = "com.elementtimes.tutorial.common.CommonProxy", clientSide = "com.elementtimes.tutorial.client.ClientProxy")
    public static CommonProxy proxy;

    @Mod.Instance(ElementTimes.MODID)
    public static ElementTimes instance;

    private static Logger logger = LogManager.getLogger("ElementsTime");

    public static Logger getLogger() {
        return logger;
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        ElementRegister.invokeMethod();
    }

}
