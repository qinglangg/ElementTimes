package com.elementtimes.tutorial.client.block;

import com.elementtimes.tutorial.common.tileentity.TileEnergyBox;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

/**
 * 流体储罐渲染
 * @author luqin2007
 */
@SideOnly(Side.CLIENT)
public class EnergyBoxRender extends FastTESR<TileEnergyBox> {

    @Override
    public void renderTileEntityFast(TileEnergyBox te, double x, double y, double z, float partialTicks,
                                     int destroyStage, float partial, @Nonnull BufferBuilder buffer) {
        String energy = te.getEnergyStored() + "/" + te.getMaxEnergyStored() + " RF";
        int fontColor = 0xFF23FFA3;

        float fontWidth = Minecraft.getMinecraft().fontRenderer.getStringWidth(energy);
        float offsetX = 0.07f;
        float offsetY = 0.9375f;
        float offsetZ = 0.01f;
        float scale = 0.625f / fontWidth;
        // south
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + offsetX, y + offsetY, z + 1 + offsetZ);
        GlStateManager.scale(scale, -scale, 1);
        Minecraft.getMinecraft().fontRenderer.drawString(energy, 0, 0, fontColor);
        GlStateManager.popMatrix();
        // east
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 1 + offsetZ, y + offsetY, z + 1 - offsetX);
        GlStateManager.rotate(90, 0, 1, 0);
        GlStateManager.scale(scale, -scale, 1);
        Minecraft.getMinecraft().fontRenderer.drawString(energy, 0, 0, fontColor);
        GlStateManager.popMatrix();
        // west
        GlStateManager.pushMatrix();
        GlStateManager.translate(x - offsetZ, y + offsetY, z + offsetX);
        GlStateManager.rotate(270, 0, 1, 0);
        GlStateManager.scale(scale, -scale, 1);
        Minecraft.getMinecraft().fontRenderer.drawString(energy, 0, 0, fontColor);
        GlStateManager.popMatrix();
        // north
        GlStateManager.pushMatrix();
        GlStateManager.translate(x + 1 - offsetX, y + offsetY, z - offsetZ);
        GlStateManager.rotate(180, 0, 1, 0);
        GlStateManager.scale(scale, -scale, 1);
        Minecraft.getMinecraft().fontRenderer.drawString(energy, 0, 0, fontColor);
        GlStateManager.popMatrix();
    }
}
