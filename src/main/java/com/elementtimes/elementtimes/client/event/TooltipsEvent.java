package com.elementtimes.elementtimes.client.event;

import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.init.Items;
import com.elementtimes.elementtimes.common.init.blocks.Ore;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import java.util.List;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = ElementTimes.MODID)
public class TooltipsEvent {
    @SubscribeEvent
    public static void onTooltips(ItemTooltipEvent event) {
        final ItemStack itemStack = event.getItemStack();
        final List<ITextComponent> lines = event.getToolTip();

        Item item = itemStack.getItem();
        //铁矿石
        if (item == Blocks.IRON_ORE.asItem() || item == Items.ironPowder) {
            lines.add(new StringTextComponent("Fe2O3").setStyle(new Style().setColor(TextFormatting.BLUE)));
            lines.add(new StringTextComponent("SiO2"));
            lines.add(new StringTextComponent("CaCO3"));
            lines.add(new StringTextComponent("用热还原法在固体反应器中使用还原剂(煤炭)进行还原为铁锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
            lines.add(new StringTextComponent("还可以用热还原法在固液反应器中使用还原剂(CO,H2)进行还原为铁锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
        } else if (item == Items.Fe2O3) {
            lines.add(new StringTextComponent("Fe2O3").setStyle(new Style().setColor(TextFormatting.BLUE)));
            lines.add(new StringTextComponent("用热还原法在固体反应器中使用还原剂(煤炭)进行还原为铁锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
            lines.add(new StringTextComponent("还可以用热还原法在固液反应器中使用还原剂(CO,H2)进行还原为铁锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
        }

        //铜矿石
        else if (item == Ore.oreCopper.asItem() || item == Items.copperPowder) {
            lines.add(new StringTextComponent("CuO").setStyle(new Style().setColor(TextFormatting.BLUE)));
            lines.add(new StringTextComponent("SiO2"));
            lines.add(new StringTextComponent("CaCO3"));
            lines.add(new StringTextComponent("用热还原法在固体反应器中使用还原剂(煤炭)进行还原为铜锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
            lines.add(new StringTextComponent("还可以用热还原法在固液反应器中使用还原剂(CO,H2)进行还原为铜锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
        } else if (item == Items.CuO) {
            lines.add(new StringTextComponent("CuO").setStyle(new Style().setColor(TextFormatting.BLUE)));
            lines.add(new StringTextComponent("用热还原法在固体反应器中使用还原剂(煤炭)进行还原为铜锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
            lines.add(new StringTextComponent("还可以用热还原法在固液反应器中使用还原剂(CO,H2)进行还原为铜锭").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
        }

        //CaF2
        else if (item == Item.getItemFromBlock(Blocks.GLOWSTONE)) {
            lines.add(new StringTextComponent("CaF2").setStyle(new Style().setColor(TextFormatting.BLUE)));
            lines.add(new StringTextComponent("SiO2"));
            lines.add(new StringTextComponent("CaCO3"));
        } else if (item == Ore.oreCalciumFluoride.asItem()) {
            lines.add(new StringTextComponent("CaF2").setStyle(new Style().setColor(TextFormatting.BLUE)));
            lines.add(new StringTextComponent("SiO2"));
            lines.add(new StringTextComponent("CaCO3"));
        }

        else if (item == Items.diamondIngot) {
            lines.add(new StringTextComponent("C").setStyle(new Style().setColor(TextFormatting.BLUE)));
            lines.add(new StringTextComponent("先将物品栏中煤炭块拆解为单个物品，不是叠加状态，仅在一个物品时且左键放入酿造台。").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
            lines.add(new StringTextComponent("再在酿造台上面格子左键放入一个钻石块，再在下面左键放入1-3个煤炭块。即可获得金刚锭。").setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
        }
        else if (item == Items.steelIngot) {
            lines.add(new StringTextComponent("Fe").setStyle(new Style().setColor(TextFormatting.BLUE)));
            lines.add(new StringTextComponent("C").setStyle(new Style().setColor(TextFormatting.BLUE)));
        }
        else if (item == Items.slag) {
            lines.add(new StringTextComponent("CaCO3"));
            lines.add(new StringTextComponent("SiO2"));
        }
        else if (item == Items.Al2O3_Na3AlF6) {
        	lines.add(new StringTextComponent("Al2O3").setStyle(new Style().setColor(TextFormatting.BLUE)));
        	lines.add(new StringTextComponent("Na3AlF6").setStyle(new Style().setColor(TextFormatting.BLUE)));
        }
     }
   }
