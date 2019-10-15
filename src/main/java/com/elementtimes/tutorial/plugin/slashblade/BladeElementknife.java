package com.elementtimes.tutorial.plugin.slashblade;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import mods.flammpfeil.slashblade.ItemSlashBladeNamed;
import mods.flammpfeil.slashblade.RecipeAwakeBlade;
import mods.flammpfeil.slashblade.SlashBlade;
import mods.flammpfeil.slashblade.item.ItemSlashBlade;
import mods.flammpfeil.slashblade.named.Fox;
import mods.flammpfeil.slashblade.named.event.LoadEvent;
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
        String name = "elementknife";
        ItemStack etherealshadow = new ItemStack(SlashBlade.bladeNamed, 1, 0);
        NBTTagCompound tag = new NBTTagCompound();
        etherealshadow.setTagCompound(tag);
        // 刀痕的颜色，注意后面的是十进制，可以到“http://tool.oschina.net/hexconvert/”去查
        tag.setInteger("SummonedSwordColor", 12138751);
        // 此刀的名字
        ItemSlashBladeNamed.CurrentItemName.set(tag, name);
        // 最大耐久值（可自定义）
        ItemSlashBladeNamed.CustomMaxDamage.set(tag, 1024);
        // 基础伤害（可以修改150.0F和IRON,注意IRON是材料，默认绿宝石）
        ItemSlashBlade.setBaseAttackModifier(tag, 150.0F + Item.ToolMaterial.IRON.getAttackDamage());
        // 是否默认为妖刀（true或false）
        ItemSlashBladeNamed.IsDefaultBewitched.set(tag, true);
        // 材质名（可自定义），会读取assets/flammpfeil.slashblade/model/modid/BladeName下的BladeName.png贴图文件
        ItemSlashBlade.TextureName.set(tag, "elementtimes/uilon");
        // 模型名（可自定义）,会读取assets/flammpfeil.slashblade/model/modid/BladeName下的BladeName.obj模型文件
        ItemSlashBlade.ModelName.set(tag, "elementtimes/uilon");
        // 此刀的SA ID(可修改),假如添加了一个自己SA，那么要使用新的SA就得填写新SA的ID
        ItemSlashBlade.SpecialAttackType.set(tag, 3);
        // 手中不持刀时此刀的显示情况，0是不显示，1是在左手，2是在背后，3是背在背上
        ItemSlashBlade.StandbyRenderType.set(tag, 3);
        ECUtils.item.addMaxEnchantments(etherealshadow);
        SlashBlade.registerCustomItemStack(name, etherealshadow);
        // 设置名字
        ItemSlashBladeNamed.NamedBlades.add(name);
    }

    @SubscribeEvent
    public void postinit(LoadEvent.PostInitEvent event) {
        SlashBlade.addRecipe("elementknife", new RecipeAwakeBlade(new ResourceLocation("flammpfeil.slashblade", "elementknife"),
                SlashBlade.getCustomBlade("flammpfeil.slashblade", "elementknife"),
                // 此变量应该是比对 NBT 判断 🔪 是否可以合成
                ItemStack.EMPTY,
                "VXW", "XXX", "AXB",
                'X', new ItemStack(ElementtimesItems.fiveElements),
                'B', SlashBlade.getCustomBlade("flammpfeil.slashblade", Fox.nameBlack),
                'A', SlashBlade.getCustomBlade("flammpfeil.slashblade", Fox.nameWhite),
                'V', new ItemStack(ElementtimesItems.photoElement),
                'W', new ItemStack(ElementtimesItems.endElement)));
    }
}
