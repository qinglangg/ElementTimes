package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.ModElement;
import com.elementtimes.tutorial.common.init.ElementtimesItems;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.other.MachineRecipeHandler;
import com.elementtimes.tutorial.util.RecipeUtil;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.Map;

/**
 * 成型机
 * @author luqin2007
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileForming extends BaseOneToOne {

    private Mode mode = Mode.Gear;

    public TileForming() {
        super(ElementtimesConfig.FORMING.maxEnergy);
    }

    public void setMode(Mode mode) {
        if (this.mode != mode) {
            interrupt();
            this.mode = mode;
            updateRecipe(getRecipes());
        }
    }

    enum Mode {
        /**
         * 成型模式
         */
        Forming,

        /**
         * 齿轮模式
         */
        Gear
    }

    public static MachineRecipeHandler sGearRecipeHandler;
    public static MachineRecipeHandler sFormingRecipeHandler;

    public static void init() {
        sGearRecipeHandler = new MachineRecipeHandler()
                .add("1", ElementtimesConfig.FORMING.maxExtract, "plankWood", 4, ElementtimesItems.gearWood, 1)
                .add("2", ElementtimesConfig.FORMING.maxExtract, "gemQuartz", 4, ElementtimesItems.gearQuartz, 1)
                .add("3", ElementtimesConfig.FORMING.maxExtract, "stone", 4, ElementtimesItems.gearStone, 1)
                .add("4", ElementtimesConfig.FORMING.maxExtract, "coal", 4, ElementtimesItems.gearCarbon, 1)
                .add("5", ElementtimesConfig.FORMING.maxExtract, "ingotGold", 4, ElementtimesItems.gearGold, 1)
                .add("6", ElementtimesConfig.FORMING.maxExtract, "ingotSteel", 4, ElementtimesItems.gearSteel, 1)
                .add("7", ElementtimesConfig.FORMING.maxExtract, "gemDiamond", 4, ElementtimesItems.gearDiamond, 1)
                .add("8", ElementtimesConfig.FORMING.maxExtract, "ingotIron", 4, ElementtimesItems.gearIron, 1)
                .add("9", ElementtimesConfig.FORMING.maxExtract, "ingotPlatinum", 4, ElementtimesItems.gearPlatinum, 1)
                .add("0", ElementtimesConfig.FORMING.maxExtract, "ingotCopper", 4, ElementtimesItems.gearCopper, 1);
        sFormingRecipeHandler = new MachineRecipeHandler();
        Map<ItemStack, ItemStack> woods = new HashMap<>();
        RecipeUtil.collectOneBlockCraftingResult("logWood", woods);
        woods.forEach((in, out) -> sFormingRecipeHandler.add(in.getUnlocalizedName() + "_to_" + out.getUnlocalizedName(), ElementtimesConfig.FORMING.maxExtract, in, out));

    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return mode == Mode.Forming ? sFormingRecipeHandler : sGearRecipeHandler;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.FORMING.maxReceive);
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.FORMING.maxExtract;
    }
}
