package com.elementtimes.elementtimes.client.block;

import com.elementtimes.elementtimes.common.block.machine.TileFluidTank;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.fluid.Fluid;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.animation.TileEntityRendererFast;
import net.minecraftforge.fluids.FluidStack;

/**
 * 流体储罐渲染

 */
@OnlyIn(Dist.CLIENT)
public class FluidTankRender extends TileEntityRendererFast<TileFluidTank> {

    @Override
    public void renderTileEntityFast(TileFluidTank te, double x, double y, double z, float partialTicks, int destroyStage, BufferBuilder buffer) {
        FluidStack stack = te.getFluid();
        Fluid fluid = stack == null ? null : stack.getFluid();
        int capability = te.getCapability();

        String fluidType = stack == null ? "Empty" : stack.getDisplayName().getFormattedText();
        String fluidCount = stack == null ? "0mb" : stack.getAmount() + "/" + capability + "mb";
        int fontColor = stack == null ? 0xFF23FFA3 : fluid.getAttributes().getColor();

        int widthFluid = Minecraft.getInstance().fontRenderer.getStringWidth(fluidType);
        int widthCap = 50;
        float left = 0.0625f;
        float yFluidName = 0.9375f, yFluidCount = -0.7825f;
        float offsetZ = 0.01f;
        float scale = 0.625f / widthFluid, scaleCap = 0.625f / widthCap;
        // south
        GlStateManager.pushMatrix();
        GlStateManager.translated(x + left, y + yFluidName, z + 1 + offsetZ);
        GlStateManager.scalef(scale, -scale, 1);
        Minecraft.getInstance().fontRenderer.drawString(fluidType, 0, 0, fontColor);
        GlStateManager.scalef(1f / scale, -1f / scale, 1);
        GlStateManager.translated(0, yFluidCount, 0);
        GlStateManager.scalef(scaleCap, -scaleCap, 1);
        Minecraft.getInstance().fontRenderer.drawString(fluidCount, 0, 0, 0xFF00AA00);
        GlStateManager.popMatrix();
        // east
        GlStateManager.pushMatrix();
        GlStateManager.translated(x + 1 + offsetZ, y + yFluidName, z + 1 - left);
        GlStateManager.rotatef(90, 0, 1, 0);
        GlStateManager.scalef(scale, -scale, 1);
        Minecraft.getInstance().fontRenderer.drawString(fluidType, 0, 0, fontColor);
        GlStateManager.scalef(1f / scale, -1f / scale, 1);
        GlStateManager.translated(0, yFluidCount, 0);
        GlStateManager.scalef(scaleCap, -scaleCap, 1);
        Minecraft.getInstance().fontRenderer.drawString(fluidCount, 0, 0, 0xFF00AA00);
        GlStateManager.popMatrix();
        // west
        GlStateManager.pushMatrix();
        GlStateManager.translated(x - offsetZ, y + yFluidName, z + left);
        GlStateManager.rotatef(270, 0, 1, 0);
        GlStateManager.scalef(scale, -scale, 1);
        Minecraft.getInstance().fontRenderer.drawString(fluidType, 0, 0, fontColor);
        GlStateManager.scalef(1f / scale, -1f / scale, 1);
        GlStateManager.translated(0, yFluidCount, 0);
        GlStateManager.scalef(scaleCap, -scaleCap, 1);
        Minecraft.getInstance().fontRenderer.drawString(fluidCount, 0, 0, 0xFF00AA00);
        GlStateManager.popMatrix();
        // north
        GlStateManager.pushMatrix();
        GlStateManager.translated(x + 1 - left, y + yFluidName, z - offsetZ);
        GlStateManager.rotatef(180, 0, 1, 0);
        GlStateManager.scalef(scale, -scale, 1);
        Minecraft.getInstance().fontRenderer.drawString(fluidType, 0, 0, fontColor);
        GlStateManager.scalef(1f / scale, -1f / scale, 1);
        GlStateManager.translated(0, yFluidCount, 0);
        GlStateManager.scalef(scaleCap, -scaleCap, 1);
        Minecraft.getInstance().fontRenderer.drawString(fluidCount, 0, 0, 0xFF00AA00);
        GlStateManager.popMatrix();
    }
}
