package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.CentrifugeData;
import com.elementtimes.elementtimes.common.block.gui.CoagulatorData;
import com.elementtimes.elementtimes.common.block.lifecycle.CoagulatorBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.CoagulatorRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "coagulator"))
public class TileCoagulator extends BaseTileEntity implements INamedContainerProvider {

    public TileCoagulator(TileEntityType<TileCoagulator> type) {
        super(type, 100000, 3, 1, 16000);
        getEngine().addLifeCycle(new CoagulatorBucketLifecycle(this));
        getEngine().addLifeCycle(new CoagulatorRecipeLifecycle(this));
    }

    @Override
    public ITextComponent getDisplayName() {
        return CentrifugeData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileCoagulator.class, id, this, CoagulatorData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "coagulator"))
    public static Container coagulator(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.coagulator.readGuiBuffer(extraData);
        TileCoagulator te = (TileCoagulator) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileCoagulator.class, id, te, CoagulatorData.INSTANCE, inventory.player);
    }
}
