package com.elementtimes.elementtimes.client.block.pipeline;

import com.elementtimes.elementtimes.common.block.pipeline.Pipeline;
import net.minecraft.block.BlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.model.BakedQuad;
import net.minecraft.client.renderer.model.IBakedModel;
import net.minecraft.client.renderer.model.ItemOverrideList;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.Direction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;



@OnlyIn(Dist.CLIENT)
public class BakedPipelineModel implements IBakedModel {

    protected String mParticleTexture;
    protected IBakedModel mModelCore;
    protected BiFunction<Direction, BlockState, IBakedModel> mSideModels;

    public BakedPipelineModel(IBakedModel modelCore, String particleTexture, BiFunction<Direction, BlockState, IBakedModel> sideModels) {
        mModelCore = modelCore;
        mParticleTexture = particleTexture;
        mSideModels = sideModels;
    }

    @Override
    public List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, Random rand) {
        List<BakedQuad> quads = new ArrayList<>(mModelCore.getQuads(state, side, rand));
        if (state != null && state.getBlock() instanceof Pipeline) {
            for (Direction value : Direction.values()) {
                IBakedModel model = mSideModels.apply(value, state);
                if (model != null) {
                    quads.addAll(model.getQuads(state, side, rand));
                }
            }
        }
        return quads;
    }

    @Override
    public boolean isAmbientOcclusion() {
        return mModelCore.isAmbientOcclusion();
    }

    @Override
    public boolean isGui3d() {
        return mModelCore.isGui3d();
    }

    @Override
    public boolean isBuiltInRenderer() {
        return false;
    }

    @Override
    public TextureAtlasSprite getParticleTexture() {
        AtlasTexture texture = Minecraft.getInstance().getTextureMap();
        return texture.getAtlasSprite(mParticleTexture);
    }

    @Override
    public ItemOverrideList getOverrides() {
        return ItemOverrideList.EMPTY;
    }
}
