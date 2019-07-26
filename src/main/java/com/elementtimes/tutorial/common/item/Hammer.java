package com.elementtimes.tutorial.common.item;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.common.init.ElementtimesMagic;
import com.elementtimes.tutorial.common.tileentity.TilePipeline;
import com.elementtimes.tutorial.other.pipeline.PLInfo;
import com.elementtimes.tutorial.other.pipeline.PLPath;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
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
import net.minecraftforge.common.property.IExtendedBlockState;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

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

    @Nonnull
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

    @Nonnull
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
        IBlockState bs = worldIn.getBlockState(pos);
        TileEntity te = worldIn.getTileEntity(pos);
        if (block == Blocks.AIR) {
            // 空气
            player.sendMessage(new TextComponentTranslation("chat.elementtimes.hammer.noblock", pos.getX(), pos.getY(), pos.getZ()));
        } else {
            debugTe(te, block, player);
            debugBs(bs, player);
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
                player.sendMessage(new TextComponentString(block.getLocalizedName()));
                sendDebugChat(player, "", nbt, 0);
                player.sendMessage(new TextComponentString("============================================================"));
            }
        }
    }

    private void debugBs(IBlockState bs, EntityPlayer player) {
        if (bs != null) {
            bs.getProperties().forEach((iProperty, comparable) ->
                    player.sendMessage(new TextComponentString(iProperty.getName() + " = " + comparable)));
            player.sendMessage(new TextComponentString("IBlockState================================================="));
            if (bs instanceof IExtendedBlockState) {
                player.sendMessage(new TextComponentString("IExtendedBlockState========================================="));
                IExtendedBlockState ebs = (IExtendedBlockState) bs;
                ebs.getUnlistedProperties().forEach((iProperty, comparable) ->
                        player.sendMessage(new TextComponentString(iProperty.getName() + " = " + comparable)));
                player.sendMessage(new TextComponentString("============================================================"));
            }
        }
    }

    private void debugPl(World world, TilePipeline tp, EntityPlayer player) {
        if (tp != null) {
            PLInfo info = tp.getInfo();
            BlockPos pos = tp.getPos();
            Map<BlockPos, PLPath> pathMap = info.allValidOutput(world, pos, null);
            player.sendMessage(new TextComponentTranslation("chat.elementtimes.hammer.pipeline_pipeline", pos.getX(), pos.getY(), pos.getZ(), info.type));
            for (PLPath value : pathMap.values()) {
                long sum = value.path.stream().mapToLong(p -> p.keepTick).sum();
                player.sendMessage(new TextComponentTranslation("chat.elementtimes.hammer.pipeline_path",
                        value.from.getX(), value.from.getY(), value.from.getZ(),
                        value.to.getX(), value.to.getY(), value.to.getZ(), sum));
            }
        } else {
            player.sendMessage(new TextComponentString("chat.elementtimes.hammer.pipeline_none"));
        }
    }

    private void sendDebugChat(EntityPlayer player, String lastKey, NBTBase nbt, int level) {
        StringBuilder space = new StringBuilder();
        for (int i = 1; i < level; i++) {
            space.append("    ");
        }
        if (nbt instanceof NBTTagCompound) {
            player.sendMessage(new TextComponentString(space.toString() + lastKey));
            ((NBTTagCompound) nbt).getKeySet().forEach(key -> {
                sendDebugChat(player, key, ((NBTTagCompound) nbt).getTag(key), level + 1);
            });
        } else {
            if (nbt instanceof NBTTagList) {
                for (int i = 0; i < ((NBTTagList) nbt).tagCount(); i++) {
                    sendDebugChat(player, lastKey + "[" + i + "]", ((NBTTagList) nbt).get(i), level);
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
