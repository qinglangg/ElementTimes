package com.elementtimes.tutorial.other.lifecycle;

import com.elementtimes.tutorial.common.capability.impl.TankHandler;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import com.elementtimes.tutorial.common.tileentity.TileSupportStand;
import com.elementtimes.tutorial.interfaces.tileentity.IMachineLifecycle;
import com.elementtimes.tutorial.other.SideHandlerType;
import com.elementtimes.tutorial.other.recipe.MachineRecipeCapture;
import com.elementtimes.tutorial.util.FluidUtil;
import it.unimi.dsi.fastutil.ints.Int2IntMap;
import it.unimi.dsi.fastutil.ints.Int2IntOpenHashMap;
import net.minecraftforge.fluids.FluidStack;

import java.util.Collections;
import java.util.List;

/**
 * @author KSGFK create in 2019/6/20
 */
public class CustomMachineLifecycle implements IMachineLifecycle {
    private BaseMachine machine;
    private MachineRecipeCapture[] recipes;
    private MachineRecipeCapture now;
    private TankHandler i;
    private TankHandler o;
    private Int2IntMap mBindInputToOutputMap = new Int2IntOpenHashMap();

    public CustomMachineLifecycle(final BaseMachine machine) {
        this.machine = machine;
        i = machine.getTanks(SideHandlerType.INPUT);
        o = machine.getTanks(SideHandlerType.OUTPUT);
    }

    @Override
    public boolean onCheckStart() {
        if (!(machine instanceof TileSupportStand)) {
            return false;
        }
        TileSupportStand t = (TileSupportStand) machine;
        List<FluidStack> f = FluidUtil.toListNotNull(i.getTankProperties());
        recipes = machine.getRecipes().matchInput(Collections.emptyList(), f);
        now = machine.getNextRecipe(machine.getItemHandler(SideHandlerType.INPUT), i);
        if (recipes.length <= 0) {
            return false;
        }
        if (!t.isRender(0)) {
            return false;
        }
        if (FluidUtil.isFull(o.getTankProperties())) {
            return false;
        }
        //TODO:酒精灯容量检查
        return true;
    }

    @Override
    public void onStart() {
        if (now == null) {
            return;
        }
        machine.setWorkingRecipe(now);
    }

    @Override
    public boolean onLoop() {
        int max = Math.max(now.fluidInputs.size(), now.fluidOutputs.size());
        for (int a = 0; a < max; a++) {
            if (now.fluidInputs.size() > a) {
                FluidStack fluid = now.fluidInputs.get(a);
                FluidStack r = i.drain(0, fluid, false);
                i.drain(0, r, true);
            }
            if (now.fluidOutputs.size() > a) {
                FluidStack fluid = now.fluidOutputs.get(a);
                int r = o.fill(0, fluid, false);
                if (r == 0) {
                    continue;
                }
                o.drain(0, r, true);
            }
        }
        return false;
    }

    @Override
    public boolean onCheckFinish() {
        return FluidUtil.isEmpty(i.getTankProperties());
    }

    @Override
    public boolean onCheckResume() {
        if (!(machine instanceof TileSupportStand)) {
            return false;
        }
        TileSupportStand t = (TileSupportStand) machine;
        return !t.isRender(0);
    }

    @Override
    public boolean onFinish() {
        return FluidUtil.isFull(o.getTankProperties());
    }
}
