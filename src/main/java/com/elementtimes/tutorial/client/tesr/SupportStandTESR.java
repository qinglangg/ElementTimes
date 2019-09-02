package com.elementtimes.tutorial.client.tesr;

import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import com.elementtimes.tutorial.other.RenderObject;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraftforge.client.model.animation.FastTESR;

public class SupportStandTESR extends FastTESR<TileSupportStand> {

    @Override
    public void renderTileEntityFast(TileSupportStand te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        for (RenderObject stack : te.getRenderItems()) {
            if (stack == null || stack == RenderObject.EMPTY || !stack.isRender()) {
                continue;
            }

            GlStateManager.pushMatrix();

            double ix = stack.vector.x;
            double iy = stack.vector.y;
            double iz = stack.vector.z;



            GlStateManager.translate(x + ix, y + iy, z + iz);
            GlStateManager.scale(3, 3, 3);

            renderItem.renderItem(stack.obj, ItemCameraTransforms.TransformType.GROUND);

            GlStateManager.popMatrix();
        }
    }
}
