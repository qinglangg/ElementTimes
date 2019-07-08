package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.annotations.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesGUI;
import com.elementtimes.tutorial.interfaces.tileentity.ITESRSupport;
import com.elementtimes.tutorial.other.lifecycle.CustomMachineLifecycle;
import com.elementtimes.tutorial.other.recipe.IngredientPart;
import com.elementtimes.tutorial.other.recipe.MachineRecipeHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

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

    public static MachineRecipeHandler mRecipe = new MachineRecipeHandler();

    private List tesr = new ArrayList<>();

    public TileSupportStand() {
        super(0, 0, 0, 1, 16000, 1, 16000);
        removeLifecycle(mDefaultMachineLifecycle);
        addLifeCycle(new CustomMachineLifecycle(this));
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

    //我也不知道什么接口需要的
    @Override
    public ElementtimesGUI.Machines getGuiType() {
        return null;
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return mRecipe;
    }
}
