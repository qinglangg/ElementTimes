package com.elementtimes.tutorial.interfaces.tileentity;

import com.elementtimes.tutorial.common.block.base.BaseClosableMachine;
import com.elementtimes.tutorial.common.capability.impl.TankHandler;
import com.elementtimes.tutorial.other.recipe.MachineRecipeCapture;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.items.IItemHandler;

import javax.annotation.Nullable;

/**
 * 与机器每 tick 执行的工作有关的接口
 * @author luqin
 */
public interface IMachineTickable extends ITickable, INBTSerializable<NBTTagCompound>, IMachineLifeCycle.IMachineLifecycleManager {

    String TICKABLE = "_tickable_";
    String TICKABLE_IS_PAUSE = "_tickable_pause_";
    String TICKABLE_PROCESSED = "_tickable_processed_";
    String TICKABLE_UNPROCESSED = "_tickable_unprocessed_";

    @Override
    default void onPause() {
        IMachineLifecycleManager.super.onPause();
        setPause(true);
        setWorking(false);
    }

    @Override
    default void onResume() {
        IMachineLifecycleManager.super.onResume();
        setPause(false);
        setWorking(true);
    }

    @Override
    default boolean onFinish() {
        boolean ret = IMachineLifecycleManager.super.onFinish();
        setWorking(false);
        setPause(false);
        setEnergyUnprocessed(0);
        setEnergyProcessed(0);
        return ret;
    }

    /**
     * 终止当前任务
     */
    void interrupt();

    /**
     * @return 是否正在工作
     */
    boolean isWorking();

    /**
     * 设置工作状态
     */
    void setWorking(boolean isWorking);

    /**
     * @return 是否暂停
     */
    boolean isPause();

    /**
     * 设置暂停标志
     */
    void setPause(boolean isPause);

    /**
     * 获取已消耗能量
     */
    int getEnergyProcessed();

    /**
     * 设置已消耗能量
     */
    void setEnergyProcessed(int energy);

    /**
     * 获取合成仍需能量/发电机仍存能量
     */
    int getEnergyUnprocessed();

    /**
     * 设置。/。。
     */
    void setEnergyUnprocessed(int energy);

    default void processEnergy(int delta) {
        int unprocessed = getEnergyUnprocessed();
        if (unprocessed != 0) {
            if (unprocessed > 0) {
                int newUnprocessed = Math.max(0, unprocessed - delta);
                setEnergyUnprocessed(newUnprocessed);
                setEnergyProcessed(getEnergyProcessed() + (unprocessed - newUnprocessed));
            } else {
                int newUnprocessed = Math.min(0, unprocessed + delta);
                setEnergyUnprocessed(newUnprocessed);
                setEnergyProcessed(getEnergyProcessed() + (unprocessed - newUnprocessed));
            }
        }
    }

    /**
     * 检查当前机器条件是否可以进行对应合成表的合成
     * 通常包括是否具有此合成表，输入物体/流体/能量是否足够
     *
     * @return 通常意味着可以进行下一次合成。
     */
    default boolean isRecipeCanWork(@Nullable MachineRecipeCapture recipeCapture, IItemHandler itemHandler, TankHandler tankHandler) {
        if (recipeCapture == null) {
            return false;
        }
        if (itemHandler.getSlots() < recipeCapture.inputs.size()
                || tankHandler.getTankProperties().length < recipeCapture.fluidInputs.size()) {
            return false;
        }

        // items
        for (int i = recipeCapture.inputs.size() - 1; i >= 0; i--) {
            ItemStack item = recipeCapture.inputs.get(i);
            ItemStack extract = itemHandler.extractItem(i, item.getCount(), true);
            if (!item.isItemEqual(extract) || item.getCount() > extract.getCount()) {
                return false;
            }
        }
        // fluids
        for (int i = 0; i < recipeCapture.fluidInputs.size(); i++) {
            FluidStack fluid = recipeCapture.fluidInputs.get(i);
            FluidStack drain = tankHandler.drain(fluid, false);
            if (!fluid.isFluidEqual(drain) || fluid.amount > drain.amount) {
                return false;
            }
        }

        return true;
    }

    /**
     * 用于运行完成更新 IBlockState
     *
     * @param old 旧的 IBlockState
     * @return 新的 IBlockState
     */
    default IBlockState updateState(IBlockState old) {
        if (old.getPropertyKeys().contains(BaseClosableMachine.IS_RUNNING)) {
            boolean running = isWorking() && !isPause();
            if (old.getValue(BaseClosableMachine.IS_RUNNING) != running) {
                return old.withProperty(BaseClosableMachine.IS_RUNNING, running);
            }
        }
        return old;
    }

    @Override
    default void update() {
        if (this instanceof TileEntity) {
            TileEntity tileEntity = (TileEntity) this;
            World world = tileEntity.getWorld();
            BlockPos pos = tileEntity.getPos();
            if (!world.isRemote) {

                // 生命周期
                onTickStart();
                if (!isWorking()) {
                    if (!isPause()) {
                        if (onCheckStart()) {
                            onStart();
                            if (!onLoop()) {
                                onPause();
                            }
                        }
                    } else {
                        if (onCheckResume()) {
                            onResume();
                        }
                    }
                } else {
                    if (onCheckFinish()) {
                        if (!onFinish()) {
                            onPause();
                        } else if (onCheckStart()) {
                            onStart();
                        }

                    } else if (!onLoop()) {
                        onPause();
                    }
                }
                onTickFinish();

                IBlockState state = world.getBlockState(pos);
                IBlockState newState = updateState(state);
                if (state != newState) {
                    world.setBlockState(pos, state, 3);
                    tileEntity.validate();
                    world.setTileEntity(pos, tileEntity);
                }
                tileEntity.markDirty(); // 咱们这么滥用 markDirty 真的没问题吗
            }
        }
    }

    @Override
    default NBTTagCompound serializeNBT() {
        return writeToNBT(new NBTTagCompound());
    }

    @Override
    default void deserializeNBT(NBTTagCompound nbt) {
        if (nbt.hasKey(TICKABLE)) {
            NBTTagCompound tickable = nbt.getCompoundTag(TICKABLE);

            if (tickable.hasKey(TICKABLE_IS_PAUSE)) {
                setPause(tickable.getBoolean(TICKABLE_IS_PAUSE));
            }

            setWorking(false);

            if (tickable.hasKey(TICKABLE_PROCESSED)) {
                setEnergyProcessed(tickable.getInteger(TICKABLE_PROCESSED));
            }

            if (tickable.hasKey(TICKABLE_UNPROCESSED)) {
                setEnergyUnprocessed(tickable.getInteger(TICKABLE_UNPROCESSED));
            }
        }
    }

    default NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound tickable = new NBTTagCompound();
        tickable.setBoolean(TICKABLE_IS_PAUSE, isWorking() || isPause());
        tickable.setInteger(TICKABLE_PROCESSED, getEnergyProcessed());
        tickable.setInteger(TICKABLE_UNPROCESSED, getEnergyUnprocessed());
        nbt.setTag(TICKABLE, tickable);
        return nbt;
    }
}