package com.elementtimes.tutorial.inventory;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.tileentity.TileOneToOne;
import com.elementtimes.tutorial.common.tileentity.TilePulverizer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.NonNullList;

/**
 * 压缩机 GUI
 * 没名字毫无意义 早晚得删的类 在这占位的
 * @author lq2007 create in 2019/5/19
 */
public class ContainerCompressor extends ContainerMachine<TileOneToOne> {

    public ContainerCompressor(TileEntity tileEntity, EntityPlayer player) {
        super((TileOneToOne) tileEntity, player);
        if (tileEntity != null) {
            this.addSlotToContainer(((TileOneToOne) tileEntity).getInputSlot());
            this.addSlotToContainer(((TileOneToOne) tileEntity).getOutputSlot());
        } else Elementtimes.getLogger().warn("Maybe god know what happen :)");
    }


}
