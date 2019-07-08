package com.elementtimes.tutorial.client.gui.base;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import com.elementtimes.tutorial.other.SideHandlerType;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.Map;

/**
 * 对所有机器 gui 的抽象
 *
 * @author luqin2007
 */
public class GuiMachineContainer extends GuiContainer {

    protected int mTextOffsetY;

    /**
     * 机器的 ContainerMachine，内含其 TileEntity
     */
    protected ContainerMachine machine;

    /**
     * GUI 界面贴图
     */
    protected ResourceLocation texture;

    public GuiMachineContainer(ContainerMachine machine, String texture, int textOffsetY) {
        super(machine);
        this.machine = machine;
        xSize = machine.getWidth();
        ySize = machine.getHeight();
        mTextOffsetY = textOffsetY;
        this.texture = new ResourceLocation(ElementTimes.MODID, texture);
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        renderHoveredToolTip(mouseX, mouseY);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(texture);
        this.drawTexturedModalRect(getGuiLeft(), getGuiTop(), 0, 0, this.xSize, this.ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        super.drawGuiContainerForegroundLayer(mouseX, mouseY);
        String name = machine.getName();
        this.fontRenderer.drawString(name, (machine.getWidth() - fontRenderer.getStringWidth(name)) / 2, mTextOffsetY, 0x404040);
        // 流体
        GlStateManager.color(1.0F, 1.0F, 1.0F);
        final Map<SideHandlerType, Int2ObjectMap<ImmutablePair<FluidStack, Integer>>> fluids = ContainerMachine.FLUIDS;
        machine.getFluidPositions().forEach((type, slots) -> {
            Int2ObjectMap<ImmutablePair<FluidStack, Integer>> typedFluids = fluids.get(type);
            if (typedFluids != null) {
                slots.forEach((slot, slotXYWH) -> {
                    ImmutablePair<FluidStack, Integer> fluidPair = typedFluids.get(slot);
                    if (fluidPair != null) {
                        float total = fluidPair.right;
                        FluidStack fluidStack = fluidPair.left;
                        if (fluidStack == null) {
                            fluidStack = new FluidStack(FluidRegistry.WATER, 0);
                        }
                        // x y w h
                        int[] p = machine.getFluidPositions().get(type).get(slot);
                        ResourceLocation stillTexture = fluidStack.getFluid().getStill();
                        ResourceLocation stillTexture2 = new ResourceLocation(stillTexture.getResourceDomain(), "textures/" + stillTexture.getResourcePath() + ".png");
                        mc.getTextureManager().bindTexture(stillTexture2);
                        int h = (int) (p[3] * fluidStack.amount / total);
                        int y = p[1] + p[3] - h;
                        this.drawTexturedModalRect(p[0], y, 0, 0, p[2], h);
                    }
                });
            }
        });
    }
}
