package com.elementtimes.elementtimes.common.init;

import com.elementtimes.elementcore.api.annotation.ModElement;
import com.elementtimes.elementcore.api.annotation.tools.ModBurnTime;
import com.elementtimes.elementcore.api.annotation.tools.ModTooltips;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.item.*;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;



@ModElement
public class Items {

    public static Item fiveElements = new MusicDiscItem(15, new SoundEvent(new ResourceLocation(ElementTimes.MODID,"sounds")), prop(Groups.Main)) {};
    public static Item endElement = new Item(prop(Groups.Main));
    public static Item soilElement = new Item(prop(Groups.Main));
    public static Item woodElement = new Item(prop(Groups.Main));
    public static Item waterElement = new Item(prop(Groups.Main));
    public static Item fireElement = new Item(prop(Groups.Main));
    public static Item goldElement = new Item(prop(Groups.Main));
    public static Item cropesSence = new Item(prop(Groups.Agriculture));
    public static Item coarseSilicon = new Item(prop(Groups.Industry));
    @ModTooltips("\u00a79Na")
    public static Item Na = new Item(prop(Groups.Chemical));
    @ModTooltips("\u00a79SiC")
    public static Item SiC = new Item(prop(Groups.Chemical));
    @ModTooltips("\u00a79Mg")
    public static Item Mg = new Item(prop(Groups.Chemical));
    @ModTooltips("\u00a79Al")
    public static Item Al = new Item(prop(Groups.Chemical));
    @ModTooltips("\u00a79AgBr")
    public static Item AgBr = new Item(prop(Groups.Chemical));
    @ModTooltips("\u00a79AgCl")
    public static Item AgCl = new Item(prop(Groups.Chemical));
    @ModTooltips("\u00a79AgI")
    public static Item AgI = new Item(prop(Groups.Chemical));
    public static Item slag = new Item(prop(Groups.Industry));
    @ModTooltips("\u00a79U3O8 U^238")
    public static Item bingU3O8 = new Item(prop(Groups.Industry));
    @ModTooltips("\u00a79U3O8 U^235")
    public static Item smallU3O8 = new Item(prop(Groups.Industry));
    public static Item sandpowder = new Item(prop(Groups.Industry));
    public static Item stonepowder = new Item(prop(Groups.Industry));
    @ModTooltips("\u00a79Si")
    public static Item Silicon = new Item(prop(Groups.Chemical));
    @ModBurnTime(800)
    public static Item bambooCharcoal = new Item(prop(Groups.Agriculture));
    @ModBurnTime(600)
    public static Item amylum = new ModFood(5, 5.0F);
    public static Item corn = new Corn();
    @ModTooltips("\u00a79CaCO3")
    public static Item stoneIngot = new Item(prop(Groups.Chemical));
    public static Item Al2O3_Na3AlF6 = new Item(prop(Groups.Chemical));
    @ModTooltips("\u00a79CaSO4")
    public static Item CaSO4 = new Item(prop(Groups.Chemical));
    @ModTooltips("\u00a79Na3AlF6")
    public static Item Na3AlF6 = new Item(prop(Groups.Chemical));
    public static Item siliconSolarCell = new Item(prop(Groups.Industry));
    public static Item light_guide_fibre = new Item(prop(Groups.Industry));
    public static Item steelIngot = new Item(prop(Groups.Industry));
    public static Item solarPanels = new Item(prop(Groups.Industry));
    public static Item carbonRod = new Item(prop(Groups.Industry));
    public static Item calciumacetylide = new Item(prop(Groups.Chemical));
    public static Item calciumCarbonate = new Item(prop(Groups.Chemical));
    public static Item calciumOxide = new Item(prop(Groups.Chemical));
    public static Item ingotColumn = new Item(prop(Groups.Industry));
    public static Item concrete = new Item(prop(Groups.Chemical));
    public static Item tin = new Item(prop(Groups.Ore));
    public static Item lead = new Item(prop(Groups.Ore));
    public static Item copper = new Item(prop(Groups.Ore));
    public static Item silver = new Item(prop(Groups.Ore));
    public static Item greenstonePowder = new Item(prop(Groups.Ore));
    public static Item silverPowder = new Item(prop(Groups.Ore));
    public static Item osmiumpowder = new Item(prop(Groups.Ore));
    public static Item arditepowder = new Item(prop(Groups.Ore));
    public static Item cobaltpowder = new Item(prop(Groups.Ore));
    public static Item nickelpowder = new Item(prop(Groups.Ore));
    @ModTooltips("\u00a79U3O8 U^235/238")
    public static Item uraniumPowder = new Item(prop(Groups.Ore));
    @ModTooltips("\u00a79UO2 U^238")
    public static Item UO2 = new Item(prop(Groups.Industry));
    @ModTooltips("\u00a79UO2 U^235")
    public static Item UO2small = new Item(prop(Groups.Industry));
	@ModTooltips("\u00a79U U^238")
    public static Item uranium = new Item(prop(Groups.Industry));
	@ModTooltips("\u00a79U U^235")
    public static Item uranium235 = new Item(prop(Groups.Industry));
    public static Item tinPowder = new Item(prop(Groups.Ore));
    public static Item leadPowder = new Item(prop(Groups.Ore));
    public static Item redstonePowder = new Item(prop(Groups.Ore));
    public static Item bluestonePowder = new Item(prop(Groups.Ore));
    public static Item copperPowder = new Item(prop(Groups.Ore));
    public static Item ironPowder = new Item(prop(Groups.Ore));
    public static Item Fe2O3 = new Item(prop(Groups.Ore));
    public static Item CuO = new Item(prop(Groups.Ore));
    public static Item coalPowder = new Item(prop(Groups.Ore));
    public static Item goldPowder = new Item(prop(Groups.Ore));
    public static Item diamondPowder = new Item(prop(Groups.Ore));
    public static Item twiningSunCompound = new Item(prop(Groups.Main));
    public static Item intermediateRubbingSunCompound = new Item(prop(Groups.Main));
    public static Item largeRubbingSunCompound = new Item(prop(Groups.Main));
    public static Item photoElement = new Item(prop(Groups.Main));
    public static Item quartzPowder = new Item(prop(Groups.Ore));
    public static Item cornBroth = new SoupItem(prop(Groups.Agriculture).food(new Food.Builder().hunger(20).build()));
    public static Item money = new Item(prop(Groups.Main));
    public static Item sulfurOrePowder = new Item(prop(Groups.Ore));
    public static Item platinumOrePowder = new Item(prop(Groups.Ore));
    @ModBurnTime(800)
    public static Item sulfurPowder = new Item(prop(Groups.Ore));
    @ModBurnTime(800)
    public static Item sucroseCharCoal = new Item(prop(Groups.Agriculture));
    public static Item platinumIngot = new Item(prop(Groups.Ore));
    public static Item salt = new Item(prop(Groups.Chemical));
    public static Item diamondIngot = new Item(prop(Groups.Industry));
    public static Item rubber = new Item(prop(Groups.Industry));
    public static Item rubberRaw = new Item(prop(Groups.Agriculture));
    public static Item starchBall = new StarchBall();

