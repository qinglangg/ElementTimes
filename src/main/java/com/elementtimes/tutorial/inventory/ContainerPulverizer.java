package com.elementtimes.tutorial.inventory;

import com.elementtimes.tutorial.Elementtimes;
import com.elementtimes.tutorial.common.tileentity.TilePulverizer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;

/**
 * @author KSGFK create in 2019/5/6
 */
public class ContainerPulverizer extends ContainerMachine<TilePulverizer> {

    public ContainerPulverizer(TileEntity tileEntity, EntityPlayer player) {
        super((TilePulverizer) tileEntity, player);
        if (tileEntity != null) {
            this.addSlotToContainer(((TilePulverizer) tileEntity).getInputSlot());
            this.addSlotToContainer(((TilePulverizer) tileEntity).getOutputSlot());
        } else Elementtimes.getLogger().warn("Maybe god know what happen :)");
    }
}
