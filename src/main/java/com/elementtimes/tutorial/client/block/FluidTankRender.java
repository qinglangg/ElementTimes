package com.elementtimes.tutorial.client.block;

import com.elementtimes.tutorial.common.tileentity.TileFluidTank;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * 流体储罐渲染
 * @author luqin2007
 */
@SideOnly(Side.CLIENT)
public class FluidTankRender extends FastTESR<TileFluidTank> {
    
    @Override
    public void renderTileEntityFast(TileFluidTank te, double x, double y, double z, float partialTicks,
                                     int destroyStage, float partial, @Nonnull BufferBuilder buffer) {
        FluidStack stack = te.getFluid();
        Fluid fluid = stack == null ? null : stack.getFluid();
        int capability = te.getCapability();

        String fluidType = stack == null ? "Empty" : fluid.getLocalizedName(stack);
        String fluidCount = stack == null ? "0mb" : stack.amount + "/" + capability + "mb";
        int fontColor = stack == null ? 0xFF23FFA3 : fluid == FluidRegistry.LAVA ? 0xFFD32F2F : fluid == FluidRegistry.WATER ? 0xFF448AFF : fluid.getColor(stack);

        int widthFluid = Minecraft.getMinecraft().fontRenderer.getStringWidth(fluidType);
        int widthCap = 50; // 16000/16000mb
        float left = 0.0625f;
        float yFluidName = 0.9375f, yFluidCount = -0.7825f;
        float offsetZ = 0.01f;
        float scale = 0.625f / widthFluid, scaleCap = 0.625f / widthCap;
        // south
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + left, y + yFluidName, z + 1 + offsetZ);
        GlStateManager.scale(scale, -scale, 1);
        Minecraft.getMinecraft().fontRenderer.drawString(fluidType, 0, 0, fontColor);
        GlStateManager.scale(1f / scale, -1f / scale, 1);
        GlStateManager.translate(0, yFluidCount, 0);
        GlStateManager.scale(scaleCap, -scaleCap, 1);
        Minecraft.getMinecraft().fontRenderer.drawString(fluidCount, 0, 0, 0xFF00AA00);
        GlStateManager.popMatrix();
        // east
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 1 + offsetZ, y + yFluidName, z + 1 - left);
        GlStateManager.rotate(90, 0, 1, 0);
        GlStateManager.scale(scale, -scale, 1);
        Minecraft.getMinecraft().fontRenderer.drawString(fluidType, 0, 0, fontColor);
        GlStateManager.scale(1f / scale, -1f / scale, 1);
        GlStateManager.translate(0, yFluidCount, 0);
        GlStateManager.scale(scaleCap, -scaleCap, 1);
        Minecraft.getMinecraft().fontRenderer.drawString(fluidCount, 0, 0, 0xFF00AA00);
        GlStateManager.popMatrix();
        // west
        GlStateManager.pushMatrix();
        GlStateManager.translate(x - offsetZ, y + yFluidName, z + left);
        GlStateManager.rotate(270, 0, 1, 0);
        GlStateManager.scale(scale, -scale, 1);
        Minecraft.getMinecraft().fontRenderer.drawString(fluidType, 0, 0, fontColor);
        GlStateManager.scale(1f / scale, -1f / scale, 1);
        GlStateManager.translate(0, yFluidCount, 0);
        GlStateManager.scale(scaleCap, -scaleCap, 1);
        Minecraft.getMinecraft().fontRenderer.drawString(fluidCount, 0, 0, 0xFF00AA00);
        GlStateManager.popMatrix();
        // north
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 1 - left, y + yFluidName, z - offsetZ);
        GlStateManager.rotate(180, 0, 1, 0);
        GlStateManager.scale(scale, -scale, 1);
        Minecraft.getMinecraft().fontRenderer.drawString(fluidType, 0, 0, fontColor);
        GlStateManager.scale(1f / scale, -1f / scale, 1);
        GlStateManager.translate(0, yFluidCount, 0);
        GlStateManager.scale(scaleCap, -scaleCap, 1);
        Minecraft.getMinecraft().fontRenderer.drawString(fluidCount, 0, 0, 0xFF00AA00);
        GlStateManager.popMatrix();
    }
}
