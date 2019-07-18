package com.elementtimes.tutorial.common.storage;

import com.elementtimes.tutorial.common.event.PipelineEvent;
import com.elementtimes.tutorial.other.pipeline.PLElement;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraft.world.storage.WorldSavedData;

import javax.annotation.Nonnull;

/**
 * 世界存储网络
 * @author luqin2007
 */
public class PLNetworkStorage extends WorldSavedData {

    private static PLNetworkStorage sStorage = null;
    private static final String WS_PIPELINE = "elementtimes_pl_network";

    @Nonnull
    public static PLNetworkStorage load(World world) {
        if (sStorage == null && world != null && world.getMapStorage() != null) {
            sStorage = (PLNetworkStorage) world.getMapStorage().getOrLoadData(PLNetworkStorage.class, WS_PIPELINE);
            if (sStorage == null) {
                sStorage = new PLNetworkStorage(WS_PIPELINE);
                world.getMapStorage().setData(WS_PIPELINE, sStorage);
            }
        }
        //noinspection ConstantConditions
        return sStorage;
    }

    public PLNetworkStorage(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(@Nonnull NBTTagCompound nbt) {
        NBTTagList list = (NBTTagList) nbt.getTag(WS_PIPELINE);
        PipelineEvent.getInstance().fromNbt(list);
    }

    @Override
    public NBTTagCompound writeToNBT(@Nonnull NBTTagCompound compound) {
        compound.setTag(WS_PIPELINE, PipelineEvent.getInstance().toNbt());
        return compound;
    }
}
