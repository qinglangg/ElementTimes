package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.CentrifugeData;
import com.elementtimes.elementtimes.common.block.lifecycle.CentrifugeBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.CentrifugeRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "centrifuge"))
public class TileCentrifuge extends BaseTileEntity implements INamedContainerProvider {

    public TileCentrifuge(TileEntityType<TileCentrifuge> type) {
        super(type, 10000, 12, 6, 16000);
        getEngine().addLifeCycle(new CentrifugeRecipeLifecycle(this));
        getEngine().addLifeCycle(new CentrifugeBucketLifecycle(this));
    }

    @Override
    public ITextComponent getDisplayName() {
        return CentrifugeData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileCentrifuge.class, id, this, CentrifugeData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "centrifuge"))
    public static Container centrifuge(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.centrifuge.readGuiBuffer(extraData);
        TileCentrifuge te = (TileCentrifuge) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileCentrifuge.class, id, te, CentrifugeData.INSTANCE, inventory.player);
    }
}
