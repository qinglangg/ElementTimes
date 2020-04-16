package com.elementtimes.tutorial.client.event;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.block.BlockDoor;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod.EventBusSubscriber
@SideOnly(Side.CLIENT)
public class RegisterEvent {

    @SubscribeEvent
    public static void model(ModelRegistryEvent event) {
        ModelLoader.setCustomStateMapper(ElementtimesBlocks.bambooDoor, new StateMap.Builder().ignore(BlockDoor.POWERED).build());
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ElementtimesBlocks.bambooDoor), 0, new ModelResourceLocation(ElementtimesBlocks.bambooDoor.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(ElementtimesBlocks.bambooSlab), 0, new ModelResourceLocation(ElementtimesBlocks.bambooSlab.getRegistryName(), "inventory"));
    }
}
