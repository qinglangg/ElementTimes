package com.elementtimes.tutorial.client;

import com.elementtimes.tutorial.common.CommonProxy;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ClientProxy extends CommonProxy {
    @SubscribeEvent
    public void registerItemModels(ModelRegistryEvent event) {
        registerItemModel(ElementtimesBlocks.Woodessence);

        registerItemModel(ElementtimesBlocks.Leafessence);

        registerItemModel(ElementtimesBlocks.Stoneblock);

        registerItemModel(ElementtimesBlocks.Cement);

        registerItemModel(ElementtimesBlocks.Cementandsteelbarmixture);
        
        registerItemModel(ElementtimesBlocks.Copperore);
        
        registerItemModel(ElementtimesBlocks.Copperbillet);

        registerItemModel(ElementtimesItems.Saplingessence);

        registerItemModel(ElementtimesItems.Fiveelements);
        
        registerItemModel(ElementtimesItems.Endelement);

        registerItemModel(ElementtimesItems.Soilelement);

        registerItemModel(ElementtimesItems.Woodelement);

        registerItemModel(ElementtimesItems.Waterelement);

        registerItemModel(ElementtimesItems.Fireelement);

        registerItemModel(ElementtimesItems.Goldelement);

        registerItemModel(ElementtimesItems.Cropessence);

        registerItemModel(ElementtimesItems.Elementsword);

        registerItemModel(ElementtimesItems.Elementaxe);

        registerItemModel(ElementtimesItems.Elementpickaxe);

        registerItemModel(ElementtimesItems.Starchpowder);

        registerItemModel(ElementtimesItems.Amylum);
        
        registerItemModel(ElementtimesItems.Puremeat);
        
        registerItemModel(ElementtimesItems.Corn);
        
        registerItemModel(ElementtimesItems.Bakedcorn);

        registerItemModel(ElementtimesItems.Stoneingot);

        registerItemModel(ElementtimesItems.Steelingot);

        registerItemModel(ElementtimesItems.Calciumcarbonate);

        registerItemModel(ElementtimesItems.Calciumoxide);

        registerItemModel(ElementtimesItems.Ingotcolumn);

        registerItemModel(ElementtimesItems.Concrete);
        
        registerItemModel(ElementtimesItems.Copper);
        
        registerItemModel(ElementtimesItems.Largerubbingsuncompound);
        registerItemModel(ElementtimesItems.Intermediaterubbingsuncompound);
        registerItemModel(ElementtimesItems.Twiningsuncompound);
        registerItemModel(ElementtimesItems.Photoelement);
        registerItemModel(ElementtimesItems.Quartzpowder);
        registerItemModel(ElementtimesItems.Greenstonepowder);
        registerItemModel(ElementtimesItems.Redstonepowder);
        registerItemModel(ElementtimesItems.Bluestonepowder);
        registerItemModel(ElementtimesItems.Ironpower);
        registerItemModel(ElementtimesItems.Goldpowder);
        registerItemModel(ElementtimesItems.Diamondpowder);
        registerItemModel(ElementtimesItems.Copperpowder);
        registerItemModel(ElementtimesItems.Coalpowder);
        registerItemModel(ElementtimesItems.Spanner);
        
        
        registerItemModel(ElementtimesItems.ElementSleeveHelmet);
        registerItemModel(ElementtimesItems.ElementSleeveBody);
        registerItemModel(ElementtimesItems.ElementSleeveLeggings);
        registerItemModel(ElementtimesItems.ElementSleeveBoots);

        registerItemModel(ElementtimesBlocks.ElementGenerater);
        
    }

    public void registerItemModel(Block block) {
        registerItemModel(Item.getItemFromBlock(block));
    }

    public void registerItemModel(Item item) {
        ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
    }
}
