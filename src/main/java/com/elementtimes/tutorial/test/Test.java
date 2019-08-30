package com.elementtimes.tutorial.test;

import java.util.List;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber
public class Test {
    @SubscribeEvent
    public static void onTooltips(ItemTooltipEvent event) {
        final ItemStack itemStack = event.getItemStack();
        final List<String> lines = event.getToolTip();
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.IRON_ORE)) {
            lines.add(new TextComponentString("FeO").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("SiO2");
            lines.add("CaCO3");
        }
        else if (itemStack.getItem() == Item.getItemFromBlock(ElementtimesBlocks.copperOre)) {
            lines.add(new TextComponentString("CuO").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("SiO2");
            lines.add("CaCO3");
        }
    
        else if (itemStack.getItem() == ElementtimesItems.steelIngot) {
            lines.add(new TextComponentString("Fe").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("C");
        }
    }
}
