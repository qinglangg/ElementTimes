package com.elementtimes.elementtimes.common.block.stand.module;

import com.elementtimes.elementcore.api.utils.TagUtils;
import com.elementtimes.elementtimes.common.fluid.Sources;
import com.elementtimes.elementtimes.common.init.Items;
import com.elementtimes.elementtimes.common.init.blocks.Chemical;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandlerItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * 酒精灯

 */
public class ModuleAlcoholLamp implements ISupportStandModule, IFluidHandler {

    public static final String KEY = "AlcoholLamp";

    private FluidStack fluid = FluidStack.EMPTY;
    private boolean isFire = false;
    private boolean needSync = false;
    private int rainOutFireColdDown = 0;

    public static final float ALCOHOL_RAIN_PROBABILITY = 0.1f;
    public static final int ALCOHOL_TICK = 5;

    @Nonnull
    @Override
    public String getKey() { return KEY; }

    @Override
    public boolean onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity playerIn, Hand hand, BlockRayTraceResult trace) {
        ItemStack held = playerIn.getHeldItem(hand);
        if (held.getItem() == Items.spanner) {
            // 扳手
            ITextComponent msg;
            if (fluid.isEmpty()) {
                msg = new StringTextComponent("EMPTY");
            } else {
                msg = fluid.getDisplayName().appendText(": " + fluid.getAmount() + " mb");
            }
            playerIn.sendMessage(msg);
            return false;
        }
        LazyOptional<IFluidHandlerItem> capabilityOpt = held.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        if (capabilityOpt.isPresent()) {
            FluidActionResult result = FluidUtil.tryEmptyContainer(held, this, getTankCapacity(0), playerIn, false);
            if (result.success) {
                held = FluidUtil.tryEmptyContainer(held, this, getTankCapacity(0), playerIn, true).result;
                playerIn.setHeldItem(hand, held);
                return true;
            }
        }
        if (isFire()) {
            return canOutfire(held, playerIn, hand, () -> setFire(false));
        } else if (canFire() && held.getItem() == net.minecraft.item.Items.FLINT_AND_STEEL) {
            // 打火石
            setFire(true);
            held.damageItem(1, playerIn, e -> {});
            return true;
        }
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getModuleItem() { return new ItemStack(Chemical.alcoholLamp); }

    @Override
    public int getLight(int light) {
        if (isFire()) {
            return 15;
        }
        return light;
    }

    @Override
    public boolean onTick(World world, BlockPos pos) {
        if (isFire()) {
            if (costAlcohol()) {
                if (rainOutFireColdDown > 0) {
                    rainOutFireColdDown--;
                } else {
                    rainOutFireColdDown = 2000;
                    if (world.isRainingAt(pos) && world.rand.nextFloat() <= ALCOHOL_RAIN_PROBABILITY) {
                        if (pos.getY() >= world.getHeight(Heightmap.Type.WORLD_SURFACE, pos).getY()) {
                            setFire(false);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public boolean onTickClient(World world, BlockPos pos, CompoundNBT data) {
        if (data != null) {
            isFire = data.getBoolean("f");
        }
        return false;
    }

    @Override
    public void animateTick(BlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (isFire()) {
            double d0 = pos.getX();
            double d1 = pos.getY();
            double d2 = pos.getZ();
            worldIn.addParticle(ParticleTypes.FLAME, d0 + 0.5, d1 + 0.4, d2 + 0.5, 0, 0, 0);
        }
    }

    @Nullable
    @Override
    public CompoundNBT getUpdateData(World world, BlockPos pos) {
        if (needSync) {
            CompoundNBT compound = new CompoundNBT();
            compound.putBoolean("f", isFire);
            needSync = false;
            return compound;
        }
        return null;
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void onRender() {
        com.mojang.blaze3d.platform.GlStateManager.translated(.5, 0, .5);
        com.mojang.blaze3d.platform.GlStateManager.scalef(3, 3, 3);
        net.minecraft.client.Minecraft.getInstance().getItemRenderer()
                .renderItem(getModuleItem(), net.minecraft.client.renderer.model.ItemCameraTransforms.TransformType.GROUND);
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY) {
            return LazyOptional.of(() -> (T) this);
        }
        return LazyOptional.empty();
    }

    @Override
    public int getTanks() {
        return 1;
    }

    @Nonnull
    @Override
    public FluidStack getFluidInTank(int tank) {
        return fluid;
    }

    @Override
    public int getTankCapacity(int tank) {
        return 16000;
    }

    @Override
    public boolean isFluidValid(int tank, @Nonnull FluidStack stack) {
        return false;
    }

    @Override
    public int fill(FluidStack resource, FluidAction action) {
        if (isFull()) {
            return 0;
        }
        if (!canFill(resource)) {
            return 0;
        }
        int resAmount = resource.getAmount();
        if (fluid.isEmpty()) {
            int amount = Math.min(getTankCapacity(0), resAmount);
            if (action.execute()) {
                fluid = new FluidStack(resource, amount);
            }
            return amount;
        }
        if (!fluid.isFluidEqual(resource)) {
            return 0;
        }
        int free = getTankCapacity(0) - resAmount;
        if (resAmount > free) {
            if (action.execute()) {
                fluid.grow(free);
            }
            return free;
        } else {
            if (action.execute()) {
                fluid.grow(resAmount);
            }
            return resAmount;
        }
    }

    @Nonnull
    @Override
    public FluidStack drain(FluidStack resource, FluidAction action) {
        return FluidStack.EMPTY;
    }

    @Nonnull
    @Override
    public FluidStack drain(int maxDrain, FluidAction action) {
        return FluidStack.EMPTY;
    }

    /**
     * 提取流体
     * 酒精灯理论上不允许抽取流体，预留一个接口吧
     * @param amount 抽取数目
     * @param doDrain 是否抽取
     * @return 抽取结果
     */
    public FluidStack use(int amount, boolean doDrain) {
        if (fluid.isEmpty()) {
            return FluidStack.EMPTY;
        }
        int drain = Math.min(amount, fluid.getAmount());
        FluidStack f = new FluidStack(fluid, drain);
        if (doDrain) {
            fluid.shrink(drain);
            if (fluid.isEmpty()) {
                fluid = FluidStack.EMPTY;
            }
        }
        return f;
    }

    public boolean isFull() {
        return fluid.getAmount() >= getTankCapacity(0);
    }

    public boolean canFill(FluidStack stack) {
        return stack != null && stack.getAmount() > 0 && stack.getFluid() == Sources.ethanol;
    }

    // nbt =============================================================================================================

    @Override
    public CompoundNBT serializeNBT() {
        CompoundNBT nbt = ISupportStandModule.super.serializeNBT();
        nbt.putBoolean("_f", isFire);
        if (!fluid.isEmpty()) {
            fluid.writeToNBT(nbt);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {
        isFire = nbt.getBoolean("_f");
        fluid = FluidStack.loadFluidStackFromNBT(nbt);
    }

    // other ===========================================================================================================

    public boolean canFire() {
        return !fluid.isEmpty();
    }

    public boolean isFire() { return isFire; }

    public void setFire(boolean fire) {
        if (fire != isFire) {
            isFire = fire;
            needSync = true;
        }
    }

    private boolean costAlcohol() {
        FluidStack drain = use(ALCOHOL_TICK, false);
        if (drain.getAmount() < ALCOHOL_TICK) {
            setFire(false);
            return false;
        } else {
            use(ALCOHOL_TICK, true);
            return true;
        }
    }

    public static boolean canOutfire(@Nonnull ItemStack stack, PlayerEntity player, Hand hand, Runnable outfire) {
        // 灭火
        if (stack.isEmpty()) {
            if (player.world.rand.nextBoolean()) {
                player.setFire(3);
            }
            outfire.run();
            return true;
        }
        LazyOptional<IFluidHandlerItem> capabilityOpt = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY);
        if (capabilityOpt.isPresent()) {
            IFluidHandlerItem capability = capabilityOpt.orElseThrow(RuntimeException::new);
            FluidStack drain = capability.drain(new FluidStack(Fluids.WATER, 1000), FluidAction.SIMULATE);
            if (drain.getAmount() >= 1000) {
                capability.drain(new FluidStack(Fluids.WATER, 1000), FluidAction.EXECUTE);
                player.setHeldItem(hand, capability.getContainer());
                outfire.run();
                return true;
            }
        }
        if (stack.getItem().isIn(TagUtils.forgeItem("dusts", "sand")) || stack.getItem().isIn(TagUtils.forgeItem("sand"))) {
            stack.shrink(1);
            outfire.run();
            return true;
        }
        return false;
    }
}
