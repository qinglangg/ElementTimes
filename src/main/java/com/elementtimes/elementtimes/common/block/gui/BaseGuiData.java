package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.BaseContainer;
import com.elementtimes.elementcore.api.gui.IGuiData;
import com.elementtimes.elementcore.api.gui.ItemSlot;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.tileentity.lifecycle.BaseTeRecipeLifecycle;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.block.Block;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.inventory.container.Slot;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.items.SlotItemHandler;



public abstract class BaseGuiData<T extends BaseTileEntity> implements IGuiData<T> {

    private final Block mBlock;

    public BaseGuiData(Block block) {
        mBlock = block;
    }

    @Override
    public float getProcess(T te) {
        return te.getEngine()
                .findLifecycle(BaseTeRecipeLifecycle.class)
                .map(BaseTeRecipeLifecycle::getProcess)
                .orElse(0f);
    }

    @Override
    public Slot createSlot(T te, ItemSlot slot) {
        return new SlotItemHandler(te.getItemHandler(), slot.index, slot.x, slot.y);
    }

    @Override
    public ITextComponent getDisplayName() {
        return mBlock.getNameTextComponent();
    }

    public Block getBlock() {
        return mBlock;
    }

    public static <T extends BaseTileEntity> Container createContainer(Class<?> typeKey, int id, T bte, BaseGuiData<T> data, PlayerEntity player) {
        ContainerType<BaseContainer<T>> type = (ContainerType<BaseContainer<T>>) ElementTimes.CONTAINER.elements.generatedContainerTypes.get(typeKey);
        return new BaseContainer<>(type, id, bte, data, player);
    }
}
