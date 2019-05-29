package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.annotation.ModItem;
import com.elementtimes.tutorial.annotation.ModOreDict;
import com.elementtimes.tutorial.common.creativetabs.ModCreativeTabs;
import com.elementtimes.tutorial.common.item.*;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;

@SuppressWarnings({"unused", "SpellCheckingInspection", "WeakerAccess", "ConstantConditions"})
public class ElementtimesItems {

    @ModItem(registerName = "fiveelements", unlocalizedName = "fiveelements")
    public static Item fiveElements = new ItemElementEnergy(() -> ElementtimesConfig.general.generaterFive);
    @ModItem(registerName = "endelement", unlocalizedName = "endelement")
    public static Item endElement = new ItemElementEnergy(() -> ElementtimesConfig.general.generaterEnd);
    @ModItem(registerName = "soilelement", unlocalizedName = "soilelement")
    public static Item soilElement = new ItemElementEnergy(() -> ElementtimesConfig.general.generaterSoilGen);
    @ModItem(registerName = "woodelement", unlocalizedName = "woodelement")
    public static Item woodElement = new ItemElementEnergy(() -> ElementtimesConfig.general.generaterWoodGen);
    @ModItem(registerName = "waterelement", unlocalizedName = "waterelement")
    public static Item waterElement = new ItemElementEnergy(() -> ElementtimesConfig.general.generaterWaterGen);
    @ModItem(registerName = "fireelement", unlocalizedName = "fireelement")
    public static Item fireElement = new ItemElementEnergy(() -> ElementtimesConfig.general.generaterFireGen);
    @ModItem(registerName = "goldelement", unlocalizedName = "goldelement")
    public static Item goldElement = new ItemElementEnergy(() -> ElementtimesConfig.general.generaterGoldGen);
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
    public static Item starchPowder = new ItemFood(1, 0.0F, false);
    @ModItem(registerName = "amylum", unlocalizedName = "amylum")
    public static Item amylum = new Amylum();
    @ModItem(registerName = "puremeat", unlocalizedName = "puremeat")
    public static Item puremeat = new ItemFood(4, 0f, false);
    @ModItem(registerName = "corn", unlocalizedName = "corn")
    public static Item corn = new Corn();
    @ModItem(registerName = "bakedcorn", unlocalizedName = "bakedcorn")
    public static Item bakedCorn = new ItemFood(5, 2.0F, false);
    @ModItem(registerName = "stoneingot", unlocalizedName = "stoneingot", creativeTab = ModCreativeTabs.Chemical)
    public static Item stoneIngot;
    @ModItem(registerName = "steelingot", unlocalizedName = "steelingot", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("ingotSteel")
    public static Item steelIngot;
    @ModItem(registerName = "calciumcarbonate", unlocalizedName = "calciumcarbonate", creativeTab = ModCreativeTabs.Chemical)
    public static Item calciumCarbonate;
    @ModItem(registerName = "calciumoxide", unlocalizedName = "calciumoxide", creativeTab = ModCreativeTabs.Chemical)
    public static Item calciumOxide;
    @ModItem(registerName = "ingotcolumn", unlocalizedName = "ingotcolumn", creativeTab = ModCreativeTabs.Industry)
    public static Item ingotColumn;
    @ModItem(registerName = "concrete", unlocalizedName = "concrete", creativeTab = ModCreativeTabs.Chemical)
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
    public static Item platinumSleeveHelmet = new ItemArmor(EnumHelper.addArmorMaterial("platinumsleeve", "elementtimes:platinum_sleeve", 2000, new int[] {3,6,8,3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 6.0F), 1, EntityEquipmentSlot.HEAD);
    @ModItem(registerName = "platinum_sleeve_body", unlocalizedName = "platinumsleevebody")
    public static Item platinumSleeveBody = new ItemArmor(EnumHelper.addArmorMaterial("platinumsleevebody", "elementtimes:platinum_sleeve", 2000, new int[] {3,6,8,3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 16.0F), 1, EntityEquipmentSlot.CHEST);
    @ModItem(registerName = "platinum_sleeve_leggings", unlocalizedName = "platinumsleeveleggings")
    public static Item platinumSleeveLeggings = new ItemArmor(EnumHelper.addArmorMaterial("platinumsleeve", "elementtimes:platinum_sleeve", 2000, new int[] {3,6,8,3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 12.0F), 1, EntityEquipmentSlot.LEGS);
    @ModItem(registerName = "platinum_sleeve_boots", unlocalizedName = "platinumsleeveboots")
    public static Item platinumSleeveBoots = new ItemArmor(EnumHelper.addArmorMaterial("platinumsleeve", "elementtimes:platinum_sleeve", 2000, new int[] {3,6,8,3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 6.0F), 1, EntityEquipmentSlot.FEET);
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
    public static Item photoElement = new ItemElementEnergy(() -> ElementtimesConfig.general.generaterSun);
    @ModItem(registerName = "quartzpowder", unlocalizedName = "quartzpowder")
    public static Item quartzPowder;
    @ModItem(registerName = "bighammer", unlocalizedName = "bighammer", creativeTab = ModCreativeTabs.Ore)
    @ModItem.Damageable(100)
    @ModItem.RetainInCrafting
    public static Item bigHammer = new Hammer();
    @ModItem(registerName = "smallhammer", unlocalizedName = "smallhammer", creativeTab = ModCreativeTabs.Ore)
    @ModItem.Damageable(10)
    @ModItem.RetainInCrafting
    public static Item smallHammer = new Hammer();
    @ModItem(registerName = "cornbroth", unlocalizedName = "cornbroth")
    public static Item cornBroth = new ItemSoup(20);
    @ModItem(registerName = "money", unlocalizedName = "money")
    public static Item money;
    @ModItem(registerName = "sulfurorepowder", unlocalizedName = "sulfurorepowder", creativeTab = ModCreativeTabs.Ore)
    public static Item sulfurOrePowder;
    @ModItem(registerName = "sulfurpowder", unlocalizedName = "sulfurpowder", creativeTab = ModCreativeTabs.Ore)
    public static Item sulfurPowder = new ItemFuel(800);
    @ModItem(registerName = "sucrosecharcoal", unlocalizedName = "sucrosecharcoal")
    public static Item sucroseCharCoal = new ItemFuel(800);
    @ModItem(registerName = "platinumingot", unlocalizedName = "platinumingot", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("ingotPlatinum")
    public static Item platinumIngot;
    @ModItem(registerName = "platinumorepowder", unlocalizedName = "platinumorepowder", creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustPlatinum")
    public static Item platinumOrePowder;
    @ModItem(registerName = "diamondingot", unlocalizedName = "diamondingot", creativeTab = ModCreativeTabs.Industry)
    public static Item diamondIngot;
    @ModItem(registerName = "hydrogen", unlocalizedName = "hydrogen", creativeTab = ModCreativeTabs.Chemical)
    public static Item hydrogen = new ItemBottleFuel(1600);
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
    public static Item platinumSword = new ItemSword(EnumHelper.addToolMaterial("platinumsword", 4, 500, 10.0F, 20.0F, 20));
    @ModItem(registerName = "platinumhoe", unlocalizedName = "platinumhoe")
    public static Item platinumHoe = new ItemHoe(EnumHelper.addToolMaterial("platinumhoe", 4, 500, 300.0F, 15.0F, 100));
    @ModItem(registerName = "platinumshovel", unlocalizedName = "platinumshovel")
    public static Item platinumShovel = new ItemSpade(EnumHelper.addToolMaterial("platinumshovel", 4, 500, 300.0F, 15.0F, 100));
    @ModItem(registerName = "platinumaxe", unlocalizedName = "platinumaxe")
    public static Item platinumAxe = new ItemAxe(EnumHelper.addToolMaterial("platinumaxe", 4, 800, 300.0F, 15.0F, 25),30.0F,0.0F) {};
    @ModItem(registerName = "platinumpick", unlocalizedName = "platinumpick")
    public static Item platinumPick = new ItemPickaxe(EnumHelper.addToolMaterial("platinumpick", 4, 500, 300.0F, 15.0F, 50)) {};

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

    // plate
    @ModItem(registerName = "platewood", unlocalizedName = "platewood", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateWood")
    public static Item platewood;
    @ModItem(registerName = "platestone", unlocalizedName = "platestone", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("platestone")
    public static Item plateStone;
    @ModItem(registerName = "plateiron", unlocalizedName = "plateiron", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateIron")
    public static Item plateIron;
    @ModItem(registerName = "platesteel", unlocalizedName = "platesteel", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateSteel")
    public static Item plateSteel;
    @ModItem(registerName = "plategold", unlocalizedName = "plategold", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateGold")
    public static Item plateGold;
    @ModItem(registerName = "platediamond", unlocalizedName = "platediamond", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateDiamond")
    public static Item plateDiamond;
    @ModItem(registerName = "plateplatinum", unlocalizedName = "plateplatinum", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("platePlatinum")
    public static Item platePlatinum;
    @ModItem(registerName = "platecopper", unlocalizedName = "platecopper", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateCopper")
    public static Item plateCopper;
    @ModItem(registerName = "platequartz", unlocalizedName = "platequartz", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateQuartz")
    public static Item plateQuartz;
    @ModItem(registerName = "platecarbon", unlocalizedName = "platecarbon", creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateCarbon")
    public static Item plateCarbon;


}
