package com.elementtimes.tutorial.interfaces.tileentity;

import com.elementtimes.tutorial.client.util.RenderObject;
import net.minecraft.item.ItemStack;

import java.util.List;

/**
 * @author KSGFK create in 2019/6/12
 */
public interface ITESRSupport {
    List<RenderObject> getRenderItems();

    void initCanRendItems();

    boolean setRender(int index, boolean isRender);

    boolean isRender(int index);

    void setRenderItemState(int index, ItemStack state);
}
