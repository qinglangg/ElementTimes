package com.elementtimes.elementtimes.client.block.pipeline;

import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.IUnbakedModel;
import net.minecraft.client.renderer.model.ModelBakery;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.ISprite;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoaderRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.Set;
import java.util.function.Function;



public class PipelineItemModel implements IUnbakedModel {

    public static final ModelResourceLocation MODEL_ITEM_INPUT = new ModelResourceLocation(ElementTimes.MODID, "sub_pipeline/item_input");
    public static final ModelResourceLocation MODEL_ITEM_OUTPUT = new ModelResourceLocation(ElementTimes.MODID, "sub_pipeline/item_output");
    public static final ModelResourceLocation MODEL_ITEM_CONNECT = new ModelResourceLocation(ElementTimes.MODID, "sub_pipeline/item_connect");
    public static final ModelResourceLocation MODEL_FLUID_INPUT = new ModelResourceLocation(ElementTimes.MODID, "sub_pipeline/fluid_input");
    public static final ModelResourceLocation MODEL_FLUID_OUTPUT = new ModelResourceLocation(ElementTimes.MODID, "sub_pipeline/fluid_output");
    public static final ModelResourceLocation MODEL_FLUID_CONNECT = new ModelResourceLocation(ElementTimes.MODID, "sub_pipeline/fluid_connect");

    public static final ResourceLocation TEXTURE_ITEM_CORE = new ResourceLocation(ElementTimes.MODID, "blocks/pipeline/item");
    public static final ResourceLocation TEXTURE_ITEM_IO = new ResourceLocation(ElementTimes.MODID, "blocks/pipeline/item_io");
    public static final ResourceLocation TEXTURE_FLUID_CORE = new ResourceLocation(ElementTimes.MODID, "blocks/pipeline/fluid");
    public static final ResourceLocation TEXTURE_FLUID_IO = new ResourceLocation(ElementTimes.MODID, "blocks/pipeline/fluid_io");

    @Override
    @Nonnull
    public Collection<ResourceLocation> getDependencies() {
        return Arrays.asList(MODEL_ITEM_CONNECT, MODEL_ITEM_INPUT, MODEL_ITEM_OUTPUT, MODEL_FLUID_CONNECT, MODEL_FLUID_INPUT, MODEL_FLUID_OUTPUT);
    }

    @Override
    @Nonnull
    public Collection<ResourceLocation> getTextures(Function<ResourceLocation, IUnbakedModel> modelGetter, Set<String> missingTextureErrors) {
        return Arrays.asList(TEXTURE_ITEM_CORE, TEXTURE_ITEM_IO, TEXTURE_FLUID_CORE, TEXTURE_FLUID_IO);
    }

    @Nullable
    @Override
    public IBakedModel bake(ModelBakery bakery, Function<ResourceLocation, TextureAtlasSprite> spriteGetter, ISprite sprite, VertexFormat format) {
        return ModelLoaderRegistry.getMissingModel().bake(bakery, spriteGetter, sprite, format);
    }
}
