package com.elementtimes.elementtimes.client.block.pipeline;

import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;

import javax.annotation.Nonnull;



@OnlyIn(Dist.CLIENT)
public class PipelineModelLoader implements ICustomModelLoader {

    private IResourceManager mResourceManager;

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {
        mResourceManager = resourceManager;
    }

    @Override
    public boolean accepts(ResourceLocation location) {
        return location.equals(Industry.pipelineItemInput.getRegistryName())
                || location.equals(Industry.pipelineItemOutput.getRegistryName())
                || location.equals(Industry.pipelineItemConnect.getRegistryName())
                || location.equals(Industry.pipelineFluidInput.getRegistryName())
                || location.equals(Industry.pipelineFluidOutput.getRegistryName())
                || location.equals(Industry.pipelineFluidConnect.getRegistryName());
    }

    @Override
    public IUnbakedModel loadModel(@Nonnull ResourceLocation location) {
        return accepts(location) ? new PipelineItemModel() : ModelLoaderRegistry.getMissingModel();
    }
}
