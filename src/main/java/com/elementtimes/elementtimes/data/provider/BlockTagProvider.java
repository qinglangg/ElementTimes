package com.elementtimes.elementtimes.data.provider;

import com.elementtimes.elementcore.api.utils.CollectionUtils;
import com.elementtimes.elementtimes.common.init.blocks.Agriculture;
import com.elementtimes.elementtimes.common.init.blocks.Chemical;
import com.elementtimes.elementtimes.common.init.blocks.Ore;
import net.minecraft.block.Block;
import net.minecraft.data.BlockTagsProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.elementtimes.elementcore.api.utils.tags.TagNames.ORES;
import static com.elementtimes.elementcore.api.utils.tags.TagNames.STORAGE_BLOCKS;



public class BlockTagProvider extends BlockTagsProvider {

    public final Set<Tag<Block>> tags = new HashSet<>();
    public final Map<String, Tag<Block>> groups = new HashMap<>();

    public BlockTagProvider(DataGenerator generatorIn) {
        super(generatorIn);
    }

    @Override
    protected void registerTags() {
        newForgeTag(ORES, "copper"  , Ore.oreCopper);
        newForgeTag(ORES, "lead"    , Ore.oreLead);
        newForgeTag(ORES, "platinum", Ore.orePlatinum);
        newForgeTag(ORES, "salt"    , Ore.oreSalt);
        newForgeTag(ORES, "silver"  , Ore.oreSilver);
        newForgeTag(ORES, "sulfur"  , Ore.oreSulfur);
        newForgeTag(ORES, "tin"     , Ore.oreTin);
        newForgeTag(ORES, "uranium" , Ore.oreUranium);
        newForgeTag(STORAGE_BLOCKS, "copper"  , Ore.blockCopper);
        newForgeTag(STORAGE_BLOCKS, "diamond" , Ore.blockRawDiamond);
        newForgeTag(STORAGE_BLOCKS, "lead"    , Ore.blockLead);
        newForgeTag(STORAGE_BLOCKS, "platinum", Ore.blockPlatinum);
        newForgeTag(STORAGE_BLOCKS, "silver"  , Ore.blockSilver);
        newForgeTag(STORAGE_BLOCKS, "steel"   , Ore.blockSteel);
        newForgeTag(STORAGE_BLOCKS, "tin"     , Ore.blockTin);
        newForgeTag("cement", Chemical.cement);
        newVanillaTag(BlockTags.SAPLINGS, Agriculture.saplingEssence, Agriculture.saplingRubber);
        newForgeTag("saplings/rubber", Agriculture.saplingRubber);
        newForgeTag("saplings/essence", Agriculture.saplingEssence);
        newVanillaTag(BlockTags.LOGS, Agriculture.logEssence, Agriculture.logRubber);
        newForgeTag("logs/rubber", Agriculture.logRubber);
        newForgeTag("logs/essence", Agriculture.logEssence);
        newVanillaTag(BlockTags.LEAVES, Agriculture.leavesEssence, Agriculture.leavesRubber);
        newForgeTag("leaves/rubber", Agriculture.leavesRubber);
        newForgeTag("leaves/essence", Agriculture.leavesEssence);
    }

    private void newForgeTag(String type, String material, Block... blocks) {
        BlockTags.Wrapper tag = new BlockTags.Wrapper(new ResourceLocation("forge", type + "/" + material));
        if (blocks.length > 0) {
            getBuilder(tag).add(blocks);
        }
        Tag<Block> group = CollectionUtils.computeIfAbsent(groups, type, () -> {
            BlockTags.Wrapper groupTag = new BlockTags.Wrapper(new ResourceLocation("forge", type));
            tags.add(groupTag);
            return groupTag;
        });
        getBuilder(group).add(tag);
        tags.add(tag);
    }

    private void newForgeTag(String name, Block... blocks) {
        BlockTags.Wrapper tag = new BlockTags.Wrapper(new ResourceLocation("forge", name));
        getBuilder(tag).add(blocks);
        tags.add(tag);
    }

    private void newVanillaTag(Tag<Block> tag, Block... blocks) {
        getBuilder(tag).add(blocks);
        tags.add(tag);
    }
}
