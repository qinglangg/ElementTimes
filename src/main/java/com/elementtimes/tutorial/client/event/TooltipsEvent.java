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

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(Side.CLIENT)
public class TooltipsEvent {
    @SubscribeEvent
    public static void onTooltips(ItemTooltipEvent event) {
        final ItemStack itemStack = event.getItemStack();
        final List<String> lines = event.getToolTip();
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.IRON_ORE)) {
            lines.add(new TextComponentString("FeO").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("SiO2");
            lines.add("CaCO3");
        }
        else if (itemStack.getItem() == Item.getItemFromBlock(Blocks.GLOWSTONE)) {
            lines.add(new TextComponentString("CaF2").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("SiO2");
            lines.add("CaCO3");
        }
    }
}
