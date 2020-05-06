package com.elementtimes.tutorial.common.block.stand.module;

import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.plugin.elementcore.SSMRegister;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fluids.FluidActionResult;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.FluidUtil;
import net.minecraftforge.fluids.capability.*;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Random;

/**
 * 酒精灯
 * @author luqin2007
 */
@SSMRegister.SupportStandModule(ModuleAlcoholLamp.KEY)
public class ModuleAlcoholLamp implements ISupportStandModule, IFluidHandler {

    public static final String KEY = "AlcoholLamp";

    private FluidStack fluid = null;
    private boolean isFire = false;
    private boolean needSync = false;
    private int rainOutFireColdDown = 0;

    public static final float ALCOHOL_RAIN_PROBABILITY = 0.1f;
    public static final int ALCOHOL_TICK = 5;

    @Nonnull
    @Override
    public String getKey() { return KEY; }

    @Override
    public boolean onBlockActivated(EntityPlayer playerIn, EnumHand hand, EnumFacing facing,
                                    float hitX, float hitY, float hitZ) {
        ItemStack held = playerIn.getHeldItem(hand);
        if (held.getItem() == ElementtimesItems.spanner) {
            // 扳手
            String msg;
            if (fluid == null || fluid.amount <= 0) {
                msg = "EMPTY";
            } else {
                msg = String.format("%s: %d mb", fluid.getUnlocalizedName(), fluid.amount);
            }
            playerIn.sendMessage(new TextComponentTranslation(msg));
            return false;
        }
        if (held.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            FluidActionResult result = FluidUtil.tryEmptyContainer(held, this, getCapacity(), playerIn, false);
            if (result.success) {
                held = FluidUtil.tryEmptyContainer(held, this, getCapacity(), playerIn, true).result;
                playerIn.setHeldItem(hand, held);
                return true;
            }
        }
        if (isFire()) {
            return canOutfire(held, playerIn, hand, () -> setFire(false));
        } else if (canFire() && held.getItem() == Items.FLINT_AND_STEEL) {
            // 打火石
            setFire(true);
            held.damageItem(1, playerIn);
            return true;
        }
        return false;
    }

    @Nonnull
    @Override
    public ItemStack getModuleItem() { return new ItemStack(ElementtimesBlocks.alcoholLamp); }

    @Override
    public int getLight(int light) {
        if (isFire()) {
            return 15;
        }
        return light;
    }

    // Tickable ========================================================================================================

