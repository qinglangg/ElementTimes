package com.elementtimes.tutorial.plugin.slashblade;

import com.elementtimes.tutorial.common.init.ElementtimesItems;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.Fox;
import mods.flammpfeil.slashblade.named.event.LoadEvent;
import net.minecraft.init.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

/**
 * @author 卿岚
 */
@Optional.Interface(iface = "", modid = "flammpfeil.slashblade")
public class BladeElementknife {

    @SubscribeEvent
    public void init(LoadEvent.InitEvent event) {
        String name = "elementknife";//此刀的名字（可自定义）
        ItemStack etherealshadow = new ItemStack(SlashBlade.bladeNamed, 1, 0);//这一段默认这样，如果想做有特效的刀，比如风雷太刀，就可以修改
        NBTTagCompound tag = new NBTTagCompound();//开始设置NBT
        etherealshadow.setTagCompound(tag);
        tag.setInteger("SummonedSwordColor", 12138751);//刀痕的颜色，注意后面的是十进制，可以到“http://tool.oschina.net/hexconvert/”去查
        ItemSlashBladeNamed.CurrentItemName.set(tag, name);//此刀的名字
        ItemSlashBladeNamed.CustomMaxDamage.set(tag, Integer.valueOf(1024));//最大耐久值（可自定义）
        ItemSlashBlade.setBaseAttackModifier(tag, 150.0F + Item.ToolMaterial.IRON.getAttackDamage());//基础伤害（可以修改150.0F和IRON,注意IRON是材料，默认绿宝石）
        ItemSlashBladeNamed.IsDefaultBewitched.set(tag, Boolean.valueOf(true));//是否默认为妖刀（true或false）
        ItemSlashBlade.TextureName.set(tag, "elementtimes/uilon");//材质名（可自定义），会读取assets/flammpfeil.slashblade/model/modid/BladeName下的BladeName.png贴图文件
        ItemSlashBlade.ModelName.set(tag, "elementtimes/uilon");//模型名（可自定义）,会读取assets/flammpfeil.slashblade/model/modid/BladeName下的BladeName.obj模型文件
        ItemSlashBlade.SpecialAttackType.set(tag, Integer.valueOf(3));//此刀的SA ID(可修改),假如添加了一个自己SA，那么要使用新的SA就得填写新SA的ID
        ItemSlashBlade.StandbyRenderType.set(tag, Integer.valueOf(3));//手中不持刀时此刀的显示情况，0是不显示，1是在左手，2是在背后，3是背在背上
        etherealshadow.addEnchantment(Enchantments.FORTUNE, 3);
        etherealshadow.addEnchantment(Enchantments.LOOTING, 3);
        etherealshadow.addEnchantment(Enchantments.POWER, 4);
        etherealshadow.addEnchantment(Enchantments.PROTECTION, 5);
        etherealshadow.addEnchantment(Enchantments.SHARPNESS, 4);
        etherealshadow.addEnchantment(Enchantments.FIRE_ASPECT, 1);
        SlashBlade.registerCustomItemStack(name, etherealshadow);
        //设置名字
        ItemSlashBladeNamed.NamedBlades.add(name);
    }

    @SubscribeEvent
    public void postinit(LoadEvent.PostInitEvent event) {
        SlashBlade.addRecipe("elementknife", new RecipeAwakeBlade(new ResourceLocation("flammpfeil.slashblade", "elementknife"),
                SlashBlade.getCustomBlade("flammpfeil.slashblade", "elementknife"),
                SlashBlade.findItemStack("flammpfeil.slashblade", "slashbladeWrapper", 1),
                "VXW", "XXX", "AXB",
                'X', new ItemStack(ElementtimesItems.fiveElements),
                'B', SlashBlade.getCustomBlade("flammpfeil.slashblade", Fox.nameBlack),
                'A', SlashBlade.getCustomBlade("flammpfeil.slashblade", Fox.nameWhite),
                'V', new ItemStack(ElementtimesItems.photoElement),
                'W', new ItemStack(ElementtimesItems.endElement)));
    }
}
