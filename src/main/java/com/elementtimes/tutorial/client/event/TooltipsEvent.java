package com.elementtimes.tutorial.client.event;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
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
@Mod.EventBusSubscriber(value = Side.CLIENT, modid = ElementTimes.MODID)
public class TooltipsEvent {
    @SubscribeEvent
    public static void onTooltips(ItemTooltipEvent event) {
        final ItemStack itemStack = event.getItemStack();
        final List<String> lines = event.getToolTip();
        
        //铁矿石
        if (itemStack.getItem() == Item.getItemFromBlock(Blocks.IRON_ORE)) {
            lines.add(new TextComponentString("Fe2O3").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("SiO2");
            lines.add("CaCO3");
            lines.add(new TextComponentString("用热还原法在固体反应器中使用还原剂(煤炭)进行还原为铁锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)).getFormattedText());
            lines.add(new TextComponentString("还可以用热还原法在固液反应器中使用还原剂(CO,H2)进行还原为铁锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)).getFormattedText());
        }
        else if (itemStack.getItem() == ElementtimesItems.Fe2O3) {
            lines.add(new TextComponentString("Fe2O3").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("SiO2");
            lines.add("CaCO3");
            lines.add(new TextComponentString("用热还原法在固体反应器中使用还原剂(煤炭)进行还原为铁锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)).getFormattedText());
            lines.add(new TextComponentString("还可以用热还原法在固液反应器中使用还原剂(CO,H2)进行还原为铁锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)).getFormattedText());
        }
        
        //铜矿石
        else if (itemStack.getItem() == Item.getItemFromBlock(ElementtimesBlocks.copperOre)) {
            lines.add(new TextComponentString("CuO").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("SiO2");
            lines.add("CaCO3");
            lines.add(new TextComponentString("用热还原法在固体反应器中使用还原剂(煤炭)进行还原为铁锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)).getFormattedText());
            lines.add(new TextComponentString("还可以用热还原法在固液反应器中使用还原剂(CO,H2)进行还原为铁锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)).getFormattedText());
        }
        else if (itemStack.getItem() == ElementtimesItems.copperPowder) {
            lines.add(new TextComponentString("CuO").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("SiO2");
            lines.add("CaCO3");
            lines.add(new TextComponentString("用热还原法在固体反应器中使用还原剂(煤炭)进行还原为铜锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)).getFormattedText());
            lines.add(new TextComponentString("还可以用热还原法在固液反应器中使用还原剂(CO,H2)进行还原为铜锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)).getFormattedText());
        }
        
        
        //CaF2
        else if (itemStack.getItem() == Item.getItemFromBlock(Blocks.GLOWSTONE)) {
            lines.add(new TextComponentString("CaF2").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("SiO2");
            lines.add("CaCO3");
        }
        else if (itemStack.getItem() == Item.getItemFromBlock(ElementtimesBlocks.calciumFluoride)) {
            lines.add(new TextComponentString("CaF2").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add("SiO2");
            lines.add("CaCO3");
        }
        
        
        
        else if (itemStack.getItem() == ElementtimesItems.steelIngot) {
            lines.add(new TextComponentString("Fe").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
            lines.add(new TextComponentString("C").setStyle(new Style().setColor(TextFormatting.BLUE)).getFormattedText());
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
