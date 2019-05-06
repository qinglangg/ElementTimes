package com.elementtimes.tutorial.client.gui;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.inventory.ContainerPulverizer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * @author KSGFK create in 2019/5/6
 */
public class GuiContainerPul extends GuiContainer {
    private static final String TEXTURE_PATH = "textures/gui/5.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(Elementtimes.MODID, TEXTURE_PATH);

    private int energy;
    private int maxEnergy;
    private int process;
    private int perTime = 1;
    private ContainerPulverizer pul;

    public GuiContainerPul(ContainerPulverizer inventorySlotsIn) {
        super(inventorySlotsIn);
        this.xSize = 176;
        this.ySize = 156;
        this.pul = inventorySlotsIn;
        maxEnergy = pul.getTileEntity().getMaxEnergyStored(null);
        //perTime = pul.getTileEntity().getPerTime();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
        int textureWidth = energy * 90 / maxEnergy;
        this.drawTexturedModalRect(offsetX + 43, offsetY + 55, 24, 156, textureWidth, 4);//白色条子

        int arrowWidth = process * 24 / perTime;
        this.drawTexturedModalRect(offsetX + 80, offsetY + 30, 0, 156, arrowWidth, 17);//
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String a = energy + "/" + maxEnergy;
        this.fontRenderer.drawString(a, 88 - this.fontRenderer.getStringWidth(a) / 2, 60, 0x404040);
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public void setProcess(int process) {
        this.process = process;
    }
}
