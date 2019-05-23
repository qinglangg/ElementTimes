package com.elementtimes.tutorial.client.gui.base;

import com.elementtimes.tutorial.common.tileentity.base.TileOneToOne;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import com.elementtimes.tutorial.util.RedStoneEnergy;
import net.minecraft.client.resources.I18n;
import net.minecraftforge.energy.CapabilityEnergy;

public class GuiContainerOneToOne<T extends TileOneToOne> extends GuiMachineContainer<T> {
    private int energy = 0;
    private int maxEnergy = 0;

    public GuiContainerOneToOne(ContainerMachine<T> inventorySlotsIn) {
        super(inventorySlotsIn, "textures/gui/5.png");
        this.xSize = 176;
        this.ySize = 156;
        this.machine = inventorySlotsIn;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        super.drawGuiContainerBackgroundLayer(partialTicks, mouseX, mouseY);
        RedStoneEnergy rfEnergy = (RedStoneEnergy) machine.tileEntity.getCapability(CapabilityEnergy.ENERGY, null);
        energy = rfEnergy.getEnergyStored();
        maxEnergy = rfEnergy.getMaxEnergyStored();
        int process = machine.tileEntity.getSchedule();
        int perTime = machine.getTileEntity().getPerTime();

        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        int textureWidth = energy * 90 / maxEnergy;
        this.drawTexturedModalRect(offsetX + 43, offsetY + 55, 24, 156, textureWidth, 4);//白色条子

        int arrowWidth = process * 24 / perTime;
        if (process >= perTime) {
            this.drawTexturedModalRect(offsetX + 80, offsetY + 30, 0, 156, 0, 17);//箭头
        } else {
            this.drawTexturedModalRect(offsetX + 80, offsetY + 30, 0, 156, arrowWidth, 17);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String a = I18n.format(machine.tileEntity.getBlockType().getUnlocalizedName());
        this.fontRenderer.drawString(a, 88 - this.fontRenderer.getStringWidth(a) / 2, 60, 0x404040);
    }
}
