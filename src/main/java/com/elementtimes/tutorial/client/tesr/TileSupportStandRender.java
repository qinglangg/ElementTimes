package com.elementtimes.tutorial.client.tesr;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.client.util.RenderObject;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * @author KSGFK create in 2019/6/12
 */
@SideOnly(Side.CLIENT)
public class TileSupportStandRender extends TileEntitySpecialRenderer<TileSupportStand> {
    private RenderItem renderItem;

    @Override
    public void render(TileSupportStand te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        for (RenderObject stack : te.getRenderItems()) {
            if (stack == null || stack == RenderObject.EMPTY || !stack.isRender()) {
                continue;
            }

            if (renderItem == null) {
                renderItem = Minecraft.getMinecraft().getRenderItem();
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
