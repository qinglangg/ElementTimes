package com.elementtimes.tutorial.annotation.enums;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

import java.util.function.Function;

public enum FluidBlockType {

    Classic(fluid -> new BlockFluidClassic(fluid, Material.WATER)),
    ClassicLava(fluid -> new BlockFluidClassic(fluid, Material.LAVA)),
    Finite(fluid -> new BlockFluidFinite(fluid, Material.WATER)),
    FiniteLava(fluid -> new BlockFluidFinite(fluid, Material.LAVA));

    private Function<Fluid, Block> creator;

    FluidBlockType(Function<Fluid, Block> creator) {
        this.creator = creator;
    }

    public Block create(Fluid fluid) {
        return creator.apply(fluid);
    }
}
