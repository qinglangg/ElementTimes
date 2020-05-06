package com.elementtimes.tutorial.client.block;

import com.elementtimes.tutorial.common.block.stand.module.ISupportStandModule;
import com.elementtimes.tutorial.common.tileentity.stand.TileSupportStand;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.client.model.animation.FastTESR;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SSMRender extends FastTESR<TileSupportStand> {

    @Override
    public void renderTileEntityFast(TileSupportStand te, double x, double y, double z,
                                     float partialTicks, int destroyStage, float partial,
                                     BufferBuilder buffer) {
        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y - 0.13, z);
        for (ISupportStandModule module : te.getModules()) {
            GlStateManager.pushMatrix();
            module.onRender();
            GlStateManager.popMatrix();
        }
        GlStateManager.popMatrix();
    }
}
