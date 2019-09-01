package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.tileentity.BaseMachine;
import com.elementtimes.tutorial.inventory.base.ContainerMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

/**
 * GUI初始化
 *
 * @author KSGFK create in 2019/2/17
 */
public class ElementtimesGUI implements IGuiHandler {

    public static ElementtimesGUI GUI;

    public enum Machines {
        /**
         * 所有以 BaseMachine 为 TileEntity 的基类的机器 GUI 枚举
         */
        ElementGenerator("0"),
        FuelGenerator("0"),
        Pulverize("5"),
        Compressor("5"),
        Furnace("5"),
        Rebuild("5"),
        Extractor("5"),
        Forming("5"),
        ItemReducer("5"),
        SolidMelter("solidmelter"),
        FluidReactor("fluidreactor"),
        SolidReactor("solidreactor"),
        Condenser("condenser"),
        FluidHeater("fluidheater"),
        ElectrolyticCell("electrolyticcell"),
        SolidFluidReactor("solidfluidreactor"),
        PumpAir("pump"),
        PumpFluid("pump"),
        Centrifuge("centrifuge"),
        Coagulator("coagulator"),
        SolidCentrifuge("solidcentrifuge"),
        SupportStandAL("supportstand"),
        SupportStandC("supportstand");

        Machines(String texture) {
            this.texture = texture;
        }

        private String texture;

        public int id() {
            return ordinal();
        }
        public String texture() {
            return texture;
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
                case ItemReducer:
                    return ContainerMachine.cm176_156_74(machine, player);
                case SolidMelter:
                case SolidCentrifuge:
                    return ContainerMachine.cm176_166_84(machine, player);
                case Condenser:
                case FluidReactor:
                case SolidReactor:
                case SolidFluidReactor:
                case FluidHeater:
                case ElectrolyticCell:
                case PumpAir:
                case PumpFluid:
                case Centrifuge:
                    return ContainerMachine.cm176_204_122(machine, player);
                case SupportStandAL:
                case SupportStandC:
                    return ContainerMachine.cm176_201_119(machine, player);
                case Coagulator:
                    return ContainerMachine.cm176_179_97(machine, player);
                default:
                    return null;
            }
        }
		return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        if (FMLCommonHandler.instance().getSide() == Side.CLIENT) {
            TileEntity tileEntity = world.getTileEntity(new BlockPos(x, y, z));
            if (tileEntity instanceof BaseMachine) {
                BaseMachine machine = (BaseMachine) tileEntity;
                final Machines type = machine.getGuiType();
                switch (type) {
                    case ElementGenerator:
                    case FuelGenerator:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_156_74(machine, player), type.texture, 60,
                                new int[0][], new int[][] {new int[] {43, 55, 0, 156, 89, 4}});
                    case Pulverize:
                    case Compressor:
                    case Furnace:
                    case Rebuild:
                    case Extractor:
                    case Forming:
                    case ItemReducer:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_156_74(machine, player), type.texture, 60,
                                80, 30, 0, 156, 24, 17,
                                43, 55, 24, 156, 90, 4);
                    case Centrifuge:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), type.texture, 112,
                                40, 30, 43, 107);
                    case SolidMelter:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_166_84(machine, player), type.texture, 4,
                                65, 31, 43, 72);
                    case Condenser:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), type.texture, 115,
                                new int[][] {new int[] {54, 18, 0, 204, 70, 20}, new int[] {55, 52, 0, 224, 64, 15}},
                                45, 89, 0, 239, 90, 4);
                    case FluidReactor:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), type.texture, 100,
                                58, 30, 43, 108);
                    case SolidReactor:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), type.texture, 85,
                                75, 42, 43, 108);
                    case SolidFluidReactor:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), type.texture, 14,
                                65, 30, 43, 108);
                    case FluidHeater:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), type.texture, 117,
                                new int[][] {new int[] {53, 18, 0, 204, 70, 19}, new int[] {57, 52, 0, 223, 62, 15}},
                                45, 89, 0, 237, 90, 4);
                    case ElectrolyticCell:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), type.texture, 108,
                                new int[][] {new int[] {58, 20, 0, 204, 24, 17}, new int[] {63, 35, 0, 221, 16, 16}},
                                43, 107, 24, 204, 90, 4);
                    case PumpAir:
                    case PumpFluid:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_204_122(machine, player), type.texture, 85,
                                new int[0][], 43, 108, 24, 204, 90, 4);
                    case SupportStandAL:
                    case SupportStandC:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_201_119(machine, player), type.texture, 105,
                                80, 26, 0, 201, 14, 14, new int[0][]);
                    case Coagulator:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_179_97(machine, player), type.texture, 85,
                                81, 36, 0, 196, 16, 16, 43,86, 24, 179, 90 ,4);
                    case SolidCentrifuge:
                        return new com.elementtimes.tutorial.client.gui.base.GuiContainerElectrical(ContainerMachine.cm176_166_84(machine, player), type.texture, 72,
                                80, 31, 0, 183, 16, 16, 43, 73, 24, 166, 90, 4);
                    default:
                        return null;
                }
            } else {
                return null;
            }
        } else {
            return getServerGuiElement(id, player, world, x, y, z);
        }
    }
}
