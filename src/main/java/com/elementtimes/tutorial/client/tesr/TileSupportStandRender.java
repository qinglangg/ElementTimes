package com.elementtimes.tutorial.client.tesr;

import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 * @author KSGFK create in 2019/6/12
 */
public class TileSupportStandRender extends BaseTESR<TileSupportStand> {
    @Override
    protected void renderPipeline(TileSupportStand te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        for (ItemStack stack : te.getRenderItems()) {
            GlStateManager.pushMatrix();

            NBTTagCompound nbt = stack.getTagCompound();//用nbt感觉会比较慢...

            float ix = nbt.getFloat("x");
            float iy = nbt.getFloat("y");
            float iz = nbt.getFloat("z");

            GlStateManager.translate(x + ix, y + iy, z + iz);
            GlStateManager.scale(3, 3, 3);
            renderItem.renderItem(stack, ItemCameraTransforms.TransformType.GROUND);

            GlStateManager.popMatrix();
        }
    }
}