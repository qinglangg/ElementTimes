package com.elementtimes.tutorial.common;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class CommonProxy {
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(ElementtimesBlocks.Woodessence);
        event.getRegistry().register(ElementtimesBlocks.Leafessence);
        event.getRegistry().register(ElementtimesBlocks.Stoneblock);
        event.getRegistry().register(ElementtimesBlocks.Cement);
        event.getRegistry().register(ElementtimesBlocks.Cementandsteelbarmixture);
        event.getRegistry().register(ElementtimesBlocks.ElementGenerater);
        event.getRegistry().register(ElementtimesBlocks.Copperore);
        event.getRegistry().register(ElementtimesBlocks.Copperbillet);
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) {
        registerItemBlock(ElementtimesBlocks.Woodessence, event);
        registerItemBlock(ElementtimesBlocks.Leafessence, event);
        registerItemBlock(ElementtimesBlocks.Stoneblock, event);
        registerItemBlock(ElementtimesBlocks.Cement, event);
        registerItemBlock(ElementtimesBlocks.Cementandsteelbarmixture, event);
        registerItemBlock(ElementtimesBlocks.ElementGenerater, event);
        registerItemBlock(ElementtimesBlocks.Copperore, event);
        registerItemBlock(ElementtimesBlocks.Copperbillet, event);

        event.getRegistry().register(ElementtimesItems.Fiveelements);
        event.getRegistry().register(ElementtimesItems.Endelement);
        event.getRegistry().register(ElementtimesItems.Soilelement);
        event.getRegistry().register(ElementtimesItems.Woodelement);
        event.getRegistry().register(ElementtimesItems.Waterelement);
        event.getRegistry().register(ElementtimesItems.Fireelement);
        event.getRegistry().register(ElementtimesItems.Goldelement);
        event.getRegistry().register(ElementtimesItems.Saplingessence);
        event.getRegistry().register(ElementtimesItems.Cropessence);
        event.getRegistry().register(ElementtimesItems.Elementsword);
        event.getRegistry().register(ElementtimesItems.Elementaxe);
        event.getRegistry().register(ElementtimesItems.Elementpickaxe);
        event.getRegistry().register(ElementtimesItems.Starchpowder);
        event.getRegistry().register(ElementtimesItems.Amylum);
        event.getRegistry().register(ElementtimesItems.Puremeat);
        event.getRegistry().register(ElementtimesItems.Corn);
        event.getRegistry().register(ElementtimesItems.Bakedcorn);
        event.getRegistry().register(ElementtimesItems.Stoneingot);
        event.getRegistry().register(ElementtimesItems.Steelingot);
        event.getRegistry().register(ElementtimesItems.Calciumcarbonate);
        event.getRegistry().register(ElementtimesItems.Calciumoxide);
        event.getRegistry().register(ElementtimesItems.Concrete);
        event.getRegistry().register(ElementtimesItems.ElementSleeveHelmet);
        event.getRegistry().register(ElementtimesItems.ElementSleeveBody);
        event.getRegistry().register(ElementtimesItems.ElementSleeveLeggings);
        event.getRegistry().register(ElementtimesItems.ElementSleeveBoots);
        event.getRegistry().register(ElementtimesItems.Copper);
        event.getRegistry().register(ElementtimesItems.Ingotcolumn);
        event.getRegistry().register(ElementtimesItems.Greenstonepowder);
        event.getRegistry().register(ElementtimesItems.Redstonepowder);
        event.getRegistry().register(ElementtimesItems.Bluestonepowder);
        event.getRegistry().register(ElementtimesItems.Ironpower);
        event.getRegistry().register(ElementtimesItems.Goldpowder);
        event.getRegistry().register(ElementtimesItems.Coalpowder);


        event.getRegistry().register(ElementtimesItems.Diamondpowder);
        event.getRegistry().register(ElementtimesItems.Copperpowder);
        event.getRegistry().register(ElementtimesItems.Spanner);
    }

    public void registerItemBlock(Block block, RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
}
