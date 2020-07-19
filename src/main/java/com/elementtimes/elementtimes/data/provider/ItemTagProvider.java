package com.elementtimes.elementtimes.data.provider;

import com.elementtimes.elementcore.api.utils.CollectionUtils;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.init.Items;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.ItemTagsProvider;
import net.minecraft.item.Item;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.Tag;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

import static com.elementtimes.elementcore.api.utils.tags.TagNames.DUSTS;
import static com.elementtimes.elementcore.api.utils.tags.TagNames.INGOTS;



public class ItemTagProvider extends ItemTagsProvider {

    private final BlockTagProvider mBlockTags;
    private final Map<String, ItemTags.Wrapper> mGroups = new HashMap<>();

    public ItemTagProvider(DataGenerator generatorIn, BlockTagProvider blockTags) {
        super(generatorIn);
        mBlockTags = blockTags;
    }

    @Override
    protected void registerTags() {
        mBlockTags.tags.forEach(this::copyBlock);
        newModTag("hammer", Items.hammerSmall, Items.hammerMedium, Items.hammerBig);
        newForgeTag("silicon", Items.coarseSilicon);
        newForgeTag("rubber", Items.rubber);
        newForgeTag("resin", Items.rubberRaw);
        newForgeTag("salt", Items.salt);
        newForgeTag(DUSTS, "ardite", Items.arditepowder);
        newForgeTag(DUSTS, "bluestone", Items.bluestonePowder);
        newForgeTag(DUSTS, "coal", Items.coalPowder);
        newForgeTag(DUSTS, "cobalt", Items.cobaltpowder);
        newForgeTag(DUSTS, "copper", Items.copperPowder, Items.CuO);
        newForgeTag(DUSTS, "diamond", Items.diamondPowder);
        newForgeTag(DUSTS, "greenstone", Items.greenstonePowder);
        newForgeTag(DUSTS, "gold", Items.goldPowder);
        newForgeTag(DUSTS, "iron", Items.ironPowder, Items.Fe2O3);
        newForgeTag(DUSTS, "lead", Items.leadPowder);
        newForgeTag(DUSTS, "nickel", Items.nickelpowder);
        newForgeTag(DUSTS, "osmium", Items.osmiumpowder);
        newForgeTag(DUSTS, "platinum", Items.platinumOrePowder);
        newForgeTag(DUSTS, "quartz", Items.quartzPowder);
        newForgeTag(DUSTS, "redstone", Items.redstonePowder);
        newForgeTag(DUSTS, "sand", Items.sandpowder);
        newForgeTag(DUSTS, "silver", Items.silverPowder);
        newForgeTag(DUSTS, "stone", Items.stonepowder);
        newForgeTag(DUSTS, "sulfur", Items.sulfurPowder, Items.sulfurOrePowder);
        newForgeTag(DUSTS, "tin", Items.tinPowder);
        newForgeTag(DUSTS, "uranium", Items.uraniumPowder);
        newForgeTag(INGOTS, "steel", Items.steelIngot);
        newForgeTag(INGOTS, "stone", Items.stoneIngot);
        newForgeTag(INGOTS, "platinum", Items.platinumIngot);
        newForgeTag(INGOTS, "tin", Items.tin);
        newForgeTag(INGOTS, "lead", Items.lead);
        newForgeTag(INGOTS, "copper", Items.copper);
        newForgeTag(INGOTS, "silver", Items.silver);
        newForgeTag("plates", "adamas", Items.plateRawDiamond);
        newForgeTag("plates", "carbon", Items.plateCarbon);
        newForgeTag("plates", "copper", Items.plateCopper);
        newForgeTag("plates", "diamond", Items.plateDiamond);
        newForgeTag("plates", "gold", Items.plateGold);
        newForgeTag("plates", "iron", Items.plateIron);
        newForgeTag("plates", "lead", Items.plateLead);
        newForgeTag("plates", "obsidian", Items.plateObsidian);
        newForgeTag("plates", "platinum", Items.platePlatinum);
        newForgeTag("plates", "quartz", Items.plateQuartz);
        newForgeTag("plates", "silver", Items.plateSilver);
        newForgeTag("plates", "steel", Items.plateSteel);
        newForgeTag("plates", "stone", Items.plateStone);
        newForgeTag("plates", "tin", Items.plateTin);
        newForgeTag("plates", "wood", Items.plateWood);
        newForgeTag("gears", "adamas", Items.gearRawDiamond);
        newForgeTag("gears", "carbon", Items.gearCarbon);
        newForgeTag("gears", "copper", Items.gearCopper);
        newForgeTag("gears", "diamond", Items.gearDiamond);
        newForgeTag("gears", "gold", Items.gearGold);
        newForgeTag("gears", "iron", Items.gearIron);
        newForgeTag("gears", "lead", Items.gearLead);
        newForgeTag("gears", "obsidian", Items.gearObsidian);
        newForgeTag("gears", "platinum", Items.gearPlatinum);
        newForgeTag("gears", "quartz", Items.gearQuartz);
        newForgeTag("gears", "silver", Items.gearSilver);
        newForgeTag("gears", "steel", Items.gearSteel);
        newForgeTag("gears", "stone", Items.gearStone);
        newForgeTag("gears", "tin", Items.gearTin);
        newForgeTag("gears", "wood", Items.gearWood);
    }

    private void copyBlock(Tag<Block> blockTag) {
        if (!blockTag.getAllElements().isEmpty()) {
            copy(blockTag, new ItemTags.Wrapper(blockTag.getId()));
        }
    }

    private void newForgeTag(String name, Item... items) {
        ItemTags.Wrapper tag = new ItemTags.Wrapper(new ResourceLocation("forge", name));
        getBuilder(tag).add(items);
    }

    private void newForgeTag(String type, String material, Item... items) {
        ItemTags.Wrapper tag = new ItemTags.Wrapper(new ResourceLocation("forge", type + "/" + material));
        getBuilder(tag).add(items);
        ItemTags.Wrapper group = CollectionUtils.computeIfAbsent(mGroups, type, () -> new ItemTags.Wrapper(new ResourceLocation("forge", type)));
        getBuilder(group).add(tag);
    }

    private void newModTag(String name, Item... items) {
        ItemTags.Wrapper tag = new ItemTags.Wrapper(new ResourceLocation(ElementTimes.MODID, name));
        getBuilder(tag).add(items);
    }
}
