package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.template.capability.fluid.ITankHandler;
import com.elementtimes.elementcore.api.template.capability.fluid.TankHandler;
import com.elementtimes.elementcore.api.template.capability.item.IItemHandler;
import com.elementtimes.elementcore.api.template.capability.item.ItemHandler;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.SideHandlerType;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.ITileTESR;
import com.elementtimes.elementcore.api.template.tileentity.lifecycle.FluidMachineLifecycle;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeCapture;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.block.AlcoholLamp;
import com.elementtimes.tutorial.common.block.SupportStand;
import com.elementtimes.tutorial.common.block.interfaces.ISupportStandModule;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fluids.FluidStack;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/**
 * @author KSGFK create in 2019/6/12
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class TileSupportStand extends BaseTileEntity implements ITileTESR {

    public static MachineRecipeHandler RECIPE_EMPTY = new MachineRecipeHandler(0, 0, 0, 0);

    private static final String NBT_RAIN_COLD_DOWN = "_nbt_bind_support_stand_cold_down_";
    private static final String NBT_ALCOHOL = "_nbt_bind_support_stand_alcohol_";

    private HashMap<String, RenderObject> tesr;
    private NBTTagCompound properties;
    private MachineRecipeHandler recipe = RECIPE_EMPTY;
    private IGuiProvider displayGui = null;

    private int rainOutFireColdDown = 0;
    private ITankHandler mEthanolTank = new TankHandler((i, f) -> f.getFluid() == ElementtimesFluids.ethanol, TankHandler.TRUE, 1, AlcoholLamp.ALCOHOL_AMOUNT);

    public TileSupportStand() {
        super(-1, 0, 0, 0, 0, 0, 0);
        addLifeCycle(new AlcoholLampLifeCycle());
    }

    @Override
    public ITankHandler getTanks(SideHandlerType type) {
        if (type == SideHandlerType.NONE) {
            return mEthanolTank;
        }
        return super.getTanks(type);
    }

    // 机器切换
    public void clear() {
        recipe = RECIPE_EMPTY;
        displayGui = null;
        resetHandler();
        resetRecipe(null, 0);
        setEnergyProcessed(0);
        markBucketInput();
        getAllLifecycles().removeIf(o -> o instanceof FluidMachineLifecycle);
    }
    public void initSubBlock(@Nullable String renderKey, MachineRecipeHandler handler, MachineRecipeCapture recipe, int process, FluidStack[] fluids, ItemStack[] items) {
        if (renderKey != null) {
            setRender(renderKey, true, pos);
        }
        this.recipe = handler;
        resetHandler();
        resetRecipe(recipe, process);
        fillHandlers(fluids, items);
    }
    public void renderModule(ISupportStandModule module) {
        if (!isRenderRegistered(module.getKey())) {
            registerRender(module.getKey(), module.createRender());
        }
        setRender(module.getKey(), true, pos);
    }
    private void resetHandler() {
        setItemHandler(SideHandlerType.INPUT, new ItemHandler(recipe.inputItemCount + recipe.inputFluidCount + recipe.outputFluidCount, this::isInputValid));
        setItemHandler(SideHandlerType.OUTPUT, new ItemHandler(recipe.outputItemCount + recipe.inputFluidCount + recipe.outputFluidCount, ItemHandler.TRUE));
        setTanks(SideHandlerType.INPUT, new TankHandler(this::isFillValid, TankHandler.FALSE, recipe.inputFluidCount, 16000));
        setTanks(SideHandlerType.OUTPUT, new TankHandler(TankHandler.FALSE, TankHandler.TRUE, recipe.outputFluidCount, 16000));
        getAllLifecycles().removeIf(o -> o instanceof FluidMachineLifecycle);
        addLifeCycle(new FluidMachineLifecycle(this));
    }
    private void resetRecipe(MachineRecipeCapture recipe, int process) {
        setWorkingRecipe(recipe);
        setEnergyProcessed(process);
        interrupt();
    }
    private void fillHandlers(FluidStack[] fluids, ItemStack[] items) {
        ITankHandler inputFluids = getTanks(SideHandlerType.INPUT);
        ITankHandler outputFluids = getTanks(SideHandlerType.OUTPUT);
        for (int i = 0; i < recipe.inputFluidCount; i++) {
            inputFluids.fillIgnoreCheck(i, fluids[i], true);
        }
        for (int i = 0; i < recipe.outputFluidCount; i++) {
            inputFluids.fillIgnoreCheck(i, fluids[recipe.inputFluidCount + i], true);
        }
        IItemHandler inputHandler = getItemHandler(SideHandlerType.INPUT);
        IItemHandler outputHandler = getItemHandler(SideHandlerType.OUTPUT);
        for (int i = 0; i < recipe.inputItemCount; i++) {
            outputHandler.insertItemIgnoreValid(i, items[i], false);
        }
        for (int i = 0; i < recipe.outputItemCount; i++) {
            outputHandler.insertItemIgnoreValid(i, items[recipe.inputItemCount + i], false);
        }
    }

    // Machine
    @Override
    public int getEnergyTick() {
        return 1;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler getRecipes() {
        return recipe;
    }

    @Override
    public HashMap<String, RenderObject> getRenderItems() {
        if (tesr == null) {
            tesr = new HashMap<>(10);
            for (ISupportStandModule module : SupportStand.MODULES) {
                registerRender(module.getKey(), module.createRender());
            }
        }
        return tesr;
    }

    @Nonnull
    @Override
    public NBTTagCompound getRenderProperties() {
        if (properties == null) {
            properties = new NBTTagCompound();
        }
        return properties;
    }

    @Override
    public void setRenderProperties(@Nonnull NBTTagCompound properties) {
        this.properties = properties;
        markRenderClient(pos);
    }

    // gui
    @Override
    public int getGuiId() {
        for (ISupportStandModule module : SupportStand.MODULES) {
            if (module.isAdded(world, pos)) {
                IGuiProvider provider = module.getGuiProvider(world, pos);
                if (provider != null) {
                    displayGui = provider;
                    return provider.getGuiId();
                }
            }
        }
        return -1;
    }

    @Override
    public ResourceLocation getBackground() {
        return displayGui == null ? null : displayGui.getBackground();
    }

    @Override
    public GuiSize getSize() {
        return displayGui == null ? null : displayGui.getSize();
    }

    @Override
    public String getTitle() {
        return displayGui == null ? null : displayGui.getTitle();
    }

    @Override
    public List<EntityPlayerMP> getOpenedPlayers() {
        return displayGui == null ? Collections.emptyList() : displayGui.getOpenedPlayers();
    }

    @Nonnull
    @Override
    public Slot[] getSlots() {
        return displayGui == null ? IGuiProvider.ITEM_NULL : displayGui.getSlots();
    }

    @Nonnull
    @Override
    public FluidSlotInfo[] getFluids() {
        return displayGui == null ? IGuiProvider.FLUID_NULL : displayGui.getFluids();
    }

    @Override
    public float getProcess() {
        return displayGui == null ? 0 : displayGui.getProcess();
    }

    @Override
    public void onGuiClosed(EntityPlayer player) {
        if (displayGui != null) {
            displayGui.onGuiClosed(player);
        }
        displayGui = null;
    }

    // NBT
    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        ITileTESR.super.readFromNBT(nbt);
        rainOutFireColdDown = nbt.getInteger(NBT_RAIN_COLD_DOWN);
        mEthanolTank.deserializeNBT(nbt.getCompoundTag(NBT_ALCOHOL));
        markRenderClient(pos);
    }

    @Nonnull
    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        nbt = super.writeToNBT(nbt);
        nbt.setInteger(NBT_RAIN_COLD_DOWN, rainOutFireColdDown);
        nbt.setTag(NBT_ALCOHOL, mEthanolTank.serializeNBT());
        return ITileTESR.super.writeRenderNbt(nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound nbt = pkt.getNbtCompound();
        ITileTESR.super.deserializeNBT(nbt);
    }

    @Override
    public void update() {
        update(this);
    }

    private class AlcoholLampLifeCycle implements IMachineLifecycle {
        @Override
        public void onTickStart() {
            boolean isFire = ((ISupportStandModule) ElementtimesBlocks.alcoholLamp).isAdded(world, pos)
                    && getRenderProperties().getBoolean(AlcoholLamp.BIND_ALCOHOL);
            // 雨天无阻挡：几率灭火
            if (rainOutFireColdDown > 0) {
                rainOutFireColdDown--;
            } else {
                if (world.isRainingAt(pos) && isFire && world.rand.nextFloat() <= AlcoholLamp.ALCOHOL_RAIN_PROBABILITY) {
                    boolean needOutFire = true;
                    for (int h = pos.getY(); h < world.getActualHeight(); h++) {
                        final BlockPos pos = new BlockPos(TileSupportStand.this.pos.getX(), h, TileSupportStand.this.pos.getZ());
                        final IBlockState state = world.getBlockState(pos);
                        if (state.getBlock().isAir(state, world, pos)) {
                            needOutFire = false;
                        }
                    }
                    if (needOutFire) {
                        getRenderProperties().setBoolean(AlcoholLamp.BIND_ALCOHOL, false);
                        markRenderClient(pos);
                    } else {
                        // 10s 内不在检查
                        rainOutFireColdDown = 2000;
                    }
                }
            }
            // 酒精消耗
            if (isFire) {
                FluidStack drain = getTanks(SideHandlerType.NONE).drain(0, AlcoholLamp.ALCOHOL_TICK, false);
                if (drain == null || drain.amount < AlcoholLamp.ALCOHOL_TICK) {
                    getRenderProperties().setBoolean(AlcoholLamp.BIND_ALCOHOL, false);
                    markRenderClient(pos);
                    return;
                } else {
                    getTanks(SideHandlerType.NONE).drain(0, AlcoholLamp.ALCOHOL_TICK, true);
                }
            }

            markDirty();
        }

        @Override
        public boolean onCheckStart() {
            return getRenderProperties().getBoolean(AlcoholLamp.BIND_ALCOHOL);
        }

        @Override
        public boolean onLoop() {
            return getRenderProperties().getBoolean(AlcoholLamp.BIND_ALCOHOL);
        }

        @Override
        public boolean onCheckResume() {
            return getRenderProperties().getBoolean(AlcoholLamp.BIND_ALCOHOL);
        }
    }
}