    // foods
    public static Item bakedCorn = new ModFood(5, 2.0F);
    public static Item starchPowder = new ModFood(1, 0.0F);
    public static Item pureMeat = new ModFood(4, 1.0f);
    public static Item saltedFish = new SaltedFish();

    // tools
    public static Item spanner = new Spanner();
    public static Item hammerSmall = new Hammer(10);
    public static Item hammerMedium = new Hammer(40);
    public static Item hammerBig = new Hammer(120);
    public static Item elementShovel = new Tools.Shovel(Tools.ELEMENT);
    public static Item elementShear = new Tools.Shear(23800, true);
    public static Item elementHoe = new Tools.Hoe(Tools.ELEMENT);
    public static Item elementSword = new Tools.Sword(Tools.ELEMENT);
    public static Item elementAxe = new Tools.Axe(Tools.ELEMENT);
    public static Item elementBow = new Tools.Bow(38400, true);
    public static Item elementPickaxe = new Tools.Pickaxe(Tools.ELEMENT);
    public static Item elementFishingRod = new Tools.FishingRod(6400, true);
    public static Item elementHelmet = new Armor(Armor.ELEMENT, EquipmentSlotType.HEAD);
    public static Item elementBody = new Armor(Armor.ELEMENT, EquipmentSlotType.CHEST);
    public static Item elementLeggings = new Armor(Armor.ELEMENT, EquipmentSlotType.LEGS);
    public static Item elementBoots = new Armor(Armor.ELEMENT, EquipmentSlotType.FEET);
    public static Item platinumSword = new Tools.Sword(Tools.PLATINUM);
    public static Item platinumHoe = new Tools.Hoe(Tools.PLATINUM);
    public static Item platinumShovel = new Tools.Shovel(Tools.PLATINUM);
    public static Item platinumAxe = new Tools.Axe(Tools.PLATINUM);
    public static Item platinumPickaxe = new Tools.Pickaxe(Tools.PLATINUM);
    public static Item platinumHelmet = new Armor(Armor.PLATINUM, EquipmentSlotType.HEAD);
    public static Item platinumBody = new Armor(Armor.PLATINUM, EquipmentSlotType.CHEST);
    public static Item platinumLeggings = new Armor(Armor.PLATINUM, EquipmentSlotType.LEGS);
    public static Item platinumBoots = new Armor(Armor.PLATINUM, EquipmentSlotType.FEET);
    public static WoodenHalter woodenHalter = new WoodenHalter();

