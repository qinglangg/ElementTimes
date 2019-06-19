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

    public enum Machines {
        ElementGenerator, Pulverize, Compressor, FuelGenerator,
        Furnace, Rebuild, Extractor, Forming, SolidMelter,
        FluidReactor, SolidReactor, Condenser,
        FluidHeater, ElectrolyticCell, SolidFluidReactor;

        public int id() {
            return ordinal();
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
            machine.getOpenedPlayers().add((EntityPlayerMP) player);
            switch (type) {
                case ElementGenerator:
                case Pulverize:
                case Forming:
                case Extractor:
                case Rebuild:
                case Furnace:
                case FuelGenerator:
                case Compressor:
                    return ContainerMachine.cm176_156_74(machine, player);
                case SolidMelter:
                    return ContainerMachine.cm176_166_84(machine, player);
                case Condenser:
                case FluidReactor:
                case SolidReactor:
                case SolidFluidReactor:
                case FluidHeater:
                case ElectrolyticCell:
                    return ContainerMachine.cm176_204_122(machine, player);
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
                    return new GuiContainerGenerator(ContainerMachine.cm176_156_74(machine, player));
                case Pulverize:
                case Compressor:
                case Furnace:
                case Rebuild:
                case Extractor:
                case Forming:
                    return new GuiContainerElectrical(ContainerMachine.cm176_156_74(machine, player), "5", 60,
                    		80, 30, 0, 156, 24, 17,
                    	    43, 55, 24, 156, 90, 4);
                case SolidMelter:
                    return new GuiContainerElectrical(ContainerMachine.cm176_166_84(machine, player), "solidmelter", 4,
                            65, 31, 43, 72);
                case Condenser:
                    return new GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), "condenser", 115,
                            new int[][] {new int[] {54, 18, 0, 204, 70, 20}, new int[] {55, 52, 0, 224, 64, 15}},
                            45, 89, 0, 239, 90, 4);
                case FluidReactor:
                    return new GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), "fluidreactor", 100,
                            58, 30, 43, 108);
                case SolidReactor:
                    return new GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), "solidreactor", 85,
                            75, 42, 43, 108);
                case SolidFluidReactor:
                    return new GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), "solidfluidreactor", 14,
                            65, 30, 43, 108);
                case FluidHeater:
                    return new GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), "fluidheater", 117,
                            new int[][] {new int[] {53, 18, 0, 204, 70, 19}, new int[] {57, 52, 0, 223, 62, 15}},
                            45, 89, 0, 237, 90, 4);
                case ElectrolyticCell:
                    return new GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), "electrolyticcell", 108,
                            new int[][] {new int[] {58, 20, 0, 204, 24, 17}, new int[] {63, 35, 0, 221, 16, 16}},
                            43, 107, 24, 204, 90, 4);
                default:
                    return null;
            }
        } else {
            return null;
        }
    }
}
