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
    public static Item fiveElements;
    @ModItem
    public static Item endElement;
    @ModItem
    public static Item soilElement;
    @ModItem
    public static Item woodElement;
    @ModItem
    public static Item waterElement;
    @ModItem
    public static Item fireElement;
    @ModItem
    public static Item goldElement;
    @ModItem
    public static Item saplingEssence;
    @ModItem
    public static Item cropesSence;
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
    public static Item stoneIngot;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("ingotSteel")
    public static Item steelIngot;
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static Item calciumCarbonate;
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static Item calciumOxide;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    public static Item ingotColumn;
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
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
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("ingotCopper")
    public static Item copper;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("ingotSilver")
    public static Item silver;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustEmerald")
    public static Item greenstonePowder;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustSilver")
    public static Item silverPowder;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustRedstone")
    public static Item redstonePowder;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustLapisLazuli")
    public static Item bluestonePowder;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustIron")
    public static Item ironPower;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustGold")
    public static Item goldPowder;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustCoal")
    public static Item coalPowder;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustDiamond")
    public static Item diamondPowder;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustCopper")
    public static Item copperPowder;
    @ModItem
    public static Item spanner = new Spanner();
    @ModItem
    public static Item twiningSunCompound;
    @ModItem
    public static Item intermediateRubbingSunCompound;
    @ModItem
    public static Item largeRubbingSunCompound;
    @ModItem
    public static Item photoElement;
    @ModItem
    public static Item quartzPowder;
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
    public static Item money;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    public static Item sulfurOrePowder;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    public static Item sulfurPowder = new ItemFuel(800);
    @ModItem
    public static Item sucroseCharCoal = new ItemFuel(800);
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("ingotPlatinum")
    public static Item platinumIngot;
    @ModItem(creativeTab = ModCreativeTabs.Ore)
    @ModOreDict("dustPlatinum")
    public static Item platinumOrePowder;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    public static Item diamondIngot;
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static Item hydrogen = new ItemBottleFuel(1600);
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle steam;
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle sodiumBisulfite;
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle sodiumMetabisulfite;
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle sulfiteSolution;
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle sulphuricAcid;
    @ModItem(creativeTab = ModCreativeTabs.Chemical)
    public static ItemGlassBottle sodiumSulfiteSolution;
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
    public static Item rubber;
    @ModItem
    public static Item rubberRaw;
    @ModItem
    @ModOreDict("salt")
    public static Item salt;
    @ModItem
    @ModItem.Damageable(10)
    public static WoodenHalter woodenHalter;

    // gear

    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearWood")
    public static Item gearWood;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearQuartz")
    public static Item gearQuartz;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearStone")
    public static Item gearStone;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearCarbon")
    public static Item gearCarbon;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearGold")
    public static Item gearGold;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearSteel")
    public static Item gearSteel;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearDiamond")
    public static Item gearDiamond;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearIron")
    public static Item gearIron;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearPlatinum")
    public static Item gearPlatinum;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearCopper")
    public static Item gearCopper;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("gearSilver")
    public static Item gearSilver;


    // plate

    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateWood")
    public static Item platewood;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateStone")
    public static Item plateStone;
    @ModItem( creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateIron")
    public static Item plateIron;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateSteel")
    public static Item plateSteel;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateGold")
    public static Item plateGold;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateDiamond")
    public static Item plateDiamond;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("platePlatinum")
    public static Item platePlatinum;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateCopper")
    public static Item plateCopper;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateQuartz")
    public static Item plateQuartz;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateCarbon")
    public static Item plateCarbon;
    @ModItem(creativeTab = ModCreativeTabs.Industry)
    @ModOreDict("plateSilver")
    public static Item plateSilver;
}
