package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.annotation.ModItem;
import com.elementtimes.tutorial.annotation.ModOreDict;
import com.elementtimes.tutorial.common.creativetabs.ModCreativeTabs;
import com.elementtimes.tutorial.common.item.*;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraftforge.common.util.EnumHelper;

/**
 * 所有物品
 * @author KSGFK
 */
@SuppressWarnings({"unused", "SpellCheckingInspection", "WeakerAccess", "ConstantConditions"})
public class ElementtimesItems {

    @ModItem
    public static Item fiveElements = new Item();
    @ModItem
    public static Item endElement = new Item();
    @ModItem
    public static Item soilElement = new Item();
    @ModItem
    public static Item woodElement = new Item();
    @ModItem
    public static Item waterElement = new Item();
    @ModItem
    public static Item fireElement = new Item();
    @ModItem
    public static Item goldElement = new Item();
    @ModItem
    public static Item saplingEssence = new Item();
    @ModItem
    public static Item cropesSence = new Item();
    @ModItem
    public static Item elementSword = new Elementsword();
    @ModItem
    public static Item elementAxe = new Elementaxe();
    @ModItem
    public static Item elementPickaxe = new Elementpickaxe();
    @ModItem
    public static Item starchPowder = new ItemFood(1, 0.0F, false);
    @ModItem
    public static Item amylum = new Amylum();
    @ModItem
    public static Item puremeat = new ItemFood(4, 0f, false);
    @ModItem
    public static Item corn = new Corn();
    @ModItem
    public static Item bakedCorn = new ItemFood(5, 2.0F, false);
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static Item stoneIngot = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("ingotSteel")
    public static Item steelIngot = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static Item calciumCarbonate = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static Item calciumOxide = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    public static Item ingotColumn = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static Item concrete = new Item();
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
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("ingotCopper")
    public static Item copper = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("ingotSilver")
    public static Item silver = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustEmerald")
    public static Item greenstonePowder = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustSilver")
    public static Item silverPowder = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustRedstone")
    public static Item redstonePowder = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustLapisLazuli")
    public static Item bluestonePowder = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustIron")
    public static Item ironPower = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustGold")
    public static Item goldPowder;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustCoal")
    public static Item coalPowder = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustDiamond")
    public static Item diamondPowder = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustCopper")
    public static Item copperPowder = new Item();
    @ModItem
    public static Item spanner = new Spanner();
    @ModItem
    public static Item twiningSunCompound = new Item();
    @ModItem
    public static Item intermediateRubbingSunCompound = new Item();
    @ModItem
    public static Item largeRubbingSunCompound = new Item();
    @ModItem
    public static Item photoElement = new Item();
    @ModItem
    public static Item quartzPowder = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModItem.Damageable(100)
    @ModItem.RetainInCrafting
    public static Item bigHammer = new Hammer();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModItem.Damageable(10)
    @ModItem.RetainInCrafting
    public static Item smallHammer = new Hammer();
    @ModItem
    public static Item cornBroth = new ItemSoup(20);
    @ModItem
    public static Item money = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    public static Item sulfurOrePowder = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    public static Item sulfurPowder = new ItemFuel(800);
    @ModItem
    public static Item sucroseCharCoal = new ItemFuel(800);
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("ingotPlatinum")
    public static Item platinumIngot = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustPlatinum")
    public static Item platinumOrePowder = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    public static Item diamondIngot = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static Item hydrogen = new ItemBottleFuel(1600);
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle steam = new ItemGlassBottle();
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle sodiumBisulfite = new ItemGlassBottle();
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle sodiumMetabisulfite = new ItemGlassBottle();
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle sulfiteSolution = new ItemGlassBottle();
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle sulphuricAcid = new ItemGlassBottle();
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle sodiumSulfiteSolution = new ItemGlassBottle();
    @ModItem
    public static Item elementShovel = new Elementshovel();
    @ModItem
    public static Item elementHoe = new Elementhoe();
    @ModItem
    public static Item platinumSword = new ItemSword(EnumHelper.addToolMaterial("platinumsword", 4, 500, 10.0F, 20.0F, 20));
    @ModItem
    public static Item platinumHoe = new ItemHoe(EnumHelper.addToolMaterial("platinumhoe", 4, 500, 300.0F, 15.0F, 100));
    @ModItem
    public static Item platinumShovel = new ItemSpade(EnumHelper.addToolMaterial("platinumshovel", 4, 500, 300.0F, 15.0F, 100));
    @ModItem
    public static Item platinumAxe = new ItemAxe(EnumHelper.addToolMaterial("platinumaxe", 4, 800, 300.0F, 15.0F, 25),30.0F,0.0F) {};
    @ModItem
    public static Item platinumPick = new ItemPickaxe(EnumHelper.addToolMaterial("platinumpick", 4, 500, 300.0F, 15.0F, 50)) {};
    @ModItem
    @ModOreDict("rubber")
    public static Item rubber = new Item();
    @ModItem
    public static Item rubberRaw = new Item();
    @ModItem
    @ModOreDict("salt")
    public static Item salt = new Item();
    @ModItem
    @ModItem.Damageable(10)
    public static WoodenHalter woodenHalter = new WoodenHalter();

    // gear

    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearWood")
    public static Item gearWood = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearQuartz")
    public static Item gearQuartz = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearStone")
    public static Item gearStone = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearCarbon")
    public static Item gearCarbon = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearGold")
    public static Item gearGold = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearSteel")
    public static Item gearSteel = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearDiamond")
    public static Item gearDiamond = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearIron")
    public static Item gearIron = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearPlatinum")
    public static Item gearPlatinum = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearCopper")
    public static Item gearCopper = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearSilver")
    public static Item gearSilver = new Item();


    // plate

    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateWood")
    public static Item platewood = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateStone")
    public static Item plateStone = new Item();
    @ModItem( creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateIron")
    public static Item plateIron = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateSteel")
    public static Item plateSteel = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateGold")
    public static Item plateGold = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateDiamond")
    public static Item plateDiamond = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("platePlatinum")
    public static Item platePlatinum = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateCopper")
    public static Item plateCopper = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateQuartz")
    public static Item plateQuartz = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateCarbon")
    public static Item plateCarbon = new Item();
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateSilver")
    public static Item plateSilver = new Item();
}
