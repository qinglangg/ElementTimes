package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.client.gui.GuiContainerEGEN;
import com.elementtimes.tutorial.inventory.ContainerElementGenerater;
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
    private GuiContainerEGEN generater;

    public void init() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Elementtimes.instance, this);
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                return new ContainerElementGenerater(world.getTileEntity(new BlockPos(x, y, z)), player);
            default:
                return null;
        }
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        switch (ID) {
            case 0:
                return generater = new GuiContainerEGEN(new ContainerElementGenerater(world.getTileEntity(new BlockPos(x, y, z)), player));
            default:
                return null;
        }
    }

    public GuiContainerEGEN getGenerater() {
        return generater;
    }
}
