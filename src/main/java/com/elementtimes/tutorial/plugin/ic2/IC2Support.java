package com.elementtimes.tutorial.plugin.ic2;

import com.elementtimes.tutorial.common.block.tree.RubberLog;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.eventhandler.Event;

public class IC2Support {

    public static boolean isLoaded() {
        return Loader.isModLoaded("ic2");
    }

    public static void register() {
        if (isLoaded()) {
            MinecraftForge.EVENT_BUS.register(new IC2Event());
        }
    }

    public static boolean extractRubber(EntityPlayer player, World world, BlockPos pos, EnumFacing side) {
        if (isLoaded()) {
            IBlockState state = world.getBlockState(pos);
            Block block = state.getBlock();
            if (block == ic2.core.ref.BlockName.rubber_wood.getInstance()) {
                return ic2.core.item.tool.ItemTreetap.attemptExtract(player, world, pos, side, state, null);
            }
        }
        return false;
    }

    public static void useTreeTap(PlayerInteractEvent.RightClickBlock event, ItemStack stack) {
        ic2.core.item.tool.ItemTreetap tap = (ic2.core.item.tool.ItemTreetap) stack.getItem();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();
        if (!world.isRemote) {
            IBlockState bs = world.getBlockState(pos);
            if (bs.getBlock() == ElementtimesBlocks.rubberLog && bs.getValue(RubberLog.HAS_RUBBER)) {
                world.setBlockState(pos, bs.withProperty(RubberLog.HAS_RUBBER, false));
                world.markBlockRangeForRenderUpdate(pos, pos);
                player.dropItem(new ItemStack(ElementtimesItems.rubberRaw), true, false);
                ic2.core.util.StackUtil.damage(player, event.getHand(), ic2.core.util.StackUtil.anyStack, 1);
                event.setUseItem(Event.Result.DENY);
            }
        }
    }

    public static void useElectricTreeTap(PlayerInteractEvent.RightClickBlock event, ItemStack stack) {
        ic2.core.item.tool.ItemTreetapElectric tap = (ic2.core.item.tool.ItemTreetapElectric) stack.getItem();
        World world = event.getWorld();
        BlockPos pos = event.getPos();
        EntityPlayer player = event.getEntityPlayer();
        if (!world.isRemote) {
            IBlockState bs = world.getBlockState(pos);
            if (bs.getBlock() == ElementtimesBlocks.rubberLog && bs.getValue(RubberLog.HAS_RUBBER)) {
                world.setBlockState(pos, bs.withProperty(RubberLog.HAS_RUBBER, false));
                world.markBlockRangeForRenderUpdate(pos, pos);
                player.dropItem(new ItemStack(ElementtimesItems.rubberRaw), true, false);
                ic2.api.item.ElectricItem.manager.use(stack, tap.operationEnergyCost, player);
                event.setUseItem(Event.Result.DENY);
            }
        }
    }
}
