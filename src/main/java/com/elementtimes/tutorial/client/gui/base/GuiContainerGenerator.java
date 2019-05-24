package com.elementtimes.tutorial.client.gui.base;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.tileentity.base.TileGenerator;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import com.elementtimes.tutorial.util.RedStoneEnergy;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 发电机客户端GUI
 *
 * @author KSGFK create in 2019/2/17
 */
@SideOnly(Side.CLIENT)
public class GuiContainerGenerator<T extends TileGenerator> extends GuiMachineContainer<T> {
    private int energy;
    private int maxEnergy;
    private int powerGening;
    private int maxPowerGen;

    public GuiContainerGenerator(ContainerMachine<T> inventorySlotsIn) {
        super(inventorySlotsIn, "textures/gui/0.png");
        this.xSize = 176;
        this.ySize = 156;
        this.machine = inventorySlotsIn;

        RedStoneEnergy rfEnergy = (RedStoneEnergy) machine.getTileEntity().getCapability(CapabilityEnergy.ENERGY, null);
        maxEnergy = rfEnergy.getMaxEnergyStored();
        maxPowerGen = machine.getTileEntity().getMaxPowerGen();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        double textureWidth = energy * 89.0 / maxEnergy;
        this.drawTexturedModalRect(offsetX + 43, offsetY + 55, 0, 156, (int) textureWidth, 4);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String title = powerGening + "/" + maxPowerGen;
        this.fontRenderer.drawString(title, (this.xSize - this.fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
        String a = energy + "/" + maxEnergy;
        this.fontRenderer.drawString(a, 88 - this.fontRenderer.getStringWidth(a) / 2, 60, 0x404040);
    }

    @Override
    protected void updateData() {
        RedStoneEnergy rfEnergy = (RedStoneEnergy) machine.getTileEntity().getCapability(CapabilityEnergy.ENERGY, null);
        energy = rfEnergy.getEnergyStored();
        maxEnergy = rfEnergy.getMaxEnergyStored();
        powerGening = machine.getTileEntity().getPowerGening();
        maxPowerGen = machine.getTileEntity().getMaxPowerGen();
    }
}
