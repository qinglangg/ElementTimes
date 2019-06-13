package com.elementtimes.tutorial.client.tesr;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.client.model.animation.FastTESR;

/**
 * @author KSGFK create in 2019/6/13
 */
@SuppressWarnings("AlibabaClassNamingShouldBeCamel")
public abstract class BaseTESR<T extends TileEntity> extends FastTESR<T> {
    private static boolean isInit = false;
    BlockRendererDispatcher blockRedner;
    RenderItem renderItem;

    private void init() {
        blockRedner = Minecraft.getMinecraft().getBlockRendererDispatcher();
        renderItem = Minecraft.getMinecraft().getRenderItem();
        isInit = true;
    }

    @Override
    public void renderTileEntityFast(T te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer) {
        if (!isInit) {
            init();
        }

        World world = te.getWorld();
        if (!world.isRemote) {
            return;
        }

        renderPipeline(te, x, y, z, partialTicks, destroyStage, partial, buffer);
    }

    protected abstract void renderPipeline(T te, double x, double y, double z, float partialTicks, int destroyStage, float partial, BufferBuilder buffer);
}
