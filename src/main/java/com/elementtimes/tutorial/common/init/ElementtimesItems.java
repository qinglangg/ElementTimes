package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.ModItem;
import com.elementtimes.elementcore.api.annotation.tools.ModOreDict;
import com.elementtimes.elementcore.api.annotation.tools.ModBurnTime;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.item.*;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.util.EnumHelper;

/**
 * 所有物品
 * @author 卿岚
 */
@SuppressWarnings({"unused", "SpellCheckingInspection", "WeakerAccess", "ConstantConditions"})
public class ElementtimesItems {

    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item fiveElements = new ItemRecord("sounds", new SoundEvent(new ResourceLocation(ElementTimes.MODID,"sounds"))) {}.setMaxStackSize(64);
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item endElement = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item soilElement = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item woodElement = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item waterElement = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item fireElement = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item goldElement = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Item cropesSence = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModItem.Tooltip("\u00a79Na")
    public static Item na = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("itemSilicon")
    public static Item coarseSilicon = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModItem.Tooltip("\u00a79SiC")
    public static Item SiC = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModItem.Tooltip("\u00a79Mg")
    public static Item Mg = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModItem.Tooltip("\u00a79Al")
    public static Item Al = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModItem.Tooltip("\u00a79AgBr")
    public static Item AgBr = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModItem.Tooltip("\u00a79AgCl")
    public static Item AgCl = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModItem.Tooltip("\u00a79AgI")
    public static Item AgI = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item slag = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModItem.Tooltip("\u00a79U3O8 U^238")
    public static Item bingU3O8 = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModItem.Tooltip("\u00a79U3O8 U^235")
    public static Item smallU3O8 = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("dustSand")
    public static Item sandpowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("dustStone")
    public static Item stonepowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModItem.Tooltip("\u00a79Si")
    public static Item Silicon = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    @ModBurnTime(800)
    public static Item bambooCharcoal = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    @ModBurnTime(600)
    public static Item amylum = new ModFood(5, 5.0F);
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Item corn = new Corn();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModItem.Tooltip("\u00a79CaCO3")
    @ModOreDict("ingotStone")
    public static Item stoneIngot = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    public static Item Al2O3_Na3AlF6 = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModItem.Tooltip("\u00a79CaSo4")
    public static Item CaSo4 = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModItem.Tooltip("\u00a79Na3AlF6")
    public static Item Na3AlF6 = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item siliconSolarCell = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item light_guide_fibre = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("ingotSteel")
    public static Item steelIngot = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item solarPanels = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item carbonRod = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    public static Item calciumacetylide = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    public static Item calciumCarbonate = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    public static Item calciumOxide = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item ingotColumn = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    public static Item concrete = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("ingotTin")
    public static Item tin = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("ingotLead")
    public static Item lead = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("ingotCopper")
    public static Item copper = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("ingotSilver")
    public static Item silver = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedGreenstone")
    public static Item greenstonePowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedSilver")
    public static Item silverPowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedOsmium")
    public static Item osmiumpowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedArdite")
    public static Item arditepowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedCobalt")
    public static Item cobaltpowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedNickel")
    public static Item nickelpowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModItem.Tooltip("\u00a79U3O8 U^235/238")
    @ModOreDict("crushedUranium")
    public static Item uraniumPowder = new Item();
	@ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModItem.Tooltip("\u00a79UO2 U^238")
    public static Item UO2 = new Item();
	@ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModItem.Tooltip("\u00a79UO2 U^235")
    public static Item UO2small = new Item();
	@ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
	@ModItem.Tooltip("\u00a79U U^238")
    public static Item uranium = new Item();
	@ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
	@ModItem.Tooltip("\u00a79U U^235")
    public static Item uranium235 = new Item();
	@ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedTin")
    public static Item tinPowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedLead")
    public static Item leadPowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedRedstone")
    public static Item redstonePowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedBluestone")
    public static Item bluestonePowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedIron")
    public static Item ironpowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedPurifiedIron")
    public static Item Fe2O3 = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedPurifiedCopper")
    public static Item CuO = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedGold")
    public static Item goldPowder= new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedCoal")
    public static Item coalPowder = new Item();
    @ModOreDict("crushedDiamond")
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    public static Item diamondPowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedCopper")
    public static Item copperPowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item twiningSunCompound = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item intermediateRubbingSunCompound = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item largeRubbingSunCompound = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item photoElement = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedQuartz")
    public static Item quartzPowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Item cornBroth = new ItemSoup(20);
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item money = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedSulfur")
    public static Item sulfurOrePowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("dustSulfur")
    @ModBurnTime(800)
    public static Item sulfurPowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    @ModBurnTime(800)
    public static Item sucroseCharCoal = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("ingotPlatinum")
    public static Item platinumIngot = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModOreDict("crushedPlatinum")
    public static Item platinumOrePowder = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item diamondIngot = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("rubber")
    public static Item rubber = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    @ModOreDict({"itemResin","materialResin"})
    public static Item rubberRaw = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.CHEMICAL)
    @ModOreDict("salt")
    public static Item salt = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Item starchball = new Starchball();

    // foods
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Item bakedCorn = new ModFood(5, 2.0F);
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Item starchPowder = new ModFood(1, 0.0F);
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Item puremeat = new ModFood(4, 1.0f);
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    public static Item saltedFish = new SaltedFish();

    // tools
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item spanner = new Spanner();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModItem.Damageable(120)
    @ModItem.RetainInCrafting
    public static Item bigHammer = new Hammer();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModItem.Damageable(40)
    @ModItem.RetainInCrafting
    public static Item mediumHammer = new Hammer();
    @ModItem(creativeTabKey = ElementtimesTabs.ORE)
    @ModItem.Damageable(10)
    @ModItem.RetainInCrafting
    public static Item smallHammer = new Hammer();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item elementShovel = new Elementshovel();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item elementshears = new Elementshears();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item elementHoe = new Elementhoe();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item platinumSword = new ItemSword(EnumHelper.addToolMaterial("platinumsword", 4, 5000, 200.0F, 30.0F, 30));
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item platinumHoe = new ItemHoe(EnumHelper.addToolMaterial("platinumhoe", 4, 5000, 100.0F, 10.0F, 30));
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item platinumShovel = new ItemSpade(EnumHelper.addToolMaterial("platinumshovel", 4, 5000, 150.0F, 20.0F, 30));
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item platinumAxe = new ItemAxe(EnumHelper.addToolMaterial("platinumaxe", 4, 8000, 50.0F, 15.0F, 30),50.0F,30.0F) {};
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item platinumPick = new ItemPickaxe(EnumHelper.addToolMaterial("platinumpick", 4, 8000, 150.0F, 20.0F, 30)) {};
    @ModItem(creativeTabKey = ElementtimesTabs.AGRICULTURE)
    @ModItem.Damageable(10)
    public static WoodenHalter woodenHalter = new WoodenHalter();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN,
             registerName = "element_sleeve_helmet", unlocalizedName = "elementsleevehelmet")
    public static Item elementSleeveHelmet = new ElementSleeveHelmet();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN,
             registerName = "element_sleeve_body", unlocalizedName = "elementsleevebody")
    public static Item elementSleeveBody = new ElementSleeveBody();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN,
             registerName = "element_sleeve_leggings", unlocalizedName = "elementsleeveleggings")
    public static Item elementSleeveLeggings = new ElementSleeveLeggings();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN,
             registerName = "element_sleeve_boots", unlocalizedName = "elementsleeveboots")
    public static Item elementSleeveBoots = new ElementSleeveBoots();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN,
             registerName = "platinum_sleeve_helmet", unlocalizedName = "platinumsleevehelmet")
    public static Item platinumSleeveHelmet = new ItemArmor(EnumHelper.addArmorMaterial("platinumsleeve", "elementtimes:platinum_sleeve", 2000, new int[] {3,6,8,3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 6.0F), 1, EntityEquipmentSlot.HEAD);
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN,
             registerName = "platinum_sleeve_body", unlocalizedName = "platinumsleevebody")
    public static Item platinumSleeveBody = new ItemArmor(EnumHelper.addArmorMaterial("platinumsleevebody", "elementtimes:platinum_sleeve", 2000, new int[] {3,6,8,3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 16.0F), 1, EntityEquipmentSlot.CHEST);
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN,
             registerName = "platinum_sleeve_leggings", unlocalizedName = "platinumsleeveleggings")
    public static Item platinumSleeveLeggings = new ItemArmor(EnumHelper.addArmorMaterial("platinumsleeve", "elementtimes:platinum_sleeve", 2000, new int[] {3,6,8,3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 12.0F), 1, EntityEquipmentSlot.LEGS);
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN,
             registerName = "platinum_sleeve_boots", unlocalizedName = "platinumsleeveboots")
    public static Item platinumSleeveBoots = new ItemArmor(EnumHelper.addArmorMaterial("platinumsleeve", "elementtimes:platinum_sleeve", 2000, new int[] {3,6,8,3}, 15, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 6.0F), 1, EntityEquipmentSlot.FEET);
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item elementSword = new Elementsword();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item elementAxe = new Elementaxe();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item elementbow = new Elementbow();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item elementPickaxe = new Elementpickaxe();
    @ModItem(creativeTabKey = ElementtimesTabs.MAIN)
    public static Item elementFishPole = new ElementFishPole();

    // gear
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearWood")
    public static Item gearWood = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearQuartz")
    public static Item gearQuartz = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearStone")
    public static Item gearStone = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearCarbon")
    public static Item gearCarbon = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearGold")
    public static Item gearGold = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearSteel")
    public static Item gearSteel = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearDiamond")
    public static Item gearDiamond = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearIron")
    public static Item gearIron = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearPlatinum")
    public static Item gearPlatinum = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearCopper")
    public static Item gearCopper = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearObsidian")
    public static Item gearObsidian = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearLead")
    public static Item gearLead = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearTin")
    public static Item gearTin = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearSilver")
    public static Item gearSilver = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("gearAdamas")
    public static Item gearAdamas = new Item();

    // plate
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateWood")
    public static Item platewood = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateStone")
    public static Item plateStone = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateIron")
    public static Item plateIron = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateSteel")
    public static Item plateSteel = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateGold")
    public static Item plateGold = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateDiamond")
    public static Item plateDiamond = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("platePlatinum")
    public static Item platePlatinum = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateCopper")
    public static Item plateCopper = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateQuartz")
    public static Item plateQuartz = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateCarbon")
    public static Item plateCarbon = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateObsidian")
    public static Item plateObsidian = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateLead")
    public static Item plateLead = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateTin")
    public static Item plateTin = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateSilver")
    public static Item plateSilver = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    @ModOreDict("plateAdamas")
    public static Item plateAdamas = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item aluminiumPlate = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item siliconPlate  = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item cpu = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item cpuUp  = new Item();
    @ModItem(creativeTabKey = ElementtimesTabs.INDUSTRY)
    public static Item cpuDown  = new Item();
}
