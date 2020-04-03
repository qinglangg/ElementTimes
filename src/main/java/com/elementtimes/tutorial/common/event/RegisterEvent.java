package com.elementtimes.tutorial.common.event;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesTabs;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class RegisterEvent {

    @SubscribeEvent
    public static void item(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemDoor(ElementtimesBlocks.bambooDoor).setUnlocalizedName("elementtimes.bamboodoor").setRegistryName(ElementtimesBlocks.bambooDoor.getRegistryName()).setCreativeTab(ElementtimesTabs.Main));
    }

    @SubscribeEvent
    public static void block(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(ElementtimesBlocks.bambooDoor.setUnlocalizedName("elementtimes.bamboodoor").setRegistryName(ElementTimes.MODID, "bamboodoor"));
    }
}
