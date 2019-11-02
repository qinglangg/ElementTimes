package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.elementcore.api.annotation.ModInvokeStatic;
import com.elementtimes.elementcore.api.template.tileentity.BaseTileEntity;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import com.elementtimes.elementcore.api.template.tileentity.recipe.IngredientPart;
import com.elementtimes.elementcore.api.template.tileentity.recipe.MachineRecipeHandler;
import com.elementtimes.tutorial.common.init.ElementtimesBlocks;
import com.elementtimes.tutorial.common.init.ElementtimesFluids;
import com.elementtimes.tutorial.common.init.ElementtimesItems;

import com.elementtimes.tutorial.plugin.elementcore.JeiRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

@ModInvokeStatic("init")
public class TileBeaker extends BaseTileEntity implements IGuiProvider {
    @JeiRecipe.MachineRecipe(block = "elementtimes:beaker", gui = TileBeaker.class, u = 16, v = 7, w = 144, h = 77)
    public static MachineRecipeHandler RECIPE = new MachineRecipeHandler(3, 0, 2, 1);
    @JeiRecipe.MachineRecipe(block = "elementtimes:beaker", id = "elementtimes:beaker.stand", gui = TileBeaker.class, u = 16, v = 7, w = 144, h = 77)
    public static MachineRecipeHandler RECIPE_STAND = new MachineRecipeHandler(3, 0, 2, 1);

    public static void init() {
        if (RECIPE.getMachineRecipes().isEmpty() && RECIPE_STAND.getMachineRecipes().isEmpty()) {
            RECIPE_STAND
                    //盐溶解
                    .newRecipe()
                    .addItemInput(IngredientPart.forItem(ElementtimesItems.salt, 1))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionDilute, Fluid.BUCKET_VOLUME))
                    .endAdd()
                    //盐浓溶液稀释
                    .newRecipe()
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.NaClSolutionDilute, Fluid.BUCKET_VOLUME))
                    .addFluidInput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, Fluid.BUCKET_VOLUME))
                    .addFluidOutput(IngredientPart.forFluid(ElementtimesFluids.waterDistilled, Fluid.BUCKET_VOLUME))
                    .endAdd();
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
