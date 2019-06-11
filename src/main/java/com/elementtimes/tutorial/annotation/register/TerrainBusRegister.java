package com.elementtimes.tutorial.annotation.register;

import com.elementtimes.tutorial.annotation.enums.GenType;
import com.elementtimes.tutorial.annotation.processor.ModBlockLoader;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 区块生成相关
 * @author luqin2007
 */
public class TerrainBusRegister {

    @SubscribeEvent
    public static void onGenerateTree(DecorateBiomeEvent.Post event) {
        if (!event.getWorld().isRemote) {
            for (WorldGenerator generator: ModBlockLoader.WORLD_GENERATORS.get(GenType.Tree)) {
                ChunkPos chunkPos = event.getChunkPos();
                if (TerrainGen.decorate(event.getWorld(), event.getRand(), chunkPos, DecorateBiomeEvent.Decorate.EventType.TREE)) {
                    generator.generate(event.getWorld(), event.getRand(), new BlockPos(chunkPos.getXStart(), 0, chunkPos.getZStart()));
                }
            }
        }
    }
}
