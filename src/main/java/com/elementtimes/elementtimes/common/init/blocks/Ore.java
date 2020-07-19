package com.elementtimes.elementtimes.common.init.blocks;

import com.elementtimes.elementcore.api.annotation.ModBlock;
import com.elementtimes.elementcore.api.annotation.ModElement;
import com.elementtimes.elementcore.api.annotation.enums.ValueType;
import com.elementtimes.elementcore.api.annotation.part.*;
import com.elementtimes.elementcore.api.annotation.tools.ModTooltips;
import com.elementtimes.elementtimes.common.feature.SulfurFeature;
import com.elementtimes.elementtimes.common.init.Groups;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.material.Material;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.feature.SphereReplaceConfig;
import net.minecraft.world.gen.placement.ChanceRangeConfig;
import net.minecraft.world.gen.placement.FrequencyConfig;
import net.minecraft.world.gen.placement.NoPlacementConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.ToolType;



@ModElement(itemProp = @ItemProps(group = @Getter(value = Groups.class, name = "Ore")))
public class Ore {
    @ModBlock.Features({
            @Feature(biome = @Biome(value = "plains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "desert"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "mountains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "forest"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "swamp"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "river"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "beach"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "jungle"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "savanna"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "badlands"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature"))})
    public static Block oreTin = new Block(ore(15, 10, 2));
    @ModBlock.Features({
            @Feature(biome = @Biome(value = "plains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "desert"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "mountains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "forest"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "swamp"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "river"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "beach"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "jungle"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "savanna"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "badlands"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature"))})
    public static Block oreLead = new Block(ore(15, 10, 2));
    @ModBlock.Features({
            @Feature(biome = @Biome(value = "plains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "desert"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "mountains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "forest"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "swamp"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "river"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "beach"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "jungle"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "savanna"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "badlands"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature"))})
    public static Block oreCopper = new Block(ore(15, 10, 1));
    @ModBlock.Features({
            @Feature(biome = @Biome(value = "plains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "desert"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "mountains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "forest"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "swamp"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "river"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "beach"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "jungle"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "savanna"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "badlands"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature"))})
    public static Block orePlatinum = new Block(ore(20, 10, 3));
    @ModBlock.Features({
            @Feature(biome = @Biome(value = "plains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "desert"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "mountains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "forest"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "swamp"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "river"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "beach"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "jungle"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "savanna"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "badlands"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature"))})
    public static Block oreSilver = new Block(ore(15, 10, 2));
    @ModBlock.Features({
            @Feature(biome = @Biome(value = "plains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "desert"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "mountains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "forest"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "swamp"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "river"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "beach"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "jungle"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "savanna"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "badlands"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature"))})
    public static Block oreSalt = new Block(Block.Properties.create(Material.CLAY));
    @ModBlock.Features({
            @Feature(biome = @Biome(value = "plains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "desert"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "mountains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "forest"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "swamp"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "river"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "beach"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "jungle"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "savanna"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "badlands"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature"))})
    public static Block oreSulfur = new Block(ore(20, 10, 2));
    @ModBlock.Features({
            @Feature(biome = @Biome(value = "plains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "desert"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "mountains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "forest"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "swamp"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "river"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "beach"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "jungle"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "savanna"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "badlands"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature"))})
    public static Block oreCalciumFluoride = new Block(ore(15, 10, 2));
    @ModTooltips("\u00a79U3O8 U^235/238")
    @ModBlock.Features({
            @Feature(biome = @Biome(value = "plains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "desert"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "mountains"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "forest"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "swamp"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "river"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "beach"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "jungle"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "savanna"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature")),
            @Feature(biome = @Biome(value = "badlands"), decoration = GenerationStage.Decoration.UNDERGROUND_ORES, type = ValueType.METHOD, method = @Method(value = Ore.class, name = "feature"))})
    public static Block oreUranium = new Block(ore(20, 10, 3));

    public static Block blockCopper = new Block(ore(50, 20, 2));
    public static Block blockLead = new Block(ore(50, 20, 2));
    public static Block blockPlatinum = new Block(ore(50, 20, 3).lightValue(50));
    public static Block blockRawDiamond = new Block(ore(100, 35, 2).lightValue(100));
    public static Block blockSilver = new Block(ore(50, 20, 2).lightValue(50));
    public static Block blockSteel = new Block(ore(50, 20, 2).lightValue(50));
    public static Block blockTin = new Block(ore(50, 20, 2));

    private static Block.Properties ore(float hardness, float resistance, int level) {
        return Block.Properties.create(Material.ROCK).hardnessAndResistance(hardness, resistance).harvestTool(ToolType.PICKAXE).harvestLevel(level);
    }

    private static ConfiguredFeature<?> feature(Block block) {
        if (block == orePlatinum || block == oreUranium) {
            OreFeatureConfig fc = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, block.getDefaultState(), 8);
            ChanceRangeConfig pc = new ChanceRangeConfig(0.3f, 5, 0, 15);
            return net.minecraft.world.biome.Biome.createDecoratedFeature(net.minecraft.world.gen.feature.Feature.ORE, fc, Placement.CHANCE_RANGE, pc);
        } else if (block == oreSalt) {
            SphereReplaceConfig fc = new SphereReplaceConfig(Blocks.GRAVEL.getDefaultState(), 6, 2, Lists.newArrayList(Blocks.DIRT.getDefaultState(), Blocks.GRASS_BLOCK.getDefaultState()));
            return net.minecraft.world.biome.Biome.createDecoratedFeature(net.minecraft.world.gen.feature.Feature.DISK, fc, Placement.COUNT_TOP_SOLID, new FrequencyConfig(1));
        } else if (block == oreSulfur) {
            return net.minecraft.world.biome.Biome.createDecoratedFeature(new SulfurFeature(), NoFeatureConfig.NO_FEATURE_CONFIG, Placement.NOPE, NoPlacementConfig.NO_PLACEMENT_CONFIG);
        } else {
            OreFeatureConfig fc = new OreFeatureConfig(OreFeatureConfig.FillerBlockType.NATURAL_STONE, block.getDefaultState(), 8);
            ChanceRangeConfig pc = new ChanceRangeConfig(0.6f, 16, 0, 64);
            return net.minecraft.world.biome.Biome.createDecoratedFeature(net.minecraft.world.gen.feature.Feature.ORE, fc, Placement.CHANCE_RANGE, pc);
        }
    }
}
