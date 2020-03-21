package com.elementtimes.tutorial.common.event;

import com.elementtimes.tutorial.ElementTimes;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
public class NetEvent {

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public static void onConfigChanged(ConfigChangedEvent event) {
        if (ElementTimes.MODID.equals(event.getModID())) {
            ConfigManager.sync(ElementTimes.MODID, Config.Type.INSTANCE);
        }
    }
}
