package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.block.Pipeline;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.init.ElementtimesMagic;
import com.elementtimes.tutorial.other.pipeline.PLNetworkManager;
import net.minecraft.block.Block;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.*;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;

/**
 * 大小锤子
 * 唯一指定调试工具
 * @author luqin2007
 */
public class Hammer extends Item {

    private static final String TAG_DAMAGE = "damage";

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, net.minecraft.client.util.ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        if (EnchantmentHelper.getEnchantments(stack).containsKey(ElementtimesMagic.hammerDebugger)) {
            tooltip.add(net.minecraft.client.resources.I18n.format("tooltip.elementtimes.enchantment.debug"));
        }
    }

    @Override
    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return repair.getItem() == ElementtimesItems.diamondIngot;
    }

    @Override
    public ItemStack getContainerItem(ItemStack itemStack) {
        NBTTagCompound bind = itemStack.getOrCreateSubCompound(ElementTimes.MODID + "_bind");
        int d = 1;
        if (bind.hasKey(TAG_DAMAGE)) {
            d = bind.getInteger(TAG_DAMAGE);
        }
        ItemStack container = itemStack.copy();
        container.removeSubCompound(ElementTimes.MODID + "_bind");
        container.attemptDamageItem(d, itemRand, null);

        return container;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if (!worldIn.isRemote) {
            ItemStack stack = player.getHeldItem(hand);
            if (EnchantmentHelper.getEnchantments(stack).containsKey(ElementtimesMagic.hammerDebugger)) {
                debug(worldIn, pos, player);
            }
        }
        return EnumActionResult.PASS;
    }

    private void debug(World worldIn, BlockPos pos, EntityPlayer player) {
        Block block = worldIn.getBlockState(pos).getBlock();
        TileEntity te = worldIn.getTileEntity(pos);
        if (block == Blocks.AIR) {
            // 空气
            player.sendMessage(new TextComponentTranslation("chat.elementtimes.hammer.noblock", pos.getX(), pos.getY(), pos.getZ()));
        } else if (block instanceof Pipeline) {
            if (player.isSneaking()) {
                debugTe(te, block, player);
            } else {
                debugPl(block, player);
            }
        } else {
            debugTe(te, block, player);
        }
    }

    private void debugTe(TileEntity te, Block block, EntityPlayer player) {
        if (te == null) {
            // 无 te
            player.sendMessage(new TextComponentTranslation("chat.elementtimes.hammer.noblockte", new ItemStack(block).getDisplayName()));
        } else {
            NBTTagCompound nbt = te.writeToNBT(new NBTTagCompound());
            if (nbt.getKeySet().isEmpty()) {
                // 无 nbt
                player.sendMessage(new TextComponentTranslation("chat.elementtimes.hammer.nonbt", new ItemStack(block).getDisplayName()));
            } else {
                sendDebugChat(player, block, "", nbt, 0);
                player.sendMessage(new TextComponentString("============================================================"));
            }
        }
    }

    private void debugPl(Block block, EntityPlayer player) {
        sendDebugChat(player, block, "", PLNetworkManager.serializeNbt(), 0);
        player.sendMessage(new TextComponentString("============================================================"));
    }

    private void sendDebugChat(EntityPlayer player, Block block, String lastKey, NBTBase nbt, int level) {
        String name = new ItemStack(block).getDisplayName();
        StringBuilder space = new StringBuilder(name).append(": ");
        for (int i = 0; i < level; i++) {
            space.append("    ");
        }
        if (nbt instanceof NBTTagCompound) {
            player.sendMessage(new TextComponentString(space.toString() + lastKey));
            ((NBTTagCompound) nbt).getKeySet().forEach(key -> {
                sendDebugChat(player, block, key, ((NBTTagCompound) nbt).getTag(key), level + 1);
            });
        } else {
            if (nbt instanceof NBTTagList) {
                for (int i = 0; i < ((NBTTagList) nbt).tagCount(); i++) {
                    sendDebugChat(player, block, lastKey + "[" + i + "]", ((NBTTagList) nbt).get(i), level);
                }
            } else if (nbt instanceof NBTTagByte) {
                player.sendMessage(new TextComponentString(space.toString() + lastKey + " = " + ((NBTTagByte) nbt).getByte()));
            } else if (nbt instanceof NBTTagDouble) {
                player.sendMessage(new TextComponentString(space.toString() + lastKey + " = " + ((NBTTagDouble) nbt).getDouble()));
            } else if (nbt instanceof NBTTagFloat) {
                player.sendMessage(new TextComponentString(space.toString() + lastKey + " = " + ((NBTTagFloat) nbt).getDouble()));
            } else if (nbt instanceof NBTTagInt) {
                player.sendMessage(new TextComponentString(space.toString() + lastKey + " = " + ((NBTTagInt) nbt).getDouble()));
            } else if (nbt instanceof NBTTagLong) {
                player.sendMessage(new TextComponentString(space.toString() + lastKey + " = " + ((NBTTagLong) nbt).getDouble()));
            } else if (nbt instanceof NBTTagShort) {
                player.sendMessage(new TextComponentString(space.toString() + lastKey + " = " + ((NBTTagShort) nbt).getDouble()));
            } else if (nbt instanceof NBTTagByteArray) {
                player.sendMessage(new TextComponentString(space.toString() + lastKey + " = " + Arrays.toString(((NBTTagByteArray) nbt).getByteArray())));
            } else if (nbt instanceof NBTTagIntArray) {
                player.sendMessage(new TextComponentString(space.toString() + lastKey + " = " + Arrays.toString(((NBTTagIntArray) nbt).getIntArray())));
            } else if (nbt instanceof NBTTagString) {
                player.sendMessage(new TextComponentString(space.toString() + lastKey + " = " + ((NBTTagString) nbt).getString()));
            } else {
                player.sendMessage(new TextComponentString(space.toString() + lastKey + " = " + nbt.toString()));
            }
        }
    }
}
