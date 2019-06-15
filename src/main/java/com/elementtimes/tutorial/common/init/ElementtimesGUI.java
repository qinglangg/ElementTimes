package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical;
import com.elementtimes.tutorial.client.gui.base.GuiContainerGenerator;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * GUI初始化
 *
 * @author KSGFK create in 2019/2/17
 */
public class ElementtimesGUI implements IGuiHandler {

    public static ElementtimesGUI GUI;

    public static Map<Machines, Set<EntityPlayerMP>> GUI_DISPLAYED = new HashMap<>();

    public enum Machines {
        // 已完成
        ElementGenerator, Pulverize, Compressor, FuelGenerator,
        Furnace, Rebuild, Extractor, Forming,
        // 未完成
        SolidMelter, SolidReactor, FluidReactor, SolidFluidReactor,
        FluidHeater, ElectrolyticCell;

        public int id() {
            return ordinal();
        }

        public static Machines fromId(int id) {
            return Machines.values()[id];
        }
    }

    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(ElementTimes.instance, this);
        GUI = this;
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity instanceof BaseMachine) {
            BaseMachine machine = (BaseMachine) tileEntity;
            Machines type = machine.getGuiType();
            if (!GUI_DISPLAYED.containsKey(type)) {
                GUI_DISPLAYED.put(type, new HashSet<>());
            }
            GUI_DISPLAYED.get(type).add((EntityPlayerMP) player);
            System.out.println("save " + player + " in " + type.name());
            switch (type) {
                case ElementGenerator:
                case Pulverize:
                case SolidMelter:
                case Forming:
                case Extractor:
                case Rebuild:
                case Furnace:
                case FuelGenerator:
                case Compressor:
                    return new ContainerMachine<>(machine, player);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
        if (tileEntity instanceof BaseMachine) {
            BaseMachine machine = (BaseMachine) tileEntity;
            switch (machine.getGuiType()) {
                case ElementGenerator:
                case FuelGenerator:
                    return new GuiContainerGenerator(new ContainerMachine<>(machine, player));
                case Pulverize:
                case Compressor:
                case Furnace:
                case Rebuild:
                case Extractor:
                case Forming:
                    return new GuiContainerElectrical(new ContainerMachine<>(machine, player));
                case SolidMelter:
                    return new GuiContainerElectrical(new ContainerMachine<>(machine, player), "textures/gui/solidmelter.png",
                            65, 31, 0, 166, 24, 17, 43, 72, 24, 166, 90, 4);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }
}
