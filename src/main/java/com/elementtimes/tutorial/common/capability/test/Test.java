package com.elementtimes.tutorial.common.capability.test;

import com.elementtimes.tutorial.Elementtimes;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class Test {

    @SubscribeEvent
    public static void onAttachCapability(AttachCapabilitiesEvent<Entity> event) {
        event.addCapability(new ResourceLocation(Elementtimes.MODID, "test"), new CapabilityTest.Provider());
    }

    @SubscribeEvent
    public static void onPlayerJump(PlayerEvent.LivingJumpEvent event) {
        if (!event.getEntity().world.isRemote) {
            Entity entity = event.getEntity();
            if (entity.hasCapability(CapabilityTest.getMessageCapability(), entity.getHorizontalFacing())) {
                IMessage message = entity.getCapability(CapabilityTest.getMessageCapability(), entity.getHorizontalFacing());
                entity.sendMessage(new TextComponentString(message.getMessage()));
            }
        }
    }
}
