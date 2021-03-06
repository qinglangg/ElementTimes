package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.ModGui;
import com.elementtimes.elementcore.api.template.gui.server.BaseContainer;
import com.elementtimes.elementcore.api.template.tileentity.interfaces.IGuiProvider;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

/**
 * GUI初始化
 * @author KSGFK create in 2019/2/17
 */
@ModGui
public class ElementtimesGUI implements IGuiHandler {

    public enum Machines {
        /**
         * 所有机器 GUI 枚举，目前只为提供 id， id 目前没用
         */
        ElementGenerator,
        FuelGenerator,
        FluidGenerator,
        Pulverize,
        Compressor,
        Furnace,
        Rebuild,
        Extractor,
        Forming,
        ItemReducer,
        SolidMelter,
        FluidReactor,
        SolidReactor,
        Condenser,
        FluidHeater,
        ElectrolyticCell,
        SolidFluidReactor,
        PumpAir,
        PumpFluid,
        Centrifuge,
        Coagulator,
        SolidCentrifuge,
        SupportStandED,
        SupportStandC,
        SolarDecomposer,
        Fermenter,
        Test,
        Beaker;

        public int id() {
            return ordinal();
        }
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity te = world.getTileEntity(new BlockPos(x, y, z));
        if (te instanceof IGuiProvider) {
            return ((IGuiProvider) te).getGuiId() >= 0 ? new BaseContainer(te, (IGuiProvider) te, player) : null;
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity instanceof IGuiProvider) {
            IGuiProvider provider = (IGuiProvider) tileEntity;
            if (provider.getGuiId() >= 0) {
                return new com.elementtimes.elementcore.api.template.gui.client.BaseGuiContainer(
                        new BaseContainer(tileEntity, provider, player));
            }
        }
        return getServerGuiElement(id, player, world, x, y, z);
    }
}
