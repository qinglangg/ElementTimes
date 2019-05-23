package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.client.gui.base.GuiContainerGenerator;
import com.elementtimes.tutorial.client.gui.base.GuiContainerOneToOne;
import com.elementtimes.tutorial.common.tileentity.TileCompressor;
import com.elementtimes.tutorial.common.tileentity.TileElementGenerator;
import com.elementtimes.tutorial.common.tileentity.TilePulverize;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;

/**
 * GUI初始化
 *
 * @author KSGFK create in 2019/2/17
 */
public class ElementtimesGUI implements IGuiHandler {

    public static final int ElementGenerator = 0;
    public static final int Pulverize = 1;
    public static final int Compressor = 2;
    public static final int FuelGenerator = 3;
    public static final int Furnace = 4;

    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Elementtimes.instance, this);
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case ElementGenerator:
                return new ContainerMachine((TileElementGenerator) world.getTileEntity(new BlockPos(x, y, z)), player);
            case Pulverize:
                return new ContainerMachine((TilePulverize) world.getTileEntity(new BlockPos(x, y, z)), player);
            case Compressor:
                return new ContainerMachine((TileCompressor) world.getTileEntity(new BlockPos(x, y, z)), player);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case ElementGenerator:
                return new GuiContainerGenerator(new ContainerMachine((TileElementGenerator) world.getTileEntity(new BlockPos(x, y, z)), player));
            case Pulverize:
                return new GuiContainerOneToOne(new ContainerMachine((TilePulverize) world.getTileEntity(new BlockPos(x, y, z)), player));
            case Compressor:
                return new GuiContainerOneToOne(new ContainerMachine((TileCompressor) world.getTileEntity(new BlockPos(x, y, z)), player));
            default:
                return null;
        }
    }
}
