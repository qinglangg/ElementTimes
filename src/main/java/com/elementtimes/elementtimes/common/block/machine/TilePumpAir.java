package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.PumpAirData;
import com.elementtimes.elementtimes.common.block.lifecycle.PumpAirLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.PumpBucketLifecycle;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "pumpAir"))
public class TilePumpAir extends BaseTileEntity implements INamedContainerProvider {

    public TilePumpAir(TileEntityType<TilePumpAir> type) {
        super(type, 0, 2, 1, 16000);
        getEngine().addLifeCycle(new PumpBucketLifecycle(this));
        getEngine().addLifeCycle(new PumpAirLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return PumpAirData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TilePumpAir.class, id, this, PumpAirData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "pumpAir"))
    public static Container pumpAir(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.pumpAir.readGuiBuffer(extraData);
        TilePumpAir te = (TilePumpAir) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TilePumpAir.class, id, te, PumpAirData.INSTANCE, inventory.player);
    }
}
