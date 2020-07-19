package com.elementtimes.elementtimes.client.block;

import com.elementtimes.elementtimes.common.block.stand.module.ISupportStandModule;
import com.elementtimes.elementtimes.common.block.stand.te.TileSupportStand;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.animation.TileEntityRendererFast;



@OnlyIn(Dist.CLIENT)
public class SSMRender extends TileEntityRendererFast<TileSupportStand> {

    @Override
    public void renderTileEntityFast(TileSupportStand te, double x, double y, double z, float partialTicks, int destroyStage, BufferBuilder buffer) {
        GlStateManager.pushMatrix();
        GlStateManager.translated(x, y - 0.13, z);
        for (ISupportStandModule module : te.getModules()) {
            GlStateManager.pushMatrix();
            module.onRender();
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }
}
