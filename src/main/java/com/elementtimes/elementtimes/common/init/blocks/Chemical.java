package com.elementtimes.elementtimes.common.init.blocks;

import com.elementtimes.elementcore.api.annotation.ModElement;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.ItemProps;
import com.elementtimes.elementcore.api.annotation.tools.ModTooltips;
import com.elementtimes.elementtimes.common.block.stand.*;
import com.elementtimes.elementtimes.common.init.Groups;
import com.elementtimes.elementtimes.common.block.stand.te.TileBeaker;
import com.elementtimes.elementtimes.common.block.stand.te.TileCrucible;
import com.elementtimes.elementtimes.common.block.stand.te.TileEvaporatingDish;
import com.elementtimes.elementtimes.common.block.stand.module.ModuleBeaker;
import com.elementtimes.elementtimes.common.block.stand.module.ModuleCrucible;
import com.elementtimes.elementtimes.common.block.stand.module.ModuleEvaporatingDish;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;



@ModElement(itemProp = @ItemProps(group = @Getter(value = Groups.class, name = "Chemical")))
public class Chemical {

    @ModTooltips("\u00a79CaCO3")
    public static Block stoneBlock = new Block(prop(2, 50, 20));
    public static Block cement = new Block(prop(3, 200, 50));
    public static Block cementAndSteelBarMixture = new Block(prop(4, 1000, 150));

    public static Block supportStand = new SupportStand();
    public static Block alcoholLamp = new AlcoholLamp();
    public static Block evaporatingDish = new BaseModuleBlock<>(ModuleEvaporatingDish.KEY, TileEvaporatingDish.class, 0.25, 0, 0.25, 0.75, 0.25, 0.75);
    public static Block crucible = new BaseModuleBlock<>(ModuleCrucible.KEY, TileCrucible.class);
    public static Block beaker = new BaseModuleBlock<>(ModuleBeaker.KEY, TileBeaker.class, 0.3125, 0, 0.3125, 0.6875, 0.4375, 0.6875);

    private static Block.Properties prop(int level, float hardness, float resistance) {
        return Block.Properties.create(Material.ROCK).harvestTool(ToolType.PICKAXE).harvestLevel(level).hardnessAndResistance(hardness, resistance);
    }
}
