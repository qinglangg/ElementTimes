package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

@ModInvokeStatic("init")
public class TileBeaker extends BaseTileEntity implements IGuiProvider {

    public static MachineRecipeHandler RECIPE = null, RECIPE_STAND = null;

    public static void init() {
        if (RECIPE == null || RECIPE_STAND == null) {
            RECIPE = new MachineRecipeHandler(3, 0, 2, 1);
            RECIPE_STAND = new MachineRecipeHandler(3, 0, 2, 1);
        }
    }

    public TileBeaker() {
        super(0, 3, 0, 2, 16000, 1, 16000);
    }

    public TileBeaker(World world, BlockPos pos) {
        this();
        this.world = world;
        this.pos = pos;
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation("elementtimes", "textures/gui/beaker");
    }

    @Override
    public GuiSize getSize() {
        return null;
    }

    @Override
    public String getTitle() {
        return null;
    }

    @Override
    public int getGuiId() {
        return 0;
    }

    @Override
    public void update() {
        update(this);
    }

    @Override
    public MachineRecipeHandler getRecipes() {
        if (world.getBlockState(pos) == ElementtimesBlocks.supportStand) {
            return RECIPE_STAND;
        }
        return RECIPE;
    }
}
