package com.elementtimes.tutorial.interfaces.tileentity;

import com.elementtimes.tutorial.client.gui.base.GuiMachineContainer;
import com.elementtimes.tutorial.other.SideHandlerType;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.function.Function;

/**
 * 为 GUI 提供交互元素
 * @author luqin2007
 */
public interface IGuiProvider {

    /**
     * 创建物品槽位。该方法将在 MachineTile 构造中调用
     * @return 物品槽位
     */
    @Nonnull
    default Slot[] createSlots() { return new Slot[0]; };

    /**
     * 创建按钮。该方法将在 MachineTile 构造中调用
     * @return 按钮
     */
    @Nonnull
    default GuiButton[] createButton() { return new GuiButton[0]; }

    /**
     * 获取物品槽位。该方法将会在 GUI 类种调用
     * @return 物品槽位
     */
    Slot[] getSlots();

    /**
     * 获取按钮。该方法将会在 GUI 类种调用
     * @return 按钮
     */
    GuiButton[] getButtons();

    /**
     * 按钮响应
     */
    default void actionPerformed(GuiButton button, GuiMachineContainer guiContainer) {}

    /**
     * 模板。只有一个槽位，位于中间
     */
    Function<ITileItemHandler, Slot[]> SLOT_ONE = provider -> new Slot[] {
            new SlotItemHandler(provider.getItemHandler(SideHandlerType.INPUT), 0, 80, 30)
    };

    /**
     * 模板。一个输入一个输出
     */
    Function<ITileItemHandler, Slot[]> SLOT_ONE_TO_ONE = provider -> new Slot[] {
            new SlotItemHandler(provider.getItemHandler(SideHandlerType.INPUT), 0, 56, 30),
            new SlotItemHandler(provider.getItemHandler(SideHandlerType.OUTPUT), 0, 110, 30)
    };
}
