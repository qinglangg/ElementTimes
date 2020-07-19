package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.SolidCentrifugeData;
import com.elementtimes.elementtimes.common.block.lifecycle.SolidCentrifugeRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "solidCentrifuge"))
public class TileSolidCentrifuge extends BaseTileEntity implements INamedContainerProvider {

    public TileSolidCentrifuge(TileEntityType<TileSolidCentrifuge> type) {
        super(type, 100000, 4);
        getEngine().addLifeCycle(new SolidCentrifugeRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return SolidCentrifugeData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileSolidCentrifuge.class, id, this, SolidCentrifugeData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "solidCentrifuge"))
    public static Container solidCentrifuge(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.solidCentrifuge.readGuiBuffer(extraData);
        TileSolidCentrifuge te = (TileSolidCentrifuge) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileSolidCentrifuge.class, id, te, SolidCentrifugeData.INSTANCE, inventory.player);
    }
}