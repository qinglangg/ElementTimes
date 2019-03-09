package com.elementtimes.tutorial.inventory;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.tileentity.TileElementGenerater;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * @author KSGFK create in 2019/2/17
 */
public class ContainerElementGenerater extends ContainerMachine<TileElementGenerater> {
    public ContainerElementGenerater(TileEntity tileEntity, EntityPlayer player) {
        super((TileElementGenerater) tileEntity, player);
        if (tileEntity != null) {
            this.addSlotToContainer(((TileElementGenerater) tileEntity).getInputSlot());
        } else Elementtimes.getLogger().error("How can it be!");
    }
}
