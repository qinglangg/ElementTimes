package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.client.gui.base.GuiContainerGenerator;
import com.elementtimes.tutorial.client.gui.base.GuiContainerOneToOne;
import com.elementtimes.tutorial.common.tileentity.*;
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
public class ElementtimesGui implements IGuiHandler {

    public static ElementtimesGUI GUI;

    public static final int ELEMENT_GENERATOR = 0;
    public static final int PULVERIZE = 1;
    public static final int COMPRESSOR = 2;
    public static final int FUEL_GENERATOR = 3;
    public static final int FURNACE = 4;
    public static final int REBUILD = 5;
    public static final int EXTRACTOR = 6;

    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(ElementTimes.instance, this);
        GUI = this;
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case ELEMENT_GENERATOR:
                return new ContainerMachine<>((TileGeneratorElement) world.getTileEntity(new BlockPos(x, y, z)), player);
            case PULVERIZE:
                return new ContainerMachine<>((TilePulverize) world.getTileEntity(new BlockPos(x, y, z)), player);
            case COMPRESSOR:
                return new ContainerMachine<>((TileCompressor) world.getTileEntity(new BlockPos(x, y, z)), player);
            case FUEL_GENERATOR:
                return new ContainerMachine<>((TileGeneratorFuel) world.getTileEntity(new BlockPos(x, y, z)), player);
            case FURNACE:
                return new ContainerMachine<>((TileFurnace) world.getTileEntity(new BlockPos(x, y, z)), player);
            case REBUILD:
                return new ContainerMachine<>((TileRebuild) world.getTileEntity(new BlockPos(x, y, z)), player);
            case EXTRACTOR:
                return new ContainerMachine<>((TileExtractor) world.getTileEntity(new BlockPos(x, y, z)), player);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        switch (id) {
            case ELEMENT_GENERATOR:
                return new GuiContainerGenerator<>(new ContainerMachine<>((TileGeneratorElement) world.getTileEntity(new BlockPos(x, y, z)), player));
            case PULVERIZE:
                return new GuiContainerOneToOne<>(new ContainerMachine<>((TilePulverize) world.getTileEntity(new BlockPos(x, y, z)), player));
            case COMPRESSOR:
                return new GuiContainerOneToOne<>(new ContainerMachine<>((TileCompressor) world.getTileEntity(new BlockPos(x, y, z)), player));
            case FUEL_GENERATOR:
                return new GuiContainerGenerator<>(new ContainerMachine<>((TileGeneratorFuel) world.getTileEntity(new BlockPos(x, y, z)), player));
            case FURNACE:
                return new GuiContainerOneToOne<>(new ContainerMachine<>((TileFurnace) world.getTileEntity(new BlockPos(x, y, z)), player));
            case REBUILD:
                return new GuiContainerOneToOne<>(new ContainerMachine<>((TileRebuild) world.getTileEntity(new BlockPos(x, y, z)), player));
            case EXTRACTOR:
                return new GuiContainerOneToOne<>(new ContainerMachine<>((TileExtractor) world.getTileEntity(new BlockPos(x, y, z)), player));
            default:
                return null;
        }
    }
}
