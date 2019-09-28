package com.elementtimes.tutorial.client;

import com.elementtimes.elementcore.api.common.ECUtils;
import com.elementtimes.elementcore.api.tools.CreativeTabWrapper;
import com.elementtimes.tutorial.common.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.ForgeModContainer;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * 客户端 Proxy
 * @author qinglan,ksgfk,luqin2007
 */
@SideOnly(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        // 创造
        CreativeTabWrapper.apply(CreativeTabs.MISC, "misc").addPredicate(itemStack -> {
            Item item = itemStack.getItem();
            if (item == ForgeModContainer.getInstance().universalBucket) {
                FluidStack f = ECUtils.fluid.getFluid(itemStack);
                return !f.getFluid().getName().startsWith("elementtimes.");
            }
            return true;
        });
    }
}
