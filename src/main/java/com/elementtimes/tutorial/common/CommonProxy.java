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
        event.getRegistry().register(ElementtimesBlocks.Sulfurore);
        event.getRegistry().register(ElementtimesBlocks.Calciumfluoride);
        event.getRegistry().register(ElementtimesBlocks.Platinumblock);
        event.getRegistry().register(ElementtimesBlocks.Platinumore);
        event.getRegistry().register(ElementtimesBlocks.Diamondblock);
        event.getRegistry().register(ElementtimesBlocks.Corncrop);
        event.getRegistry().register(ElementtimesBlocks.Corncropup);

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
        registerItemBlock(ElementtimesBlocks.Sulfurore, event);
        registerItemBlock(ElementtimesBlocks.Calciumfluoride, event);
        registerItemBlock(ElementtimesBlocks.Platinumblock, event);
        registerItemBlock(ElementtimesBlocks.Platinumore, event);
        registerItemBlock(ElementtimesBlocks.Diamondblock, event);
        registerItemBlock(ElementtimesBlocks.Corncrop, event);
        registerItemBlock(ElementtimesBlocks.Corncropup, event);
        
        event.getRegistry().register(ElementtimesItems.Sucrosecharcoal);
        event.getRegistry().register(ElementtimesItems.Sulfurpowder);
        event.getRegistry().register(ElementtimesItems.Sulfurorepowder);
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
        event.getRegistry().register(ElementtimesItems.PlatinumSleeveBoots);
        event.getRegistry().register(ElementtimesItems.PlatinumSleeveHelmet);
        event.getRegistry().register(ElementtimesItems.PlatinumSleeveLeggings);
        event.getRegistry().register(ElementtimesItems.PlatinumSleeveBody);
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
        event.getRegistry().register(ElementtimesItems.Twiningsuncompound);
        event.getRegistry().register(ElementtimesItems.Intermediaterubbingsuncompound);
        event.getRegistry().register(ElementtimesItems.Largerubbingsuncompound);
        event.getRegistry().register(ElementtimesItems.Photoelement);
        event.getRegistry().register(ElementtimesItems.Quartzpowder);
        event.getRegistry().register(ElementtimesItems.Bighammer);
        event.getRegistry().register(ElementtimesItems.Smallhammer);
        event.getRegistry().register(ElementtimesItems.Cornbroth);
        event.getRegistry().register(ElementtimesItems.Money);
        event.getRegistry().register(ElementtimesItems.Platinumingot);
        event.getRegistry().register(ElementtimesItems.Diamondingot);
        event.getRegistry().register(ElementtimesItems.Platinumorepowder);	
        event.getRegistry().register(ElementtimesItems.Hydrogen);	
        event.getRegistry().register(ElementtimesItems.Steam);	
        event.getRegistry().register(ElementtimesItems.Sulfitesolution);	
        event.getRegistry().register(ElementtimesItems.Sulphuricacid);	
        event.getRegistry().register(ElementtimesItems.Sodiumsulfitesolution);	
        event.getRegistry().register(ElementtimesItems.Elementhoe);	
        event.getRegistry().register(ElementtimesItems.Elementshovel);	
        event.getRegistry().register(ElementtimesItems.Platinumsword);	
        event.getRegistry().register(ElementtimesItems.Platinumhoe);	
        event.getRegistry().register(ElementtimesItems.Platinumshovel);	
        event.getRegistry().register(ElementtimesItems.Platinumaxe);	
        event.getRegistry().register(ElementtimesItems.Platinumpick);	
    }

    public void registerItemBlock(Block block, RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(block).setRegistryName(block.getRegistryName()));
    }
}
