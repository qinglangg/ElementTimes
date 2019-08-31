package com.elementtimes.tutorial.client;

import com.elementtimes.elementcore.api.ECUtils;
import com.elementtimes.tutorial.common.CommonProxy;
import com.elementtimes.tutorial.other.MiscTabWrapper;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

/**
 * 客户端 Proxy
 * @author KSGFK
 */
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        // 创造
        MiscTabWrapper.apply().addPredicate(itemStack -> {
            Item item = itemStack.getItem();
            if (item == ForgeModContainer.getInstance().universalBucket) {
                FluidStack f = ECUtils.fluid.getFluid(itemStack);
                return !f.getFluid().getName().startsWith("elementtimes.");
            }
            return true;
        });
    }
}
