package com.elementtimes.elementtimes.client.event;

import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.client.block.pipeline.BakedPipelineModel;
import com.elementtimes.elementtimes.client.block.pipeline.PipelineModelLoader;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;



@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModelEvent {

    public static void onModelBake(ModelBakeEvent event) {
//        BakedPipelineModel bakedPipelineModel = new BakedPipelineModel();
//        event.getModelRegistry().put(Industry.pipelineItemInput.getRegistryName(), bakedPipelineModel);
//        event.getModelRegistry().put(Industry.pipelineItemOutput.getRegistryName(), bakedPipelineModel);
//        event.getModelRegistry().put(Industry.pipelineItemConnect.getRegistryName(), bakedPipelineModel);
//        event.getModelRegistry().put(Industry.pipelineFluidInput.getRegistryName(), bakedPipelineModel);
//        event.getModelRegistry().put(Industry.pipelineFluidOutput.getRegistryName(), bakedPipelineModel);
//        event.getModelRegistry().put(Industry.pipelineFluidConnect.getRegistryName(), bakedPipelineModel);
    }

    public static void onTextureStitch(TextureStitchEvent.Pre event) {
//        System.out.println(event.getMap().getBasePath());
//        event.addSprite(new ResourceLocation(ElementTimes.MODID, "textures/pipeline/item.png"));
//        event.addSprite(new ResourceLocation(ElementTimes.MODID, "textures/pipeline/item_io.png"));
//        event.addSprite(new ResourceLocation(ElementTimes.MODID, "textures/pipeline/fluid.png"));
//        event.addSprite(new ResourceLocation(ElementTimes.MODID, "textures/pipeline/fluid_io.png"));
    }

    public static void onModel(ModelEvent event) {
//        ModelLoaderRegistry.registerLoader(new PipelineModelLoader());
    }
}
