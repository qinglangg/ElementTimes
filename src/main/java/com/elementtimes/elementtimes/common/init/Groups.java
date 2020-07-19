package com.elementtimes.elementtimes.common.init;

import com.elementtimes.elementcore.api.groups.ItemGroupDynamic;
import com.elementtimes.elementcore.api.groups.ItemGroupStatic;
import com.elementtimes.elementtimes.ElementTimes;
import net.minecraft.item.ItemGroup;

import java.util.Arrays;



public class Groups {

    public static ItemGroup Main = new ItemGroupDynamic(ElementTimes.MODID + ".main", 20,
            () -> Arrays.asList(Items.fireElement, Items.waterElement, Items.endElement,
                    Items.photoElement, Items.woodElement, Items.goldElement,
                    Items.soilElement, Items.fiveElements));

    public static ItemGroup Agriculture = new ItemGroupDynamic(ElementTimes.MODID + ".agriculture", 20,
            () -> Arrays.asList(Items.corn, Items.bakedCorn, Items.pureMeat));

    public static ItemGroup Chemical = new ItemGroupDynamic(ElementTimes.MODID + ".chemical", 20,
            () -> Arrays.asList(Items.SiC, Items.Mg, Items.Al, Items.AgBr,
                    Items.AgCl, Items.AgI, Items.Silicon, Items.stoneIngot,
                    Items.Al2O3_Na3AlF6, Items.CaSO4, Items.Na3AlF6,
                    Items.calciumCarbonate, Items.calciumacetylide, Items.calciumOxide,
                    Items.concrete, Items.salt));

    public static ItemGroup Industry = new ItemGroupStatic(ElementTimes.MODID + ".industry", () -> Items.spanner);

    public static ItemGroup Ore = new ItemGroupDynamic(ElementTimes.MODID + ".ore", 20,
            () -> Arrays.asList(com.elementtimes.elementtimes.common.init.blocks.Ore.oreSalt,
                    com.elementtimes.elementtimes.common.init.blocks.Ore.oreCopper,
                    com.elementtimes.elementtimes.common.init.blocks.Ore.orePlatinum,
                    com.elementtimes.elementtimes.common.init.blocks.Ore.oreSulfur,
                    com.elementtimes.elementtimes.common.init.blocks.Ore.oreUranium,
                    com.elementtimes.elementtimes.common.init.blocks.Ore.oreLead,
                    com.elementtimes.elementtimes.common.init.blocks.Ore.oreTin));
}
