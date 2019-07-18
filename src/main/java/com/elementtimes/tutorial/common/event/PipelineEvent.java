package com.elementtimes.tutorial.common.event;

import com.elementtimes.tutorial.common.storage.PLNetworkStorage;
import com.elementtimes.tutorial.other.pipeline.PLElement;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.LinkedList;

public class PipelineEvent {

    private static PipelineEvent sPipelineEvent = null;

    public static PipelineEvent getInstance() {
        if (sPipelineEvent == null) {
            sPipelineEvent = new PipelineEvent();
        }
        return sPipelineEvent;
    }

    public LinkedList<PLElement> elements = new LinkedList<>();

    @SubscribeEvent
    public void onTick(TickEvent.WorldTickEvent event) {
        if (event.side == Side.SERVER) {
            if (event.phase == TickEvent.Phase.START) {
                for (PLElement element : elements) {
                    element.tickStart(event.world);
                }
            } else if (event.phase == TickEvent.Phase.END) {
                for (PLElement element : elements) {
                    element.tickEnd();
                }
            }
            PLNetworkStorage.load(event.world).markDirty();
        }
    }

    public NBTTagList toNbt() {
        NBTTagList list = new NBTTagList();
        for (PLElement plElement : elements) {
            list.appendTag(plElement.serializeNBT());
        }
        return list;
    }

    public void fromNbt(NBTTagList nbt) {
        elements.clear();
        for (NBTBase nbtBase : nbt) {
            NBTTagCompound plElement = (NBTTagCompound) nbtBase;
            PLElement e = new PLElement();
            e.deserializeNBT(plElement);
            elements.add(e);
        }
    }
}
