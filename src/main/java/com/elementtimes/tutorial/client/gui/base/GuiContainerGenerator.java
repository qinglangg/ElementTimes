package com.elementtimes.tutorial.client.gui.base;

import com.elementtimes.tutorial.common.capability.impl.RfEnergy;
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
    private int energy;
    private int maxEnergy;
    private int powerGening;
    private int maxPowerGen;

    public GuiContainerGenerator(ContainerMachine<T> inventorySlotsIn) {
        super(inventorySlotsIn, "textures/gui/0.png");
        this.xSize = 176;
        this.ySize = 156;
        this.machine = inventorySlotsIn;
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
        String title = I18n.format(machine.getMachine().getBlockType().getUnlocalizedName() + ".name") +
                 "  " + powerGening + "/" + maxPowerGen;
        this.fontRenderer.drawString(title, (this.xSize - this.fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
        String a = energy + "/" + maxEnergy;
        this.fontRenderer.drawString(a, 88 - this.fontRenderer.getStringWidth(a) / 2, 60, 0x404040);
    }

    @Override
    protected void updateData() {
        T tileEntity = machine.getMachine();
        RfEnergy.EnergyProxy rfEnergy = tileEntity.getReadonlyEnergyProxy();
        energy = rfEnergy.getEnergyStored();
        maxEnergy = rfEnergy.getMaxEnergyStored();
        powerGening = tileEntity.getEnergyProcessed();
        maxPowerGen = tileEntity.getEnergyProcessed() + powerGening;
    }
}
