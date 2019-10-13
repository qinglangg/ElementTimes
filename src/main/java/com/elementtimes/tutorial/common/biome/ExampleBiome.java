package com.elementtimes.tutorial.common.biome;

import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeManager;

import javax.annotation.Nonnull;

/**
 * @author luqin2007
 */
public class ExampleBiome extends Biome {

    public ExampleBiome() {
        super(new BiomeProperties("test").setWaterColor(0xFFFF0000).setBaseHeight(-1f).setHeightVariation(.1f));
        initSpawnable();
        initDecorator();
        fillerBlock = ElementtimesFluids.seawater.getBlock().getDefaultState();

        BiomeManager.oceanBiomes.add(this);
        BiomeManager.addSpawnBiome(this);
    }

    private void initSpawnable() {
        spawnableCreatureList.clear();
        spawnableCaveCreatureList.clear();
        spawnableMonsterList.clear();
        spawnableWaterCreatureList.clear();
        spawnableWaterCreatureList.add(new SpawnListEntry(EntitySquid.class, 10, 4, 4));
    }

    private void initDecorator() {
        decorator.flowersPerChunk = 0;
        decorator.grassPerChunk = 0;
        decorator.gravelPatchesPerChunk = 0;
        decorator.sandPatchesPerChunk = 0;
        decorator.clayPerChunk = 0;
    }

    @Nonnull
    @Override
    public TempCategory getTempCategory() {
        return TempCategory.OCEAN;
    }
}
