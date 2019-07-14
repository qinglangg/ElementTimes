package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.annotations.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.interfaces.tileentity.ITESRSupport;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.lifecycle.CustomMachineLifecycle;
import com.elementtimes.tutorial.other.lifecycle.FluidMachineLifecycle;
import com.elementtimes.tutorial.other.recipe.IngredientPart;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.SlotItemHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * tesr数组
 * 0：酒精灯
 * 1：蒸发皿
 *
 * @author KSGFK create in 2019/6/12
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileSupportStand extends BaseMachine implements ITESRSupport {
    public boolean isFire;
    public boolean canOpenGui = false;

    public static MachineRecipeHandler mRecipe = new MachineRecipeHandler();

    private List tesr = new ArrayList<>();

    public TileSupportStand() {
        super(0, 3, 3, 2, 16000, 1, 16000);
        removeLifecycle(mDefaultMachineLifecycle);
        markBucketInput(0, 1, 2);
        addLifeCycle(new CustomMachineLifecycle(this));
        addLifeCycle(new FluidMachineLifecycle(this, 3, 3));
        initCanRendItems();
    }

    public static void init() {
        mRecipe.newRecipe("0")
                .addCost(0)
                .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaCl, 1000))
                .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionConcentrated, 100))
                .endAdd();
    }

    //ITESRSupport
    @Override
    @SideOnly(Side.CLIENT)
    public List<com.elementtimes.tutorial.client.util.RenderObject> getRenderItems() {
        return tesr;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void initCanRendItems() {
        Vec3d v1 = new Vec3d(0.5F, -0.13F, 0.5F);
        Vec3d v2 = new Vec3d(0.5F, 0.375F, 0.5F);
        tesr.add(new com.elementtimes.tutorial.client.util.RenderObject(Item.getItemFromBlock(ElementtimesBlocks.alcoholLamp), v1).setRender(false));
        tesr.add(new com.elementtimes.tutorial.client.util.RenderObject(Item.getItemFromBlock(ElementtimesBlocks.evaporatingDish), v2).setRender(false));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean setRender(int index, boolean isRender) {
        ((com.elementtimes.tutorial.client.util.RenderObject) tesr.get(index)).setRender(isRender);
        return isRender;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public boolean isRender(int index) {
        return ((com.elementtimes.tutorial.client.util.RenderObject) tesr.get(index)).isRender();
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void setRenderItemState(int index, ItemStack state) {
        ((com.elementtimes.tutorial.client.util.RenderObject) tesr.get(index)).obj = state;
    }

    @Override
    public ElementtimesGUI.Machines getGuiType() {
        if (canOpenGui) {
            return ElementtimesGUI.Machines.SupportStand;
        } else {
            return null;
        }
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return mRecipe;
    }

    @Nonnull
    @Override
    public Slot[] createSlots() {
        return new Slot[] {
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 0, 37, 16),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 1, 101, 16),
                new SlotItemHandler(getItemHandler(SideHandlerType.INPUT), 2, 71, 85),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 0, 37, 34),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 1, 101, 34),
                new SlotItemHandler(getItemHandler(SideHandlerType.OUTPUT), 2, 89, 85),
        };
    }

    @Nonnull
    @Override
    public Map<SideHandlerType, Int2ObjectMap<int[]>> createFluids() {
        Map<SideHandlerType, Int2ObjectMap<int[]>> fluids = new HashMap<>(3);
        fluids.put(SideHandlerType.INPUT, new Int2ObjectArrayMap<>(new int[] {0, 1}, new int[][] {
                new int[] {58, 10, 16, 46}, new int[] {65, 64, 46, 16}
        }));
        fluids.put(SideHandlerType.OUTPUT, new Int2ObjectArrayMap<>(new int[] {0}, new int[][] {new int[] {122, 10, 16, 46}}));
        return fluids;
    }

    @Override
    public boolean isHorizontalFluidSlot(SideHandlerType type, int index) {
        return type == SideHandlerType.INPUT && index == 1;
    }
}
