package com.elementtimes.elementtimes.data.provider;

import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.CornCrop;
import com.elementtimes.elementtimes.common.init.Items;
import com.elementtimes.elementtimes.common.init.blocks.Agriculture;
import com.elementtimes.elementtimes.common.init.blocks.Ore;
import com.mojang.datafixers.util.Pair;
import net.minecraft.block.Block;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.LootTableProvider;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.*;
import net.minecraft.world.storage.loot.conditions.Alternative;
import net.minecraft.world.storage.loot.conditions.BlockStateProperty;
import net.minecraft.world.storage.loot.conditions.Inverted;
import net.minecraft.world.storage.loot.functions.ApplyBonus;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;



public class LootProvider extends LootTableProvider {

    public LootProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
    }

    @Override
    @Nonnull
    protected List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> getTables() {
        List<Pair<Supplier<Consumer<BiConsumer<ResourceLocation, LootTable.Builder>>>, LootParameterSet>> tables = new ArrayList<>();
        for (Block block : ElementTimes.CONTAINER.elements.blocks) {
            if (block == Agriculture.cornCrop) {
                tables.add(Pair.of(CornLootTables::new, LootParameterSets.BLOCK));
            } else if (block == Ore.oreSalt) {
                tables.add(Pair.of(SaltLootTables::new, LootParameterSets.BLOCK));
            } else if (block == Ore.oreSulfur) {
                tables.add(Pair.of(SulfurLootTables::new, LootParameterSets.BLOCK));
            }
        }
        return tables;
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @Nonnull ValidationResults results) {
        map.forEach((id, lootTable) -> LootTableManager.func_215302_a(results, id, lootTable, map::get));
    }

    static class CornLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            LootPool.Builder age7 = LootPool.builder()
                    .name("elementtimes.corn_7")
                    .rolls(ConstantRange.of(4))
                    .acceptFunction(ApplyBonus.func_215865_a(Enchantments.FORTUNE, 1))
                    .addEntry(ItemLootEntry.builder(Items.corn));
            LootPool.Builder age11 = LootPool.builder()
                    .name("elementtimes.corn_11")
                    .rolls(RandomValueRange.func_215837_a(1, 3))
                    .acceptFunction(ApplyBonus.func_215865_a(Enchantments.FORTUNE, 1))
                    .addEntry(ItemLootEntry.builder(Items.corn));
            LootPool.Builder normal = LootPool.builder()
                    .name("elementtimes.corn_normal")
                    .rolls(ConstantRange.of(1))
                    .addEntry(ItemLootEntry.builder(Items.corn));
            BlockStateProperty.Builder condition7 = BlockStateProperty.builder(Agriculture.cornCrop).with(CornCrop.AGE, 7);
            BlockStateProperty.Builder condition11 = BlockStateProperty.builder(Agriculture.cornCrop).with(CornCrop.AGE, 11);
            LootPool.Builder builder = new LootPool.Builder()
                    .name("elementtimes.corn")
                    .rolls(ConstantRange.of(1))
                    .addEntry(TableLootEntry
                            .builder(new ResourceLocation(ElementTimes.MODID, "blocks/corn_7"))
                            .acceptCondition(condition7))
                    .addEntry(TableLootEntry
                            .builder(new ResourceLocation(ElementTimes.MODID, "blocks/corn_11"))
                            .acceptCondition(condition11))
                    .addEntry(TableLootEntry
                            .builder(new ResourceLocation(ElementTimes.MODID, "blocks/corn_normal"))
                            .acceptCondition(Inverted.builder(Alternative.builder(condition7, condition11))));
            consumer.accept(new ResourceLocation(ElementTimes.MODID, "blocks/corn_7"), new LootTable.Builder().addLootPool(age7));
            consumer.accept(new ResourceLocation(ElementTimes.MODID, "blocks/corn_11"), new LootTable.Builder().addLootPool(age11));
            consumer.accept(new ResourceLocation(ElementTimes.MODID, "blocks/corn_normal"), new LootTable.Builder().addLootPool(normal));
            consumer.accept(Agriculture.cornCrop.getLootTable(), new LootTable.Builder().addLootPool(builder));
        }
    }

    static class SaltLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            LootPool.Builder pool = new LootPool.Builder()
                    .name("elementtimes.salt")
                    .rolls(RandomValueRange.func_215837_a(1, 2))
                    .acceptFunction(ApplyBonus.func_215869_a(Enchantments.FORTUNE))
                    .addEntry(ItemLootEntry.builder(Items.salt));
            consumer.accept(Ore.oreSalt.getLootTable(), new LootTable.Builder().addLootPool(pool));
        }
    }

    static class SulfurLootTables implements Consumer<BiConsumer<ResourceLocation, LootTable.Builder>> {

        @Override
        public void accept(BiConsumer<ResourceLocation, LootTable.Builder> consumer) {
            LootPool.Builder pool = new LootPool.Builder()
                    .name("elementtimes.sulfur")
                    .rolls(RandomValueRange.func_215837_a(2, 5))
                    .acceptFunction(ApplyBonus.func_215869_a(Enchantments.FORTUNE))
                    .addEntry(ItemLootEntry.builder(Items.sulfurOrePowder));
            consumer.accept(Ore.oreSulfur.getLootTable(), new LootTable.Builder().addLootPool(pool));
        }
    }
}
