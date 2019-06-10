package com.elementtimes.tutorial.annotation.register;

import com.elementtimes.tutorial.annotation.enums.GenType;
import com.elementtimes.tutorial.annotation.processor.ModBlockLoader;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.OreGenEvent;
import net.minecraftforge.event.terraingen.TerrainGen;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * 处理世界生成事件
 * @author luqin2007
 */
public class OreBusRegister {

    @SubscribeEvent
    public static void onGenerateOre(OreGenEvent.Post event) {
        if (!event.getWorld().isRemote) {
            for (WorldGenerator generator: ModBlockLoader.sGenerators.get(GenType.Ore)) {
                if (TerrainGen.generateOre(event.getWorld(), event.getRand(), generator, event.getPos(), OreGenEvent.GenerateMinable.EventType.CUSTOM)) {
                    generator.generate(event.getWorld(), event.getRand(), event.getPos());
                }
            }
        }
    }
}
