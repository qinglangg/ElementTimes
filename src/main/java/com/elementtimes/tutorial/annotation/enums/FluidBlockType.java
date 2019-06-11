package com.elementtimes.tutorial.annotation.enums;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraftforge.fluids.BlockFluidClassic;
import net.minecraftforge.fluids.BlockFluidFinite;
import net.minecraftforge.fluids.Fluid;

import java.util.function.Function;

/**
 * @author luqin2007
 */
public enum FluidBlockType {

    /**
     * 创建 BlockFluidClassic 作为流体方块，材质为 Material.WATER
     */
    Classic(fluid -> new BlockFluidClassic(fluid, Material.WATER)),
    /**
     * 创建 BlockFluidClassic 作为流体方块，材质为 Material.LAVA
     */
    ClassicLava(fluid -> new BlockFluidClassic(fluid, Material.LAVA)),
    /**
     * 创建 BlockFluidFinite 作为流体方块，材质为 Material.WATER
     */
    Finite(fluid -> new BlockFluidFinite(fluid, Material.WATER)),
    /**
     * 创建 BlockFluidFinite 作为流体方块，材质为 Material.LAVA
     */
    FiniteLava(fluid -> new BlockFluidFinite(fluid, Material.LAVA));

    private Function<Fluid, Block> creator;

    FluidBlockType(Function<Fluid, Block> creator) {
        this.creator = creator;
    }

    public Block create(Fluid fluid) {
        return creator.apply(fluid);
    }
}
