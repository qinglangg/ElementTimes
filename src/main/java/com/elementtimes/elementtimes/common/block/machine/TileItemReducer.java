package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.ItemReducerData;
import com.elementtimes.elementtimes.common.block.lifecycle.ItemReducerRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "itemReducer"))
public class TileItemReducer extends BaseTileEntity implements INamedContainerProvider {

    public TileItemReducer(TileEntityType<TileItemReducer> type) {
        super(type, 100000, 2);
        getEngine().addLifeCycle(new ItemReducerRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return ItemReducerData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileItemReducer.class, id, this, ItemReducerData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "itemReducer"))
    public static Container itemReducer(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.itemReducer.readGuiBuffer(extraData);
        TileItemReducer te = (TileItemReducer) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileItemReducer.class, id, te, ItemReducerData.INSTANCE, inventory.player);
    }
}
