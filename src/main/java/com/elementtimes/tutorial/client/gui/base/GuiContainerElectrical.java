package com.elementtimes.tutorial.client.gui.base;

import com.elementtimes.tutorial.common.tileentity.BaseOneToOne;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;

/**
 * 对所有只有一个输入和一个输出的机器的 GUI 抽象
 *
 * @author luqin2007
 */
public class GuiContainerElectrical<T extends BaseOneToOne> extends GuiMachineContainer<T> {

    private final int[] process, energy;

    public GuiContainerElectrical(ContainerMachine<T> inventorySlotsIn) {
        this(inventorySlotsIn, "textures/gui/5.png",
                43, 55, 24, 156, 90, 4,
                80, 30, 0, 156, 24, 17);
    }

    public GuiContainerElectrical(ContainerMachine<T> inventorySlotsIn, String texture,
                                  int xProcess, int yProcess, int uProcess, int vProcess, int wProcess, int hProcess,
                                  int xEnergy, int yEnergy, int uEnergy, int vEnergy, int wEnergy, int hEnergy) {
        super(inventorySlotsIn, texture);
        process = new int[] {xProcess, yProcess, uProcess, vProcess, wProcess, hProcess};
        energy = new int[] {xEnergy, yEnergy, uEnergy, vEnergy, wEnergy, hEnergy};
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        short energyStored = machine.getEnergyStored();
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

        // 能量条
        int textureWidth = process[4] * energyStored / Short.MAX_VALUE;
        this.drawTexturedModalRect(offsetX + process[0], offsetY + process[1], process[2], process[3], textureWidth, process[5]);

        // 箭头
        short process = machine.getEnergyProcessed();
        int arrowWidth = energy[4] * process / Short.MAX_VALUE;
        this.drawTexturedModalRect(offsetX + energy[0], offsetY + energy[1], energy[2], energy[3], arrowWidth, energy[5]);
    }
}
