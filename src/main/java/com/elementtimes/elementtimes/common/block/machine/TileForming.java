package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.FormingData;
import com.elementtimes.elementtimes.common.block.lifecycle.FormingRecipeLifecycle;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import com.elementtimes.elementtimes.config.Config;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "forming"))
public class TileForming extends BaseTileEntity implements INamedContainerProvider {

    public TileForming(TileEntityType<TileForming> type) {
        super(type, Config.formingCapacity.get(), 2);
        getEnergyHandler().setCapacity(Config.formingCapacity::get);
        getEnergyHandler().setReceive(Config.formingInput::get);
        getEngine().addLifeCycle(new FormingRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return FormingData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileForming.class, id, this, FormingData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "forming"))
    public static Container forming(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.forming.readGuiBuffer(extraData);
        TileForming te = (TileForming) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileForming.class, id, te, FormingData.INSTANCE, inventory.player);
    }
}
