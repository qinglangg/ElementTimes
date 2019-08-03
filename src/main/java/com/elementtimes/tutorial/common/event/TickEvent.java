package com.elementtimes.tutorial.common.event;

import com.elementtimes.tutorial.common.storage.PLNetworkStorage;
import com.elementtimes.tutorial.other.pipeline.PLElement;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.LinkedList;
import java.util.function.Predicate;

/**
 * 每 tick 运算的事件
 * @author luqin2007
 */
public class TickEvent {

    private static TickEvent sPipelineEvent = null;
    public static LinkedList<Predicate<net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent>> tickTasks = new LinkedList<>();

    public static TickEvent getInstance() {
        if (sPipelineEvent == null) {
            sPipelineEvent = new TickEvent();
        }
        return sPipelineEvent;
    }

    public LinkedList<PLElement> elements = new LinkedList<>();

    @SubscribeEvent
    public void onTick(net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent event) {
        if (event.side == Side.SERVER) {
            if (event.phase == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.START) {
                for (PLElement element : elements) {
                    element.tickStart(event.world);
                }
                for (Predicate<net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent> task : tickTasks) {
                    if (task.test(event)) {
                        tickTasks.remove(task);
                    }
                }
            } else if (event.phase == net.minecraftforge.fml.common.gameevent.TickEvent.Phase.END) {
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
