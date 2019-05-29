package com.elementtimes.tutorial.common.tileentity.base;

import com.elementtimes.tutorial.common.block.base.BlockTileBase;
import com.elementtimes.tutorial.common.capability.RFEnergy;
import com.elementtimes.tutorial.interface_.tileentity.IButtonProvider;
import com.elementtimes.tutorial.interface_.tileentity.ISlotProvider;
import com.elementtimes.tutorial.util.BlockUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

/**
 * @author KSGFK create in 2019/3/9
 */
public abstract class TileMachine extends TileEntity implements ITickable, ISlotProvider, IButtonProvider {
    RFEnergy mEnergyHandler;
    Map<SideHandlerType, ItemStackHandler> mItemHandlers = new HashMap<>();
    Map<EnumFacing, SideHandlerType> mEnergyHandlerTypes = new HashMap<>();
    private Map<EnumFacing, SideHandlerType> mItemHandlerTypes = new HashMap<>();

    TileMachine(int energyCapacity, int energyReceiver, int energyExtract, int inputCount, int outputCount) {
        mEnergyHandler = new RFEnergy(energyCapacity, energyReceiver, energyExtract);

        mItemHandlers.put(SideHandlerType.INPUT, new ItemStackHandler(inputCount) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return isInputItemValid(slot, stack);
            }

            @Nonnull
            @Override
            public ItemStack insertItem(int slot, @Nonnull ItemStack stack, boolean simulate) {
                if (!isItemValid(slot, stack)) return stack;
                return super.insertItem(slot, stack, simulate);
            }
        });
        mItemHandlers.put(SideHandlerType.OUTPUT, new ItemStackHandler(outputCount) {
            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return false;
            }
        });
        mItemHandlers.put(SideHandlerType.NONE, new ItemStackHandler(0));

        for (EnumFacing facing : EnumFacing.values()) {
            if (facing == EnumFacing.DOWN) mItemHandlerTypes.put(facing, SideHandlerType.OUTPUT);
            else mItemHandlerTypes.put(facing, SideHandlerType.INPUT);
            mEnergyHandlerTypes.put(facing, SideHandlerType.INPUT);
        }
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt.hasKey("items")) {
            // 旧版本兼容
            ItemStackHandler older = new ItemStackHandler();
            older.deserializeNBT(nbt.getCompoundTag("items"));
            if (mItemHandlers.get(SideHandlerType.INPUT).getSlots() > 0)
                mItemHandlers.get(SideHandlerType.INPUT).setStackInSlot(0, older.getStackInSlot(0));
            if (older.getSlots() > 1 && mItemHandlers.get(SideHandlerType.OUTPUT).getSlots() > 0)
                mItemHandlers.get(SideHandlerType.OUTPUT).setStackInSlot(1, older.getStackInSlot(1));
            nbt.removeTag("items");
        }
        if (nbt.hasKey("inputs"))
            mItemHandlers.get(SideHandlerType.INPUT).deserializeNBT(nbt.getCompoundTag("inputs"));
        if (nbt.hasKey("outputs"))
            mItemHandlers.get(SideHandlerType.OUTPUT).deserializeNBT(nbt.getCompoundTag("outputs"));
        if (nbt.hasKey("energy"))
            mEnergyHandler.deserializeNBT(nbt.getCompoundTag("energy"));
        super.readFromNBT(nbt);
    }

    @Nonnull
    @Override
    public NBTTagCompound getUpdateTag() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    @SuppressWarnings("NullableProblems")
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag("inputs", mItemHandlers.get(SideHandlerType.INPUT).serializeNBT());
        nbt.setTag("outputs", mItemHandlers.get(SideHandlerType.OUTPUT).serializeNBT());
        nbt.setTag("energy", mEnergyHandler.serializeNBT());
        return nbt;
    }

    @Nullable
    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        return new SPacketUpdateTileEntity(this.pos, 0, this.serializeNBT());
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        this.deserializeNBT(pkt.getNbtCompound());
    }

    @Override
    public void update() {
        if (!world.isRemote) {
            logic();
            IBlockState state = world.getBlockState(pos);
            IBlockState newState = updateState(state);
            if (state != newState) {
                BlockUtil.setState(newState, world, pos);
                world.markBlockRangeForRenderUpdate(pos, pos);
            } else {
                markDirty(); // 咱们这么滥用 markDirty 真的没问题吗
                world.notifyBlockUpdate(pos, state, newState, 3);
            }
        }
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return (capability == CapabilityEnergy.ENERGY && mEnergyHandlerTypes.get(facing) != SideHandlerType.NONE)
                || (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY && hasItemHandler(facing))
                || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return capability.cast((T) mItemHandlers.get(mItemHandlerTypes.get(facing)));
        if (capability == CapabilityEnergy.ENERGY)
            return capability.cast((T) getEnergyProxy(facing));
        return super.getCapability(capability, facing);
    }

    protected RFEnergy.EnergyProxy getEnergyProxy(EnumFacing facing) {
        SideHandlerType type = mEnergyHandlerTypes.get(facing);
        switch (type) {
            case NONE: mEnergyHandler.new EnergyProxy(false, false);
            case IN_OUT: return mEnergyHandler.new EnergyProxy(true, true);
            case OUTPUT: return mEnergyHandler.new EnergyProxy(false, true);
            default: return mEnergyHandler.new EnergyProxy(true, false);
        }
    }

    public RFEnergy.EnergyProxy getReadonlyEnergyProxy() {
        return mEnergyHandler.new EnergyProxy(false, false);
    }

    private boolean hasItemHandler(EnumFacing facing) {
        SideHandlerType type = mItemHandlerTypes.get(facing);
        return mItemHandlers.get(type).getSlots() > 0;
    }

    /**
     * 会在update阶段调用
     */
    abstract void logic();

    /**
     * 终止当前任务
     */
    abstract void interrupt();

    /**
     * 用于校验输入区是否可以放入某物品栈
     * @param stack 要输入的物品栈
     * @param slot 输入的槽位
     */
    protected abstract boolean isInputItemValid(int slot, ItemStack stack);

    protected IBlockState updateState(IBlockState old) { return old; }

    public enum SideHandlerType {
        INPUT, // 物品：输入槽；能量：可输入
        OUTPUT, // 物品：输出槽；能量：可输出
        NONE, // 物品&能量：不可输入/输出
        IN_OUT; // 物品&能量：可输入/输出
    }
}
