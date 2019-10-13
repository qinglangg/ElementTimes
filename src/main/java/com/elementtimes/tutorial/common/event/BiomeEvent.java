package com.elementtimes.tutorial.common.event;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBiomes;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 群戏注册
 * @author luqin2007
 */
@Mod.EventBusSubscriber(modid = ElementTimes.MODID)
public class BiomeEvent {

    @SubscribeEvent
    public static void onRegister(RegistryEvent.Register<Biome> event) {
        event.getRegistry().register(ElementtimesBiomes.sea.setRegistryName(ElementTimes.MODID, "sea"));
    }
}
