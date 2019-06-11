package com.elementtimes.tutorial.test;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.capability.CapabilityLoader;
import com.elementtimes.tutorial.capability.component.CapabilityComponent;
import com.elementtimes.tutorial.capability.component.IComponentContain;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.List;

public class ComponentHandler {
    public ComponentHandler(){
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onAttachCapabilitiesEntity(AttachCapabilitiesEvent event)
    {
        if (event.getObject() instanceof ItemStack&& ((ItemStack) event.getObject()).getItem().equals(Items.COAL))
        {
            NBTTagCompound nbt = new NBTTagCompound();
            nbt.setFloat("石头",5f);
            nbt.setFloat("炭",5f);
            nbt.setFloat("灰尘",5f);
            ICapabilitySerializable<NBTTagCompound> provider = new CapabilityComponent.ProviderPlayer(nbt);
            event.addCapability(new ResourceLocation(ElementTimes.MODID + ":" + "component"), provider);
        }
    }
    @SubscribeEvent
    public void onToolTip(ItemTooltipEvent event)
    {
        if(event.getItemStack().hasCapability(CapabilityLoader.positionHistory,null)){
           IComponentContain componentContain = event.getItemStack().getCapability(CapabilityLoader.positionHistory,null);
           List<String> toolTip = event.getToolTip();
           toolTip.addAll(componentContain.getComponent().getComponents());
       }
    }
}
