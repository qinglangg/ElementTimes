package com.elementtimes.tutorial.common.tileentity;

import com.elementtimes.tutorial.annotation.ModElement;
import com.elementtimes.tutorial.config.ElementtimesConfig;
import com.elementtimes.tutorial.interfaces.item.IGeneratorElement;
import com.elementtimes.tutorial.other.MachineRecipeHandler;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * 元素发电机的 TileEntity
 * @author KSGFK create in 2019/2/17
 */
@ModElement
@ModElement.ModInvokeStatic("init")
public class TileGeneratorElement extends BaseGenerator {

    public TileGeneratorElement() {
        super(ElementtimesConfig.GENERAL.generaterMaxEnergy);
    }

    public static MachineRecipeHandler sRecipeHandler;

    public static void init() {
        sRecipeHandler = new MachineRecipeHandler()
                .add("0")
                .addCost((i) -> {
                    List<ItemStack> inputs = i.inputs;
                    if (inputs.isEmpty() || inputs.get(0).isEmpty()) {
                        return 0;
                    }
                    Item item = inputs.get(0).getItem();
                    if (item instanceof IGeneratorElement) {
                        return -((IGeneratorElement) item).getEnergy();
                    }
                    return 0;
                })
                .addItemInput(itemStack -> !itemStack.isEmpty() && itemStack.getItem() instanceof IGeneratorElement,
                              itemStack -> ItemHandlerHelper.copyStackWithSize(itemStack, 1))
                .build();
    }

    @Nonnull
    @Override
    public MachineRecipeHandler updateRecipe(@Nonnull MachineRecipeHandler recipe) {
        return sRecipeHandler;
    }

    @Override
    public void applyConfig() {
        setMaxTransfer(ElementtimesConfig.GENERAL.generaterMaxExtract);
    }

    @Override
    public int getMaxEnergyChange() {
        return ElementtimesConfig.GENERAL.generaterMaxReceive;
    }
}
