package com.elementtimes.tutorial.client.event;

import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import com.elementtimes.tutorial.common.init.ElementtimesItems;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class TooltipsEvent {
    @SubscribeEvent
    public static void onTooltips(ItemTooltipEvent event) {
        final ItemStack itemStack = event.getItemStack();
        final List<String> lines = event.getToolTip();
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.IRON_ORE)) {
            lines.add(new TextComponentString("Fe2O3").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("SiO2");
            lines.add("CaCO3");
        }
        else if (itemStack.getItem() == Item.getItemFromBlock(Blocks.GLOWSTONE)) {
            lines.add(new TextComponentString("CaF2").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("SiO2");
            lines.add("CaCO3");
        }
        else if (itemStack.getItem() == ElementtimesItems.steelIngot) {
            lines.add(new TextComponentString("Fe").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("C");
        }
        else if (itemStack.getItem() == ElementtimesItems.slag) {
            lines.add("CaCO3");
            lines.add("SiO2");
        }
        else if (itemStack.getItem() == ElementtimesItems.Al2O3_Na3AlF6) {
        	lines.add(new TextComponentString("Al2O3").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
        	lines.add(new TextComponentString("Na3AlF6").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
        }
    }
}
