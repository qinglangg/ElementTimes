package com.elementtimes.tutorial.interfaces.tileentity;

import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.other.SideHandlerType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 为 GUI 提供交互元素
 * @author luqin2007
 */
public interface IGuiProvider {

    /**
     * 获取所有打开该机器gui的玩家，用于向他们同步信息
     * @return 所有打开该机器 gui 的玩家
     */
    Set<EntityPlayerMP> getOpenedPlayers();

    /**
     * 获取 GUI 类型，ElementtimesGui 根据此打开对应的 GUI
     * @return GUI 类型
     */
    ElementtimesGUI.Machines getGuiType();

    /**
     * 创建物品槽位。该方法将在 MachineTile 构造中调用
     * @return 物品槽位
     */
    @Nonnull
    default Slot[] createSlots() { return new Slot[0]; };

    /**
     * 创建流体槽位
     * type -> slot -> [x, y, w, h]
     * @return 流体
     */
    @Nonnull
    default Map<SideHandlerType, Int2ObjectMap<int[]>> createFluids() {
        return new HashMap<>(0);
    }

    /**
     * 获取物品槽位。该方法将会在 GUI 类种调用
     * @return 物品槽位
     */
    Slot[] getSlots();

    /**
     * @return 流体位置。type -> slot -> [x, y, w, h]
     */
    Map<SideHandlerType, Int2ObjectMap<int[]>> getFluids();
}
