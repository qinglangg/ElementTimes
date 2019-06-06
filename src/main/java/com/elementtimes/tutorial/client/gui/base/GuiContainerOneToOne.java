package com.elementtimes.tutorial.client.gui.base;

import com.elementtimes.tutorial.common.capability.impl.RfEnergy;
import com.elementtimes.tutorial.common.tileentity.BaseOneToOne;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import net.minecraft.client.resources.I18n;

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
        T tileEntity = machine.getMachine();
        // 能量条
        RfEnergy.EnergyProxy rfEnergy = tileEntity.getReadonlyEnergyProxy();
        int energy = rfEnergy.getEnergyStored();
        int maxEnergy = rfEnergy.getMaxEnergyStored();
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        int textureWidth = energy * 90 / maxEnergy;
        this.drawTexturedModalRect(offsetX + 43, offsetY + 55, 24, 156, textureWidth, 4);

        // 箭头
        int process = tileEntity.getEnergyProcessed();
        int unprocessed = tileEntity.getEnergyUnprocessed();
        int total = unprocessed + process;
        int arrowWidth = total == 0 ? 0 : 24 / total * process;
        this.drawTexturedModalRect(offsetX + 80, offsetY + 30, 0, 156, arrowWidth, 17);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        T tileEntity = machine.getMachine();
        String a = I18n.format(tileEntity.getBlockType().getLocalizedName());
        this.fontRenderer.drawString(a, 88 - this.fontRenderer.getStringWidth(a) / 2, 60, 0x404040);
    }
}
