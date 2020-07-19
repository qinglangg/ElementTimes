package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.ElectrolyticCellData;
import com.elementtimes.elementtimes.common.block.lifecycle.ElectrolyticCellBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.ElectrolyticCellRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "electrolyticCell"))
public class TileElectrolyticCell extends BaseTileEntity implements INamedContainerProvider {

    public TileElectrolyticCell(TileEntityType<TileElectrolyticCell> type) {
        super(type, 10000, 6, 3, 16000);
        getEngine().addLifeCycle(new ElectrolyticCellBucketLifecycle(this));
        getEngine().addLifeCycle(new ElectrolyticCellRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return ElectrolyticCellData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileElectrolyticCell.class, id, this, ElectrolyticCellData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "electrolyticCell"))
    public static Container electrolyticCell(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.electrolyticCell.readGuiBuffer(extraData);
        TileElectrolyticCell te = (TileElectrolyticCell) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileElectrolyticCell.class, id, te, ElectrolyticCellData.INSTANCE, inventory.player);
    }
}
