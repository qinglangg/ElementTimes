package com.elementtimes.tutorial.common.event;

import com.elementtimes.tutorial.common.generator.OceanGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class OreEvent {

    private static final OceanGenerator GENERATOR_OCEAN = new OceanGenerator();

    @SubscribeEvent
    public static void onGenPost(OreGenEvent.Post event) {
        if (!event.getWorld().isRemote) {
            GENERATOR_OCEAN.generate(event.getWorld(), event.getRand(), event.getPos());
        }
    }
}
