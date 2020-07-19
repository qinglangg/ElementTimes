package com.elementtimes.elementtimes.common.init.blocks;

import com.elementtimes.elementcore.api.annotation.ModBlock;
import com.elementtimes.elementcore.api.annotation.ModElement;
import com.elementtimes.elementcore.api.annotation.enums.ValueType;
import com.elementtimes.elementcore.api.annotation.part.*;
import com.elementtimes.elementcore.api.annotation.tools.ModBurnTime;
import com.elementtimes.elementtimes.common.block.CornCrop;
import com.elementtimes.elementtimes.common.block.RubberLog;
import com.elementtimes.elementtimes.common.block.Tree;
import com.elementtimes.elementtimes.common.feature.EssenceFeature;
import com.elementtimes.elementtimes.common.feature.RubberFeature;
import com.elementtimes.elementtimes.common.init.Groups;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.NoFeatureConfig;
import net.minecraft.world.gen.placement.ChanceConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.common.ToolType;



@ModElement(itemProp = @ItemProps(group = @Getter(value = Groups.class, name = "Agriculture")))
public class Agriculture {

    @ModElement.Bind("cornCrop")
    public static Item NoItem = Items.AIR;

    public static Block cornCrop = new CornCrop();
    @ModBlock.Features(@Feature(biome = @Biome("plains"), decoration = GenerationStage.Decoration.VEGETAL_DECORATION, type = ValueType.METHOD, method = @Method(value = Agriculture.class, name = "featureTree")))
    public static Block saplingRubber = new SaplingBlock(new Tree(RubberFeature::new), plants()) {};
    @ModBurnTime(8000)
    public static Block logRubber = new RubberLog();
    public static Block leavesRubber = new LeavesBlock(leaves());
    public static Block saplingEssence = new SaplingBlock(new Tree(EssenceFeature::new), plants()) {};
    @ModBurnTime(8000)
    public static Block logEssence = new LogBlock(MaterialColor.GOLD, wood().hardnessAndResistance(50, 15).lightValue(50).harvestTool(ToolType.AXE).harvestLevel(2).sound(SoundType.WOOD));
    public static Block leavesEssence = new LeavesBlock(leaves().hardnessAndResistance(5, 10).lightValue(20));

    @ModBurnTime(1600)
    public static Block bambooBlock = new Block(wood().hardnessAndResistance(7, 5).harvestTool(ToolType.AXE));
    @ModBurnTime(3200)
    public static Block bambooDoor = new DoorBlock(door()) {};
    @ModBurnTime(800)
    public static Block bambooSlab = new SlabBlock(wood().hardnessAndResistance(2, 5).sound(SoundType.WOOD));
    @ModBurnTime(1600)
    public static Block bambooStair = new StairsBlock(() -> bambooBlock.getDefaultState(), Block.Properties.from(bambooBlock));


    private static Block.Properties leaves() {
        return Block.Properties.create(Material.LEAVES);
    }
    private static Block.Properties plants() {
        return Block.Properties.create(Material.PLANTS);
    }
    private static Block.Properties wood() {
        return Block.Properties.create(Material.WOOD);
    }
    private static Block.Properties door() {
        return Block.Properties.create(Material.WOOD, Material.BAMBOO.getColor()).hardnessAndResistance(3.0F).sound(SoundType.WOOD).harvestTool(ToolType.AXE);
    }

    private static ConfiguredFeature<?> featureTree(Block block) {
        if (block == saplingRubber) {
            return net.minecraft.world.biome.Biome.createDecoratedFeature(new RubberFeature(false), NoFeatureConfig.NO_FEATURE_CONFIG, Placement.CHANCE_HEIGHTMAP, new ChanceConfig(1024));
        }
        return null;
    }
}