    // gear
    public static Item gearRawDiamond = new Item(prop(Groups.Industry));
    public static Item gearWood = new Item(prop(Groups.Industry));
    public static Item gearQuartz = new Item(prop(Groups.Industry));
    public static Item gearStone = new Item(prop(Groups.Industry));
    public static Item gearCarbon = new Item(prop(Groups.Industry));
    public static Item gearGold = new Item(prop(Groups.Industry));
    public static Item gearSteel = new Item(prop(Groups.Industry));
    public static Item gearDiamond = new Item(prop(Groups.Industry));
    public static Item gearIron = new Item(prop(Groups.Industry));
    public static Item gearPlatinum = new Item(prop(Groups.Industry));
    public static Item gearCopper = new Item(prop(Groups.Industry));
    public static Item gearObsidian = new Item(prop(Groups.Industry));
    public static Item gearLead = new Item(prop(Groups.Industry));
    public static Item gearTin = new Item(prop(Groups.Industry));
    public static Item gearSilver = new Item(prop(Groups.Industry));

    // plate
    public static Item plateRawDiamond = new Item(prop(Groups.Industry));
    public static Item plateWood = new Item(prop(Groups.Industry));
    public static Item plateStone = new Item(prop(Groups.Industry));
    public static Item plateIron = new Item(prop(Groups.Industry));
    public static Item plateSteel = new Item(prop(Groups.Industry));
    public static Item plateGold = new Item(prop(Groups.Industry));
    public static Item plateDiamond = new Item(prop(Groups.Industry));
    public static Item platePlatinum = new Item(prop(Groups.Industry));
    public static Item plateCopper = new Item(prop(Groups.Industry));
    public static Item plateQuartz = new Item(prop(Groups.Industry));
    public static Item plateCarbon = new Item(prop(Groups.Industry));
    public static Item plateObsidian = new Item(prop(Groups.Industry));
    public static Item plateLead = new Item(prop(Groups.Industry));
    public static Item plateTin = new Item(prop(Groups.Industry));
    public static Item plateSilver = new Item(prop(Groups.Industry));
    public static Item plateAluminium = new Item(prop(Groups.Industry));
    public static Item plateSilicon = new Item(prop(Groups.Industry));

    public static Item cpu = new Item(prop(Groups.Industry));
    public static Item cpuUp  = new Item(prop(Groups.Industry));
    public static Item cpuDown  = new Item(prop(Groups.Industry));

    private static Item.Properties prop(ItemGroup group) {
        return new Item.Properties().group(group);
    }
}
