package com.elementtimes.tutorial.client.gui.base;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.tileentity.base.TileMachine;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

public class GuiMachineContainer<T extends TileMachine> extends GuiContainer {

    protected ContainerMachine<T> machine;
    protected ResourceLocation texture;

    GuiMachineContainer(ContainerMachine<T> inventorySlotsIn, String texture) {
        super(inventorySlotsIn);
        machine = inventorySlotsIn;
        xSize = 176;
        ySize = 156;
        this.texture = new ResourceLocation(Elementtimes.MODID, texture);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        updateData();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    public void onGuiClosed() {

    }

    protected void updateData() {}
}
