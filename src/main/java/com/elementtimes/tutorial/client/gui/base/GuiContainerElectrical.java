package com.elementtimes.tutorial.client.gui.base;

import com.elementtimes.tutorial.inventory.base.ContainerMachine;

/**
 * 对所有只有一个输入和一个输出的机器的 GUI 抽象
 *
 * @author luqin2007
 */
public class GuiContainerElectrical extends GuiMachineContainer {

    private final int[][] processPos, energyPos;

    public GuiContainerElectrical(ContainerMachine inventorySlotsIn, String texture, int textOffsetY,
                                  int xProcess, int yProcess, int xEnergy, int yEnergy) {
        this(inventorySlotsIn, texture, textOffsetY,
                new int[][] { new int[] {xProcess, yProcess, 0, inventorySlotsIn.getHeight(), 24, 17} },
                new int[][] { new int[] {xEnergy, yEnergy, 24, inventorySlotsIn.getHeight(), 90, 4} });
    }

    public GuiContainerElectrical(ContainerMachine inventorySlotsIn, String texture, int textOffsetY,
                                  int xProcess, int yProcess, int uProcess, int vProcess, int wProcess, int hProcess,
                                  int xEnergy, int yEnergy, int uEnergy, int vEnergy, int wEnergy, int hEnergy) {
        this(inventorySlotsIn, texture, textOffsetY,
                new int[][] { new int[] {xProcess, yProcess, uProcess, vProcess, wProcess, hProcess} },
                new int[][] { new int[] {xEnergy, yEnergy, uEnergy, vEnergy, wEnergy, hEnergy} });
    }

    public GuiContainerElectrical(ContainerMachine inventorySlotsIn, String texture, int textOffsetY,
                                  int[][] processXYUVWH,
                                  int xEnergy, int yEnergy, int uEnergy, int vEnergy, int wEnergy, int hEnergy) {
        this(inventorySlotsIn, texture, textOffsetY, processXYUVWH,
                new int[][] { new int[] {xEnergy, yEnergy, uEnergy, vEnergy, wEnergy, hEnergy} });
    }

    public GuiContainerElectrical(ContainerMachine inventorySlotsIn, String texture, int textOffsetY,
                                  int xProcess, int yProcess, int uProcess, int vProcess, int wProcess, int hProcess,
                                  int[][] energyXYUVWH) {
        this(inventorySlotsIn, texture, textOffsetY, new int[][] { new int[] {xProcess, yProcess, uProcess, vProcess, wProcess, hProcess} }, energyXYUVWH);
    }

    public GuiContainerElectrical(ContainerMachine inventorySlotsIn, String texture, int textOffsetY, int[][] processXYUVWH, int[][] energyXYUVWH) {
        super(inventorySlotsIn, "textures/gui/" + texture + ".png", textOffsetY);
        processPos = processXYUVWH;
        energyPos = energyXYUVWH;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        short energyStored = machine.getEnergyStored();
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

        // 能量条
        for (int[] energy : energyPos) {
            int textureWidth = energy[4] * energyStored / Short.MAX_VALUE;
            this.drawTexturedModalRect(offsetX + energy[0], offsetY + energy[1], energy[2], energy[3], textureWidth, energy[5]);
        }
        // 箭头
        short processValue = machine.getEnergyProcessed();
        for (int[] process : processPos) {
            int arrowWidth = process[4] * processValue / Short.MAX_VALUE;
            this.drawTexturedModalRect(offsetX + process[0], offsetY + process[1], process[2], process[3], arrowWidth, process[5]);
        }
    }
}
