package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.common.capability.CapabilitySeaWater;
import com.elementtimes.tutorial.common.init.ElementtimesMagic;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.commons.lang3.ArrayUtils;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * 盐
 * @author luqin2007
 */
public class Salt extends ItemFood {

    public Salt() {
        super(0, 0, false);
        setAlwaysEdible();
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack heldItem = playerIn.getHeldItem(handIn);
        if (!worldIn.isRemote) {
            PotionEffect effect = playerIn.getActivePotionEffect(ElementtimesMagic.salt);
            if (effect != null) {
                bindTag(heldItem, null, effect.getAmplifier());
                return super.onItemRightClick(worldIn, playerIn, handIn);
            }
        }
        return ActionResult.newResult(EnumActionResult.FAIL, heldItem);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        NBTTagCompound tag = stack.getOrCreateSubCompound("_salt_");
        if (tag.hasKey("level")) {
            int level = tag.getInteger("level");
            return 2 * Math.max(1, 5 - level);
        }
        return Integer.MAX_VALUE;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        // Salt 效果前置：设置最大使用时间 = 2*(5-amplifier)
        PotionEffect saltEffect = playerIn.getActivePotionEffect(ElementtimesMagic.salt);
        if (saltEffect != null) {
            int[] oreIDs = OreDictionary.getOreIDs(playerIn.getHeldItem(playerIn.getActiveHand()));
            if (ArrayUtils.contains(oreIDs, OreDictionary.getOreID("salt"))) {
                // 增加咸度
                CapabilitySeaWater.ISeaWater cap = playerIn.getCapability(CapabilitySeaWater.CAPABILITY_SEA_WATER, null);
                if (cap != null) {
                    cap.increaseSalt(1);
                }
                // 恢复饥饿值与生命
                int amplifier = saltEffect.getAmplifier();
                playerIn.getFoodStats().addStats((int) (amplifier * 1.5), amplifier * 2f);
                playerIn.heal(amplifier);
            }
        }
    }

    public static void bindTag(ItemStack stack, @Nullable ItemStack ori, int level) {
        NBTTagCompound tagCompound = stack.getTagCompound();
        boolean remove = tagCompound == null || tagCompound.hasKey("_salt_");
        NBTTagCompound tag = stack.getOrCreateSubCompound("_salt_");
        tag.setInteger("level", level);
        if (ori != null) {
            tag.setTag("item", ori.serializeNBT());
        }
        tag.setBoolean("remove", remove);
    }

    public static ItemStack removeTag(ItemStack stack) {
        ItemStack item = stack;
        NBTTagCompound tagCompound = stack.getTagCompound();
        NBTTagCompound tag = tagCompound == null ? null : tagCompound.getCompoundTag("_salt_");
        if (tag != null) {
            boolean remove = tag.getBoolean("remove");
            if (tag.hasKey("item")) {
                NBTTagCompound itemNbt = tag.getCompoundTag("item");
                ItemStack item1 = new ItemStack(itemNbt);
                item1.setCount(item.getCount());
                item = item1;
            }
            if (remove) {
                tagCompound.removeTag("_salt_");
            } else {
                tag.removeTag("level");
                tag.removeTag("item");
                tag.removeTag("remove");
            }
        }
        return item;
    }
}
