package com.elementtimes.tutorial.other.pipeline;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;
import java.util.LinkedList;
import java.util.List;

/**
 * 世界存储网络
 * @author luqin2007
 */
public class PLStorage extends WorldSavedData {

    private static PLStorage sStorage = null;
    private static final String WS_PIPELINE = "elementtimes_pl_network";
    private static final String WS_PIPELINE_ELEMENTS = "elementtimes_pl_network_elements";
    private static final String WS_PIPELINE_ADD = "elementtimes_pl_network_add";
    private static final String WS_PIPELINE_REMOVE = "elementtimes_pl_network_remove";

    public static final List<PLElement> ELEMENTS = new LinkedList<>();
    static final List<PLElement> ELEMENTS_ADD = new LinkedList<>();
    static final List<PLElement> ELEMENTS_REMOVE = new LinkedList<>();

    @Nonnull
    public static PLStorage load(World world) {
        if (sStorage == null && world != null && world.getMapStorage() != null) {
            sStorage = (PLStorage) world.getMapStorage().getOrLoadData(PLStorage.class, WS_PIPELINE);
            if (sStorage == null) {
                sStorage = new PLStorage(WS_PIPELINE);
                world.getMapStorage().setData(WS_PIPELINE, sStorage);
            }
        }
        //noinspection ConstantConditions
        return sStorage;
    }

    public PLStorage(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound nbt) {
        final NBTTagCompound compoundTag = nbt.getCompoundTag(WS_PIPELINE);
        ELEMENTS.clear();
        for (NBTBase nbtBase : (NBTTagList) compoundTag.getTag(WS_PIPELINE_ELEMENTS)) {
            NBTTagCompound plElement = (NBTTagCompound) nbtBase;
            PLElement e = new PLElement();
            e.deserializeNBT(plElement);
            ELEMENTS.add(e);
        }
        ELEMENTS_ADD.clear();
        for (NBTBase nbtBase : (NBTTagList) compoundTag.getTag(WS_PIPELINE_ADD)) {
            NBTTagCompound plElement = (NBTTagCompound) nbtBase;
            PLElement e = new PLElement();
            e.deserializeNBT(plElement);
            ELEMENTS_ADD.add(e);
        }
        ELEMENTS_REMOVE.clear();
        for (NBTBase nbtBase : (NBTTagList) compoundTag.getTag(WS_PIPELINE_REMOVE)) {
            NBTTagCompound plElement = (NBTTagCompound) nbtBase;
            PLElement e = new PLElement();
            e.deserializeNBT(plElement);
            ELEMENTS_REMOVE.add(e);
        }
    }

    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        NBTTagList listElements = new NBTTagList();
        for (PLElement plElement : ELEMENTS) {
            listElements.appendTag(plElement.serializeNBT());
        }
        NBTTagList listAdd = new NBTTagList();
        for (PLElement plElement : ELEMENTS_ADD) {
            listAdd.appendTag(plElement.serializeNBT());
        }
        NBTTagList listRemove = new NBTTagList();
        for (PLElement plElement : ELEMENTS_REMOVE) {
            listRemove.appendTag(plElement.serializeNBT());
        }

        NBTTagCompound e = new NBTTagCompound();
        e.setTag(WS_PIPELINE_ELEMENTS, listElements);
        e.setTag(WS_PIPELINE_ADD, listAdd);
        e.setTag(WS_PIPELINE_REMOVE, listRemove);
        compound.setTag(WS_PIPELINE, e);
        return compound;
    }
}
