package com.elementtimes.tutorial.test_tesr;

import net.minecraft.client.renderer.BufferBuilder;
import net.minecraftforge.client.model.animation.FastTESR;

public class TESRTest extends FastTESR<TileTESR> {
    @Override
    public void renderTileEntityFast(TileTESR te, double x, double y, double z,
                                     float partialTicks, int destroyStage, float partial,
                                     BufferBuilder buffer) {

    }
}
