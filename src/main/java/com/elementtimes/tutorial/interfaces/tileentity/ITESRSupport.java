package com.elementtimes.tutorial.interfaces.tileentity;

import com.elementtimes.tutorial.other.RenderObject;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.util.INBTSerializable;

import javax.annotation.Nullable;

/**
 * @author KSGFK create in 2019/6/12
 */
public interface ITESRSupport extends INBTSerializable<NBTTagCompound> {

    String BIND_NBT_TESR_TE = "_nbt_tesr_te_";
    String BIND_NBT_TESR_TE_ITEMS = "_nbt_tesr_te_items_";
    String BIND_NBT_TESR_TE_PROPERTIES = "_nbt_tesr_te_properties_";

    NonNullList<RenderObject> getRenderItems();

    @Nullable
    default NBTTagCompound getRenderProperties() {
        return null;
    }

    void setRenderProperties(NBTTagCompound properties);

    void markRenderClient();

    default void receiveRenderMessage(NBTTagCompound nbt) {
        NBTTagCompound nbtTe = nbt.getCompoundTag(BIND_NBT_TESR_TE);
        NBTTagList list = (NBTTagList) nbtTe.getTag(BIND_NBT_TESR_TE_ITEMS);
        NonNullList<RenderObject> renderItems = getRenderItems();
        renderItems.clear();
        for (int i = 0; i < list.tagCount(); i++) {
            renderItems.add(i, RenderObject.create(list.getCompoundTagAt(i)));
        }
        if (nbtTe.hasKey(BIND_NBT_TESR_TE_PROPERTIES)) {
            setRenderProperties(nbtTe.getCompoundTag(BIND_NBT_TESR_TE_PROPERTIES));
        }
    }

    default NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound nbtTESR = new NBTTagCompound();
        NBTTagList items = new NBTTagList();
        for (RenderObject item : getRenderItems()) {
            items.appendTag(item.serializeNBT());
        }
        nbtTESR.setTag(BIND_NBT_TESR_TE_ITEMS, items);
        NBTTagCompound properties = getRenderProperties();
        if (properties != null) {
            nbtTESR.setTag(BIND_NBT_TESR_TE_PROPERTIES, properties);
        }
        nbt.setTag(BIND_NBT_TESR_TE, nbtTESR);
        return nbt;
    }

    default void readFromNBT(NBTTagCompound nbt) {
        receiveRenderMessage(nbt);
    }

    default int registerRender(RenderObject renderObject) {
        NonNullList<RenderObject> renderItems = getRenderItems();
        for (int i = 0; i < renderItems.size(); i++) {
            if (RenderObject.EMPTY.equals(renderItems.get(i))) {
                renderItems.set(i, renderObject);
                return i;
            }
        }
        int index = renderItems.size();
        renderItems.add(renderObject);
        return index;
    }

    default void setRender(int index, boolean isRender) {
        getRenderItems().get(index).setRender(isRender);
        markRenderClient();
    }

    default boolean isRender(int i) {
        return getRenderItems().get(i).isRender();
    }

    @Override
    default NBTTagCompound serializeNBT() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    default void deserializeNBT(NBTTagCompound nbt){
        readFromNBT(nbt);
    };
}
