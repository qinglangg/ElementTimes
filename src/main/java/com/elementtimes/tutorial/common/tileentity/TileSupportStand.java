package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.annotations.ModInvokeStatic;
import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.interfaces.tileentity.ITESRSupport;
import com.elementtimes.tutorial.network.SupportStandRenderNetwork;
import com.elementtimes.tutorial.other.RenderObject;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.lifecycle.FluidMachineLifecycle;
import com.elementtimes.tutorial.other.lifecycle.PremiseMachineLifecycle;
import com.elementtimes.tutorial.other.machineRecipe.IngredientPart;
import com.elementtimes.tutorial.other.machineRecipe.MachineRecipeHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.Slot;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.server.management.PlayerChunkMapEntry;
import net.minecraft.util.NonNullList;
import net.minecraft.world.WorldServer;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * @author KSGFK create in 2019/6/12
 */
@ModInvokeStatic("init")
public class TileSupportStand extends BaseMachine implements ITESRSupport {
    public static MachineRecipeHandler RECIPE_EMPTY = new MachineRecipeHandler();
    public static MachineRecipeHandler RECIPE_EVAPORATING_DISH = null;
    public static MachineRecipeHandler RECIPE_CRUCIBLE = null;

    private NonNullList<RenderObject> tesr = NonNullList.create();
    private NBTTagCompound properties = new NBTTagCompound();

    public int alcoholLamp = registerRender(RenderObject.create(ElementtimesBlocks.alcoholLamp, .5, -.13, .5));
    public int crucible = registerRender(RenderObject.create(ElementtimesBlocks.crucible, .5, .375, .5));
    public int evaporatingDish = registerRender(RenderObject.create(ElementtimesBlocks.evaporatingDish, .5, .375, .5));

    public TileSupportStand() {
        super(0, 3, 3, 2, 16000, 1, 16000);
        markBucketInput(0, 1, 2);
        addLifeCycle(new PremiseMachineLifecycle(this::isFire));
        addLifeCycle(new FluidMachineLifecycle(this, 2, 1));
    }

    public static void init() {
        if (RECIPE_EVAPORATING_DISH == null) {
            // 蒸发皿
            RECIPE_EVAPORATING_DISH = new MachineRecipeHandler()
            		.newRecipe("NaCl")
                    .addCost(0)
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionDilute,1000))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ethanol, 1000))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionConcentrated, 100))
                    .endAdd();
        }
        if (RECIPE_CRUCIBLE == null) {
            // 坩埚
            RECIPE_CRUCIBLE = new MachineRecipeHandler()
            		//CaCO3=CaO+co2
                    .newRecipe("CaO")
                    .addCost(0)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.calciumCarbonate,1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ethanol, 1000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.calciumOxide, 1))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.co2, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    .newRecipe("U")
                    .addCost(0)
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.uraniumPowder,1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.ethanol, 3000))
                    .addItemOutput(IngredientPart.forItem(ElementtimesItems.uranium, 3))
                    .endAdd()
                    
                  
            		;
        }
    }

    // ITESRSupport

    @Override
    @SideOnly(Side.CLIENT)
    public NonNullList<RenderObject> getRenderItems() {
        return tesr;
    }

    @Override
    public NBTTagCompound getRenderProperties() {
        return properties;
    }

    @Override
    public void setRenderProperties(NBTTagCompound properties) {
        this.properties = properties;
    }

    @Override
    public void markRenderClient() {
        if (world instanceof WorldServer) {
            PlayerChunkMapEntry entry = ((WorldServer) world).getPlayerChunkMap().getEntry(pos.getX() >> 4, pos.getZ() >> 4);
            if (entry != null) {
                for (EntityPlayerMP player : entry.getWatchingPlayers()) {
                    ElementTimes.CONTAINER.elements.channel.sendTo(new SupportStandRenderNetwork(ITESRSupport.super.serializeNBT(), world.provider.getDimension(), pos), player);
                }
            }
        }
    }

    @Override
    public void setRender(int index, boolean isRender) {
        ITESRSupport.super.setRender(index, isRender);
        markRebuildFluids();
        markRebuildSlots();
        createRecipe();
    }

    // gui

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        if (isRender(alcoholLamp) && isRender(evaporatingDish)) {
            return ElementtimesGUI.Machines.SupportStandAL;
        } else if (isRender(alcoholLamp) && isRender(crucible)) {
            return ElementtimesGUI.Machines.SupportStandC;
        }
        return null;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler createRecipe() {
        if (isRender(alcoholLamp) && isRender(evaporatingDish)) {
            return RECIPE_EVAPORATING_DISH;
        } else if (isRender(alcoholLamp) && isRender(crucible)) {
            return RECIPE_CRUCIBLE;
        }
        return RECIPE_EMPTY;
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        if ((isRender(alcoholLamp) && isRender(evaporatingDish))
                || (isRender(alcoholLamp) && isRender(crucible))) {
            return new Slot[] {
                    new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 37, 16),
                    new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 101, 16),
                    new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 71, 85),
                    new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 37, 34),
                    new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 101, 34),
                    new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 89, 85),
            };
        }
        return super.createSlots();
    }

    @Nonnull
    @Override
    public Map<SideHandlerType, Int2ObjectMap<int[]>> createFluids() {
        if ((isRender(alcoholLamp) && isRender(evaporatingDish))
                || (isRender(alcoholLamp) && isRender(crucible))) {
            Map<SideHandlerType, Int2ObjectMap<int[]>> fluids = new HashMap<>(3);
            fluids.put(SideHandlerType.INPUT, new Int2ObjectArrayMap<>(new int[] {0, 1}, new int[][] {
                    new int[] {58, 10, 16, 46}, new int[] {65, 64, 46, 16}
            }));
            fluids.put(SideHandlerType.OUTPUT, new Int2ObjectArrayMap<>(new int[] {0}, new int[][] {new int[] {122, 10, 16, 46}}));
            return fluids;
        }
        return super.createFluids();
    }

    @Override
    public boolean isHorizontalFluidSlot(SideHandlerType type, int index) {
        return isRender(alcoholLamp) && isRender(evaporatingDish) && type == SideHandlerType.INPUT && index == 1;
    }

    public void setFire(boolean fire) {
        NBTTagCompound renderProperties = getRenderProperties();
        if (renderProperties != null) {
            renderProperties.setBoolean("fire", fire);
            markRenderClient();
        }
    }

    public boolean isFire() {
        NBTTagCompound renderProperties = getRenderProperties();
        if (renderProperties != null && renderProperties.hasKey("fire")) {
            return renderProperties.getBoolean("fire");
        }
        return false;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        ITESRSupport.super.readFromNBT(nbt);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        return ITESRSupport.super.writeToNBT(nbt);
    }

    @Override
    public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
        super.onDataPacket(net, pkt);
        NBTTagCompound nbt = pkt.getNbtCompound();
        if (nbt.hasKey(ITESRSupport.BIND_NBT_TESR_TE)) {
            ITESRSupport.super.deserializeNBT(nbt);
        }
    }
}
