package com.elementtimes.tutorial.client.gui.base;

import com.elementtimes.tutorial.common.tileentity.BaseOneToOne;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;

/**
 * 对所有只有一个输入和一个输出的机器的 GUI 抽象
 *
 * @author luqin2007
 */
public class GuiContainerOneToOne<T extends BaseOneToOne> extends GuiMachineContainer<T> {

    public GuiContainerOneToOne(ContainerMachine<T> inventorySlotsIn) {
        super(inventorySlotsIn, "textures/gui/5.png");
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        // 能量条
        short energy = machine.getEnergyStored();
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        int textureWidth = 90 * energy / Short.MAX_VALUE;
        this.drawTexturedModalRect(offsetX + 43, offsetY + 55, 24, 156, textureWidth, 4);

        // 箭头
        short process = machine.getEnergyProcessed();
        int arrowWidth = 24 * process / Short.MAX_VALUE;
        this.drawTexturedModalRect(offsetX + 80, offsetY + 30, 0, 156, arrowWidth, 17);
    }
}
