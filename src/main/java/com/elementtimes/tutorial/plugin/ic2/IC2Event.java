package com.elementtimes.tutorial.plugin.ic2;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class IC2Event {

    @SubscribeEvent
    public void onItemUseToBlock(PlayerInteractEvent.RightClickBlock event) {
        if (IC2Support.isLoaded()) {
            ItemStack stack = event.getItemStack();
            Item item = stack.getItem();
            if (item instanceof ic2.core.item.tool.ItemTreetap) {
                IC2Support.useTreeTap(event, stack);
            } else if (item instanceof ic2.core.item.tool.ItemTreetapElectric) {
                IC2Support.useElectricTreeTap(event, stack);
            }
        }
    }
}
