package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.FurnaceData;
import com.elementtimes.elementtimes.common.block.lifecycle.FurnaceRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "furnace"))
public class TileFurnace extends BaseTileEntity implements INamedContainerProvider {

    public TileFurnace(TileEntityType<TileFurnace> type) {
        super(type, Config.furnaceCapacity.get(), 2);
        getEnergyHandler().setReceive(Config.furnaceInput::get);
        getEnergyHandler().setCapacity(Config.furnaceCapacity::get);
        getEngine().addLifeCycle(new FurnaceRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return FurnaceData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileFurnace.class, id, this, FurnaceData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "furnace"))
    public static Container furnace(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.furnace.readGuiBuffer(extraData);
        TileFurnace te = (TileFurnace) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileFurnace.class, id, te, FurnaceData.INSTANCE, inventory.player);
    }
}

