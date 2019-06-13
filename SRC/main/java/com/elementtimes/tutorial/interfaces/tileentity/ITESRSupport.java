package com.elementtimes.tutorial.interfaces.tileentity;

import net.minecraft.item.ItemStack;

/**
 * @author KSGFK create in 2019/6/12
 */
public interface ITESRSupport {
    Iterable<ItemStack> getRenderItems();

    void initCanRendItems();

    boolean addRenderItem(Enum<?> itemEnum);

    boolean removeRenderItem(Enum<?> itemEnum);
}
