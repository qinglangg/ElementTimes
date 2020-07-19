package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.RebuildData;
import com.elementtimes.elementtimes.common.block.lifecycle.RebuildRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "rebuild"))
public class TileRebuild extends BaseTileEntity implements INamedContainerProvider {

    public TileRebuild(TileEntityType<TileRebuild> type) {
        super(type, Config.rebuildCapacity.get(), 2);
        getEnergyHandler().setCapacity(Config.rebuildCapacity::get);
        getEnergyHandler().setReceive(Config.rebuildInput::get);
        getEngine().addLifeCycle(new RebuildRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return RebuildData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileRebuild.class, id, this, RebuildData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "rebuild"))
    public static Container rebuild(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.rebuild.readGuiBuffer(extraData);
        TileRebuild te = (TileRebuild) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileRebuild.class, id, te, RebuildData.INSTANCE, inventory.player);
    }
}
