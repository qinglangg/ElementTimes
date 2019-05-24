package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.annotation.ModItem;
import com.elementtimes.tutorial.annotation.ModOreDict;
import com.elementtimes.tutorial.common.creativetabs.ModCreativeTabs;
import com.elementtimes.tutorial.common.item.*;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemGlassBottle;
import net.minecraft.item.ItemSoup;

public class ElementtimesItems {

    @ModItem(registerName = "fiveelements", unlocalizedName = "fiveelements")
    public static Item fiveElements = new Fiveelements();
    @ModItem(registerName = "endelement", unlocalizedName = "endelement")
    public static Item endElement = new Endelement();
    @ModItem(registerName = "soilelement", unlocalizedName = "soilelement")
    public static Item soilElement = new Soilelement();
    @ModItem(registerName = "woodelement", unlocalizedName = "woodelement")
    public static Item woodElement = new Woodelement();
    @ModItem(registerName = "waterelement", unlocalizedName = "waterelement")
    public static Item waterElement = new Waterelement();
    @ModItem(registerName = "fireelement", unlocalizedName = "fireelement")
    public static Item fireElement = new Fireelement();
    @ModItem(registerName = "goldelement", unlocalizedName = "goldelement")
    public static Item goldElement = new Goldelement();
    @ModItem(registerName = "saplingessence", unlocalizedName = "saplingessence")
    public static Item saplingEssence;
    @ModItem(registerName = "cropessence", unlocalizedName = "cropessence")
    public static Item cropesSence;
    @ModItem(registerName = "elementsword", unlocalizedName = "elementsword")
    public static Item elementSword = new Elementsword();
    @ModItem(registerName = "elementaxe", unlocalizedName = "elementaxe")
    public static Item elementAxe = new Elementaxe();
    @ModItem(registerName = "elementpickaxe", unlocalizedName = "elementpickaxe")
    public static Item elementPickaxe = new Elementpickaxe();
    @ModItem(registerName = "starchpowder", unlocalizedName = "starchpowder")
    public static Item starchPowder = new Starchpowder();
    @ModItem(registerName = "amylum", unlocalizedName = "amylum")
    public static Item amylum = new Amylum();
    @ModItem(registerName = "puremeat", unlocalizedName = "puremeat")
    public static Item puremeat = new ItemFood(4, 0f, false);
    @ModItem(registerName = "corn", unlocalizedName = "corn")
    public static Item corn = new Corn();
    @ModItem(registerName = "bakedcorn", unlocalizedName = "bakedcorn")
    public static Item bakedCorn = new Bakedcorn();
    @ModItem(registerName = "stoneingot", unlocalizedName = "stoneingot")
    public static Item stoneIngot;
    @ModItem(registerName = "steelingot", unlocalizedName = "steelingot")
    @ModOreDict("ingotSteel")
    public static Item steelIngot;
    @ModItem(registerName = "calciumcarbonate", unlocalizedName = "calciumcarbonate")
    public static Item calciumCarbonate;
    @ModItem(registerName = "calciumoxide", unlocalizedName = "calciumoxide")
    public static Item calciumOxide;
    @ModItem(registerName = "ingotcolumn", unlocalizedName = "ingotcolumn")
    public static Item ingotColumn;
    @ModItem(registerName = "concrete", unlocalizedName = "concrete")
    public static Item concrete;
    @ModItem(registerName = "element_sleeve_helmet", unlocalizedName = "elementsleevehelmet")
    public static Item elementSleeveHelmet = new ElementSleeveHelmet();
    @ModItem(registerName = "element_sleeve_body", unlocalizedName = "elementsleevebody")
    public static Item elementSleeveBody = new ElementSleeveBody();
    @ModItem(registerName = "element_sleeve_leggings", unlocalizedName = "elementsleeveleggings")
    public static Item elementSleeveLeggings = new ElementSleeveLeggings();
    @ModItem(registerName = "element_sleeve_boots", unlocalizedName = "elementsleeveboots")
    public static Item elementSleeveBoots = new ElementSleeveBoots();
    @ModItem(registerName = "platinum_sleeve_helmet", unlocalizedName = "platinumsleevehelmet")
    public static Item platinumSleeveHelmet = new PlatinumSleeveHelmet();
    @ModItem(registerName = "platinum_sleeve_body", unlocalizedName = "platinumsleevebody")
    public static Item platinumSleeveBody = new PlatinumSleeveBody();
    @ModItem(registerName = "platinum_sleeve_leggings", unlocalizedName = "platinumsleeveleggings")
    public static Item platinumSleeveLeggings = new PlatinumSleeveLeggings();
    @ModItem(registerName = "platinum_sleeve_boots", unlocalizedName = "platinumsleeveboots")
    public static Item platinumSleeveBoots = new PlatinumSleeveBoots();
    @ModItem(registerName = "copper", unlocalizedName = "copper", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("ingotCopper")
    public static Item copper;
    @ModItem(registerName = "greenstonepowder", unlocalizedName = "greenstonepowder", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustEmerald")
    public static Item greenstonePowder;
    @ModItem(registerName = "redstonepowder", unlocalizedName = "redstonepowder", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustRedstone")
    public static Item redstonePowder;
    @ModItem(registerName = "bluestonepowder", unlocalizedName = "bluestonepowder", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustLapisLazuli")
    public static Item bluestonePowder;
    @ModItem(registerName = "ironpower", unlocalizedName = "ironpower", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustIron")
    public static Item ironPower;
    @ModItem(registerName = "goldpowder", unlocalizedName = "goldpowder", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustGold")
    public static Item goldPowder;
    @ModItem(registerName = "coalpowder", unlocalizedName = "coalpowder", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustCoal")
    public static Item coalPowder;
    @ModItem(registerName = "diamondpowder", unlocalizedName = "diamondpowder", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustDiamond")
    public static Item diamondPowder;
    @ModItem(registerName = "copperpowder", unlocalizedName = "copperpowder", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustCopper")
    public static Item copperPowder;
    @ModItem(registerName = "spanner", unlocalizedName = "spanner")
    public static Item spanner = new Spanner();
    @ModItem(registerName = "twiningsuncompound", unlocalizedName = "twiningsuncompound")
    public static Item twiningSunCompound;
    @ModItem(registerName = "intermediaterubbingsuncompound", unlocalizedName = "intermediaterubbingsuncompound")
    public static Item intermediateRubbingSunCompound;
    @ModItem(registerName = "largerubbingsuncompound", unlocalizedName = "largerubbingsuncompound")
    public static Item largeRubbingSunCompound;
    @ModItem(registerName = "photoelement", unlocalizedName = "photoelement")
    public static Item photoElement = new Photoelement();
    @ModItem(registerName = "quartzpowder", unlocalizedName = "quartzpowder")
    public static Item quartzPowder;
    @ModItem(registerName = "bighammer", unlocalizedName = "bighammer", creativeTab = ModCreativeTabs.Ore)
    public static Item bigHammer = new Bighammer();
    @ModItem(registerName = "smallhammer", unlocalizedName = "smallhammer")
    public static Item smallHammer = new Smallhammer();
    @ModItem(registerName = "cornbroth", unlocalizedName = "cornbroth")
    public static Item cornBroth = new ItemSoup(20);
    @ModItem(registerName = "money", unlocalizedName = "money")
    public static Item money;
    @ModItem(registerName = "sulfurorepowder", unlocalizedName = "sulfurorepowder", creativeTab = ModCreativeTabs.Ore)
    public static Item sulfurOrePowder;
    @ModItem(registerName = "sulfurpowder", unlocalizedName = "sulfurpowder", creativeTab = ModCreativeTabs.Ore)
    public static Item sulfurPowder = new Sulfurpowder();
    @ModItem(registerName = "sucrosecharcoal", unlocalizedName = "sucrosecharcoal")
    public static Item sucroseCharCoal = new Sucrosecharcoal();
    @ModItem(registerName = "platinumingot", unlocalizedName = "platinumingot", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("ingotPlatinum")
    public static Item platinumIngot;
    @ModItem(registerName = "platinumorepowder", unlocalizedName = "platinumorepowder", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustPlatinum")
    public static Item platinumOrePowder;
    @ModItem(registerName = "diamondingot", unlocalizedName = "diamondingot", creativeTab = ModCreativeTabs.Ore)
    public static Item diamondIngot;
    @ModItem(registerName = "hydrogen", unlocalizedName = "hydrogen", creativeTab = ModCreativeTabs.Chemical)
    public static Item hydrogen = new Hydrogen();
    @ModItem(registerName = "steam", unlocalizedName = "steam", creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle steam;
    @ModItem(registerName = "sulfitesolution", unlocalizedName = "sulfitesolution", creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle sulfiteSolution;
    @ModItem(registerName = "sulphuricacid", unlocalizedName = "sulphuricacid")
    public static ItemGlassBottle sulphuricAcid;
    @ModItem(registerName = "sodiumsulfitesolution", unlocalizedName = "sodiumsulfitesolution", creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle sodiumSulfiteSolution;
    @ModItem(registerName = "elementshovel", unlocalizedName = "elementshovel")
    public static Item slementShovel = new Elementshovel();
    @ModItem(registerName = "elementhoe", unlocalizedName = "elementhoe")
    public static Item elementHoe = new Elementhoe();
    @ModItem(registerName = "platinumsword", unlocalizedName = "platinumsword")
    public static Item platinumSword = new Platinumsword();
    @ModItem(registerName = "platinumhoe", unlocalizedName = "platinumhoe")
    public static Item platinumHoe = new Platinumhoe();
    @ModItem(registerName = "platinumshovel", unlocalizedName = "platinumshovel")
    public static Item platinumShovel = new Platinumshovel();
    @ModItem(registerName = "platinumaxe", unlocalizedName = "platinumaxe")
    public static Item platinumAxe = new Platinumaxe();
    @ModItem(registerName = "platinumpick", unlocalizedName = "platinumpick")
    public static Item platinumPick = new Platinumpick();

    // gear
    @ModItem(registerName = "gearwood", unlocalizedName = "gearwood", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearWood")
    public static Item gearWood;
    @ModItem(registerName = "gearquartz", unlocalizedName = "gearquartz", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearQuartz")
    public static Item gearQuartz;
    @ModItem(registerName = "gearstone", unlocalizedName = "gearstone", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearStone")
    public static Item gearStone;
    @ModItem(registerName = "gearcarbon", unlocalizedName = "gearcarbon", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearCarbon")
    public static Item gearCarbon;
    @ModItem(registerName = "geargold", unlocalizedName = "geargold", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearGold")
    public static Item gearGold;
    @ModItem(registerName = "gearsteel", unlocalizedName = "gearsteel", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearSteel")
    public static Item gearSteel;
    @ModItem(registerName = "geardiamond", unlocalizedName = "geardiamond", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearDiamond")
    public static Item gearDiamond;
    @ModItem(registerName = "geariron", unlocalizedName = "geariron", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearIron")
    public static Item gearIron;
    @ModItem(registerName = "gearplatinum", unlocalizedName = "gearplatinum", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearPlatinum")
    public static Item gearPlatinum;
    @ModItem(registerName = "gearcopper", unlocalizedName = "gearcopper", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearCopper")
    public static Item gearCopper;
}
