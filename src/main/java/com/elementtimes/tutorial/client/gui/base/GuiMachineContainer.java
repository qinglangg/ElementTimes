package com.elementtimes.tutorial.client.gui.base;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

/**
 * 对所有机器 gui 的抽象
 *
 * @author luqin2007
 */
public class GuiMachineContainer<T extends BaseMachine> extends GuiContainer {

    /**
     * 机器的 ContainerMachine，内含其 TileEntity
     */
    protected ContainerMachine<T> machine;

    /**
     * GUI 界面贴图
     */
    protected ResourceLocation texture;

    GuiMachineContainer(ContainerMachine<T> machine, String texture) {
        super(machine);
        this.machine = machine;
        xSize = 176;
        ySize = 156;
        this.texture = new ResourceLocation(Elementtimes.MODID, texture);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    public void initGui() {
        super.initGui();
        for (GuiButton button : machine.getButtons()) {
            addButton(button);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        int offsetX = (this.width - this.xSize) / 2, offsetY = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(offsetX, offsetY, 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String name = machine.getName();
        this.fontRenderer.drawString(name, 88 - this.fontRenderer.getStringWidth(name) / 2, 60, 0x404040);
    }
}
