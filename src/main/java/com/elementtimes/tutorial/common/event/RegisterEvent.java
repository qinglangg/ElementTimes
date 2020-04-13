package com.elementtimes.tutorial.common.event;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesTabs;
import net.minecraft.block.Block;
import net.minecraft.block.BlockSlab;
import net.minecraft.item.Item;
import net.minecraft.item.ItemDoor;
import net.minecraft.item.ItemSlab;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.GameData;

@Mod.EventBusSubscriber
public class RegisterEvent {

    @SubscribeEvent
    public static void item(RegistryEvent.Register<Item> event) {
        ItemDoor door = (ItemDoor) new ItemDoor(ElementtimesBlocks.bambooDoor).setUnlocalizedName("elementtimes.bamboodoor")
                .setRegistryName(ElementtimesBlocks.bambooDoor.getRegistryName())
                .setCreativeTab(ElementtimesTabs.Agriculture);
        ItemSlab slab = (ItemSlab) new ItemSlab(ElementtimesBlocks.bambooSlab, (BlockSlab) ElementtimesBlocks.bambooSlab, (BlockSlab) ElementtimesBlocks.bambooSlabDouble)
                .setUnlocalizedName("elementtimes.bambooslab")
                .setRegistryName(ElementTimes.MODID, "bambooslab")
                .setCreativeTab(ElementtimesTabs.Agriculture);

        event.getRegistry().register(door);
        event.getRegistry().register(slab);
        GameData.getBlockItemMap().put(ElementtimesBlocks.bambooDoor, door);
    }

    @SubscribeEvent
    public static void block(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(ElementtimesBlocks.bambooDoor.setUnlocalizedName("elementtimes.bamboodoor").setRegistryName(ElementTimes.MODID, "bamboodoor"));
        event.getRegistry().register(ElementtimesBlocks.bambooSlab.setUnlocalizedName("elementtimes.bambooslab").setRegistryName(ElementTimes.MODID, "bambooslab"));
        event.getRegistry().register(ElementtimesBlocks.bambooSlabDouble.setUnlocalizedName("elementtimes.bambooslab").setRegistryName(ElementTimes.MODID, "bambooslabdouble"));
    }
}
