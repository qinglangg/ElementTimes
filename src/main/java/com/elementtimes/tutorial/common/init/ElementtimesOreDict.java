package com.elementtimes.tutorial.common.init;

import net.minecraftforge.oredict.OreDictionary;

/**
 * @author KSGFK create in 2019/5/6
 */
public class ElementtimesOreDict {
    public static void Init() {
        OreDictionary.registerOre("oreCopper", ElementtimesBlocks.Copperore);
        OreDictionary.registerOre("blockCopper", ElementtimesBlocks.Copperbillet);
        OreDictionary.registerOre("ingotCopper", ElementtimesItems.Copper);
        OreDictionary.registerOre("ingotSteel", ElementtimesItems.Steelingot);
        OreDictionary.registerOre("orePlatinum", ElementtimesBlocks.Platinumore);
        OreDictionary.registerOre("blockPlatinum", ElementtimesBlocks.Platinumblock);
        OreDictionary.registerOre("blockSteel", ElementtimesBlocks.Steelblock);
        OreDictionary.registerOre("ingotPlatinum", ElementtimesItems.Platinumingot);

        OreDictionary.registerOre("dustEmerald", ElementtimesItems.Greenstonepowder);
        OreDictionary.registerOre("dustRedstone", ElementtimesItems.Redstonepowder);
        OreDictionary.registerOre("dustLapisLazuli", ElementtimesItems.Bluestonepowder);
        OreDictionary.registerOre("dustIron", ElementtimesItems.Ironpower);
        OreDictionary.registerOre("dustGold", ElementtimesItems.Goldpowder);
        OreDictionary.registerOre("dustCoal", ElementtimesItems.Coalpowder);
        OreDictionary.registerOre("dustDiamond", ElementtimesItems.Diamondpowder);
        OreDictionary.registerOre("dustCopper", ElementtimesItems.Copperpowder);
        OreDictionary.registerOre("dustPlatinum", ElementtimesItems.Platinumorepowder);
    }
}
