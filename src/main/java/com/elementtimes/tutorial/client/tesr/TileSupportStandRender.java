package com.elementtimes.tutorial.client.tesr;

import com.elementtimes.tutorial.client.util.RenderObject;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;

/**
 * @author KSGFK create in 2019/6/12
 */
public class TileSupportStandRender extends TileEntitySpecialRenderer<TileSupportStand> {
    private boolean isInit = false;
    private BlockRendererDispatcher blockRedner;
    private RenderItem renderItem;

    private void init() {
        blockRedner = Minecraft.getMinecraft().getBlockRendererDispatcher();
        renderItem = Minecraft.getMinecraft().getRenderItem();
        isInit = true;
    }

    @Override
    public void render(TileSupportStand te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if (!isInit) {
            init();
        }

        for (RenderObject stack : te.getRenderItems()) {
            if (!stack.isRender()) {
                continue;
            }

            GlStateManager.pushMatrix();

            double ix = stack.vector.x;
            double iy = stack.vector.y;
            double iz = stack.vector.z;

            GlStateManager.translate(x + ix, y + iy, z + iz);
            GlStateManager.scale(3, 3, 3);

            if (stack.isBlock()) {
                renderItem.renderItem(stack.obj, ItemCameraTransforms.TransformType.GROUND);
            } else {
                renderItem.renderItem(stack.obj, ItemCameraTransforms.TransformType.GROUND);
            }

            GlStateManager.popMatrix();
        }
    }
}
