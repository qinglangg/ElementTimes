package com.elementtimes.elementtimes.common.block;

import net.minecraft.world.gen.feature.AbstractTreeFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;

import javax.annotation.Nullable;
import java.util.Random;
import java.util.function.Supplier;



public class Tree extends net.minecraft.block.trees.Tree {

    private final Supplier<AbstractTreeFeature<NoFeatureConfig>> feature;

    public Tree(Supplier<AbstractTreeFeature<NoFeatureConfig>> feature) {
        this.feature = feature;
    }

    @Nullable
    @Override
    protected AbstractTreeFeature<NoFeatureConfig> getTreeFeature(Random random) {
        return feature.get();
    }
}
