package com.elementtimes.tutorial.client.gui.base;

import com.elementtimes.tutorial.common.tileentity.BaseGenerator;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 对所有只有一个槽位的机器的抽象
 *
 * @author KSGFK create in 2019/2/17
 */
@SideOnly(Side.CLIENT)
public class GuiContainerGenerator<T extends BaseGenerator> extends GuiMachineContainer<T> {

    public GuiContainerGenerator(ContainerMachine<T> inventorySlotsIn) {
        super(inventorySlotsIn, "textures/gui/0.png");
        this.xSize = 176;
        this.ySize = 156;
        this.machine = inventorySlotsIn;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        short energy = machine.getEnergyStored();
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        double textureWidth = 89.0 * energy / Short.MAX_VALUE;
        this.drawTexturedModalRect(offsetX + 43, offsetY + 55, 0, 156, (int) textureWidth, 4);
    }
}
