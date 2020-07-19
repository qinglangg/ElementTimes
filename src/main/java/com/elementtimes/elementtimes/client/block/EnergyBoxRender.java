package com.elementtimes.elementtimes.client.block;

import com.elementtimes.elementtimes.common.block.machine.TileEnergyBox;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.animation.TileEntityRendererFast;

/**
 * 能量盒渲染

 */
@OnlyIn(Dist.CLIENT)
public class EnergyBoxRender extends TileEntityRendererFast<TileEnergyBox> {

    @Override
    public void renderTileEntityFast(TileEnergyBox te, double x, double y, double z, 
                                     float partialTicks, int destroyStage, BufferBuilder buffer) {
        String energy = te.getEnergyStored() + "/" + te.getMaxEnergyStored() + " RF";
        int fontColor = 0xFF23FFA3;

        float fontWidth = Minecraft.getInstance().fontRenderer.getStringWidth(energy);
        float offsetX = 0.07f;
        float offsetY = 0.9375f;
        float offsetZ = 0.01f;
        float scale = 0.625f / fontWidth;
        // south
        GlStateManager.pushMatrix();
        GlStateManager.translated(x + offsetX, y + offsetY, z + 1 + offsetZ);
        GlStateManager.scalef(scale, -scale, 1);
        Minecraft.getInstance().fontRenderer.drawString(energy, 0, 0, fontColor);
        GlStateManager.popMatrix();
        // east
        GlStateManager.pushMatrix();
        GlStateManager.translated(x + 1 + offsetZ, y + offsetY, z + 1 - offsetX);
        GlStateManager.rotatef(90, 0, 1, 0);
        GlStateManager.scalef(scale, -scale, 1);
        Minecraft.getInstance().fontRenderer.drawString(energy, 0, 0, fontColor);
        GlStateManager.popMatrix();
        // west
        GlStateManager.pushMatrix();
        GlStateManager.translated(x - offsetZ, y + offsetY, z + offsetX);
        GlStateManager.rotatef(270, 0, 1, 0);
        GlStateManager.scalef(scale, -scale, 1);
        Minecraft.getInstance().fontRenderer.drawString(energy, 0, 0, fontColor);
        GlStateManager.popMatrix();
        // north
        GlStateManager.pushMatrix();
        GlStateManager.translated(x + 1 - offsetX, y + offsetY, z - offsetZ);
        GlStateManager.rotatef(180, 0, 1, 0);
        GlStateManager.scalef(scale, -scale, 1);
        Minecraft.getInstance().fontRenderer.drawString(energy, 0, 0, fontColor);
        GlStateManager.popMatrix();
    }
}
