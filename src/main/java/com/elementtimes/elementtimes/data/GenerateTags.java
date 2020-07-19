package com.elementtimes.elementtimes.data;

import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.data.provider.BlockTagProvider;
import com.elementtimes.elementtimes.data.provider.LootProvider;
import com.elementtimes.elementtimes.data.provider.ItemTagProvider;
import com.elementtimes.elementtimes.data.provider.RecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;



@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class GenerateTags {

    @SubscribeEvent
    public static void genData(GatherDataEvent event) {
        // EC 初始化
        ElementTimes.CONTAINER.elements();
        DataGenerator generator = event.getGenerator();
        BlockTagProvider blockTags;
        generator.addProvider(blockTags = new BlockTagProvider(generator));
        generator.addProvider(new ItemTagProvider(generator, blockTags));
        generator.addProvider(new RecipeProvider(generator));
        generator.addProvider(new LootProvider(generator));
    }
}
