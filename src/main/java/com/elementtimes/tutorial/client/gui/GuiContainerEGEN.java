package com.elementtimes.tutorial.client.gui;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.inventory.ContainerElementGenerater;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 发电机客户端GUI
 *
 * @author KSGFK create in 2019/2/17
 */
@SideOnly(Side.CLIENT)
public class GuiContainerEGEN extends GuiContainer {
    private static final String TEXTURE_PATH = "textures/gui/0.png";
    private static final ResourceLocation TEXTURE = new ResourceLocation(Elementtimes.MODID, TEXTURE_PATH);

    private ContainerElementGenerater generater;
    private int energy;
    private int maxEnergy;
    private int powerGening;
    private int maxPowerGen;

    public GuiContainerEGEN(ContainerElementGenerater inventorySlotsIn) {
        super(inventorySlotsIn);
        this.xSize = 176;
        this.ySize = 156;
        this.generater = inventorySlotsIn;

        maxEnergy = generater.getTileEntity().getMaxEnergyStored(null);
        maxPowerGen = generater.getTileEntity().getMaxPowerGen();
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        updateData();
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);

        int textureWidth = energy * 89 / maxEnergy;
        this.drawTexturedModalRect(offsetX + 43, offsetY + 55, 0, 156, textureWidth, 4);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        updateData();
        String title = powerGening + "/" + maxPowerGen;
        this.fontRenderer.drawString(title, (this.xSize - this.fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);

        String a = energy + "/" + maxEnergy;
        this.fontRenderer.drawString(a, 88 - this.fontRenderer.getStringWidth(a) / 2, 60, 0x404040);
    }

    protected void updateData() {
        energy = generater.getTileEntity().getStorage().getEnergyStored();
        maxEnergy = generater.getTileEntity().getStorage().getMaxEnergyStored();
        powerGening = generater.getTileEntity().getPowerGening();
        maxPowerGen = generater.getTileEntity().getMaxPowerGen();
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public void setMaxEnergy(int maxEnergy) {
        this.maxEnergy = maxEnergy;
    }

    public void setPowerGening(int powerGening) {
        this.powerGening = powerGening;
    }

    public void setMaxPowerGen(int maxPowerGen) {
        this.maxPowerGen = maxPowerGen;
    }
}
