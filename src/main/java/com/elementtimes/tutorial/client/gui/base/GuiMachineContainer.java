package com.elementtimes.tutorial.client.gui.base;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;

import java.io.IOException;

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

    public GuiMachineContainer(ContainerMachine<T> machine, String texture) {
        super(machine);
        this.machine = machine;
        xSize = 176;
        ySize = 156;
        this.texture = new ResourceLocation(ElementTimes.MODID, texture);
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
        // 流体
        ContainerMachine.FLUIDS.keySet().forEach(type -> ContainerMachine.FLUIDS.get(type).keySet().forEach(slot -> {
            FluidStack fluidStack = ContainerMachine.FLUIDS.get(type).get(slot).left;
            if (fluidStack == null) {
                fluidStack = new FluidStack(FluidRegistry.WATER, 0);
            }
            float total = ContainerMachine.FLUIDS.get(type).get(slot).right;
            // x y w h
            int[] p = machine.getFluidPositions().get(type).get(slot);
            mc.getTextureManager().bindTexture(fluidStack.getFluid().getStill());
            int h = (int) (p[3] * fluidStack.amount / total);
            this.drawTexturedModalRect(p[0], p[1], 0, 0, p[2], h);
        }));
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String name = machine.getName();
        this.fontRenderer.drawString(name, 88 - this.fontRenderer.getStringWidth(name) / 2, 60, 0x404040);
    }

    @Override
    protected void actionPerformed(GuiButton button) throws IOException {
        super.actionPerformed(button);
        machine.actionPerformed(button, this);
    }
}
