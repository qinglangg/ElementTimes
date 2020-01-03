package com.elementtimes.tutorial.common.pipeline.test;

import com.elementtimes.tutorial.interfaces.ITilePipeline;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import javax.annotation.Nonnull;

public class PLTestContainer extends Container {

    public final ITilePipeline pipeline;

    public PLTestContainer(ITilePipeline pipeline) {
        this.pipeline = pipeline;
    }

    @Override
    public boolean canInteractWith(@Nonnull EntityPlayer playerIn) {
        return true;
    }
}