    @Override
    public boolean onTick(World world, BlockPos pos) {
        if (isFire()) {
            if (costAlcohol()) {
                if (rainOutFireColdDown > 0) {
                    rainOutFireColdDown--;
                } else {
                    rainOutFireColdDown = 2000;
                    if (world.isRainingAt(pos) && world.rand.nextFloat() <= ALCOHOL_RAIN_PROBABILITY) {
                        if (pos.getY() >= world.getHeight(pos.getX(), pos.getZ())) {
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
    @SideOnly(Side.CLIENT)
    public boolean onTickClient(World world, BlockPos pos, NBTTagCompound data) {
        if (data != null) {
            isFire = data.getBoolean("f");
        }
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World worldIn, BlockPos pos, Random rand) {
        if (isFire()) {
            double d0 = pos.getX();
            double d1 = pos.getY();
            double d2 = pos.getZ();
            worldIn.spawnParticle(EnumParticleTypes.FLAME, d0 + 0.5, d1 + 0.4, d2 + 0.5, 0, 0, 0);
        }
    }

    @Nullable
    @Override
    public NBTTagCompound getUpdateData(World world, BlockPos pos) {
        if (needSync) {
            NBTTagCompound compound = new NBTTagCompound();
            compound.setBoolean("f", isFire);
            needSync = false;
            return compound;
        }
        return null;
    }

    @Override
    public void onRender() {
        net.minecraft.client.renderer.GlStateManager.translate(.5, 0, .5);
        net.minecraft.client.renderer.GlStateManager.scale(3, 3, 3);
        net.minecraft.client.Minecraft.getMinecraft().getRenderItem()
                .renderItem(getModuleItem(), net.minecraft.client.renderer.block.model.ItemCameraTransforms.TransformType.GROUND);
    }

    // Capability ======================================================================================================

    @Override
    public boolean hasCapability(@Nonnull Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY;
    }

    @Nullable
    @Override
    public <T> T getCapability(@Nonnull Capability<T> capability, @Nullable EnumFacing facing) {
        if (hasCapability(capability, facing)) {
            return (T) this;
        }
        return null;
    }

    // Tank ============================================================================================================

    @Override
    public IFluidTankProperties[] getTankProperties() {
        return new IFluidTankProperties[] {new FluidTankProperties(fluid, getCapacity(), true, false)};
    }

    @Override
    public int fill(FluidStack resource, boolean doFill) {
        if (isFull()) {
            return 0;
        }
        if (!canFill(resource)) {
            return 0;
        }
        int resAmount = resource.amount;
        if (fluid == null) {
            int amount = Math.min(getCapacity(), resAmount);
            if (doFill) {
                fluid = new FluidStack(resource, amount);
            }
            return amount;
        }
        if (!fluid.isFluidEqual(resource)) {
            return 0;
        }
        int free = getCapacity() - resAmount;
        if (resAmount > free) {
            if (doFill) {
                fluid.amount += free;
            }
            return free;
        } else {
            if (doFill) {
                fluid.amount += resAmount;
            }
            return resAmount;
        }
    }

    @Nullable
    @Override
    public FluidStack drain(FluidStack resource, boolean doDrain) { return null; }

    @Nullable
    @Override
    public FluidStack drain(int maxDrain, boolean doDrain) { return null; }

    /**
     * 提取流体
     * 酒精灯理论上不允许抽取流体，预留一个接口吧
     * @param amount 抽取数目
     * @param doDrain 是否抽取
     * @return 抽取结果
     */
    public FluidStack use(int amount, boolean doDrain) {
        if (fluid == null) {
            return null;
        }
        int drain = Math.min(amount, fluid.amount);
        FluidStack f = new FluidStack(fluid, drain);
        if (doDrain) {
            fluid.amount -= drain;
            if (fluid.amount <= 0) {
                fluid = null;
            }
        }
        return f;
    }

    public int getCapacity() { return 16000; }

    public boolean isFull() {
        return fluid != null && fluid.amount >= getCapacity();
    }

    public boolean canFill(FluidStack stack) {
        return stack != null && stack.amount > 0 && stack.getFluid() == ElementtimesFluids.ethanol;
    }

    // nbt =============================================================================================================

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound nbt = ISupportStandModule.super.serializeNBT();
        nbt.setBoolean("_f", isFire);
        if (fluid != null) {
            fluid.writeToNBT(nbt);
        }
        return nbt;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        isFire = nbt.getBoolean("_f");
        fluid = FluidStack.loadFluidStackFromNBT(nbt);
    }

    // other ===========================================================================================================

    public boolean canFire() {
        return fluid != null && fluid.amount > 0;
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
        if (drain == null || drain.amount < ALCOHOL_TICK) {
            setFire(false);
            return false;
        } else {
            use(ALCOHOL_TICK, true);
            return true;
        }
    }

    public static boolean canOutfire(@Nonnull ItemStack stack, EntityPlayer player, EnumHand hand, Runnable outfire) {
        // 灭火
        if (stack.isEmpty()) {
            if (player.world.rand.nextBoolean()) {
                player.setFire(3);
            }
            outfire.run();
            return true;
        } else if (stack.hasCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null)) {
            IFluidHandlerItem capability = stack.getCapability(CapabilityFluidHandler.FLUID_HANDLER_ITEM_CAPABILITY, null);
            FluidStack drain = capability.drain(new FluidStack(FluidRegistry.WATER, 1000), false);
            if (drain != null && drain.amount >= 1000) {
                capability.drain(new FluidStack(FluidRegistry.WATER, 1000), true);
                player.setHeldItem(hand, capability.getContainer());
                outfire.run();
                return true;
            }
        } else {
            for (int id : OreDictionary.getOreIDs(stack)) {
                String oreName = OreDictionary.getOreName(id);
                if ("dustSand".equals(oreName) || "sandPowder".equals(oreName)) {
                    stack.shrink(1);
                    outfire.run();
                    return true;
                }
            }
        }
        return false;
    }
}
