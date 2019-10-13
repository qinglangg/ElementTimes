package com.elementtimes.tutorial.common.event;

/**
 * 群系结构生成
 * @author luqin2007
 */
public class BiomeStructureEvent {

//    @SubscribeEvent
//    public static void onStructureGenInit(InitMapGenEvent event) {
//        if (event.getType() == InitMapGenEvent.EventType.OCEAN_MONUMENT && event.getOriginalGen() instanceof StructureOceanMonument) {
//            event.setNewGen(new StructureOceanMonument() {
//
//                @Override
//                protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ) {
//                    int i = chunkX;
//                    int j = chunkZ;
//                    int spacing = 32;
//                    int separation = 5;
//
//                    if (chunkX < 0) {
//                        chunkX -= spacing - 1;
//                    }
//
//                    if (chunkZ < 0) {
//                        chunkZ -= spacing - 1;
//                    }
//
//                    int k = chunkX / spacing;
//                    int l = chunkZ / spacing;
//                    Random random = this.world.setRandomSeed(k, l, 10387313);
//                    k = k * spacing;
//                    l = l * spacing;
//                    k = k + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;
//                    l = l + (random.nextInt(spacing - separation) + random.nextInt(spacing - separation)) / 2;
//
//                    if (i == k && j == l) {
//                        int x = i * 16 + 8;
//                        int z = j * 16 + 8;
//
//                        List<Biome> biomeSpawn = new ArrayList<>(SPAWN_BIOMES.size() + 1);
//                        biomeSpawn.addAll(SPAWN_BIOMES);
//                        biomeSpawn.add(ElementtimesBiomes.sea);
//                        if (!this.world.getBiomeProvider().areBiomesViable(x, z, 16, biomeSpawn)) {
//                            return false;
//                        }
//
//                        List<Biome> biomeWater = new ArrayList<>(WATER_BIOMES.size() + 1);
//                        biomeSpawn.addAll(WATER_BIOMES);
//                        biomeSpawn.add(ElementtimesBiomes.sea);
//
//                        return this.world.getBiomeProvider().areBiomesViable(x, z, 29, biomeWater);
//                    }
//
//                    return false;
//                }
//            });
//        }
//    }
}
