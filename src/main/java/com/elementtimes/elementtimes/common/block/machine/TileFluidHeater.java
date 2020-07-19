package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.FluidHeaterData;
import com.elementtimes.elementtimes.common.block.lifecycle.FluidHeaterBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.FluidHeaterRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "fluidHeater"))
public class TileFluidHeater extends BaseTileEntity implements INamedContainerProvider {

    public TileFluidHeater(TileEntityType<TileFluidHeater> type) {
        super(type, 100000, 4, 2, 16000);
        getEngine().addLifeCycle(new FluidHeaterBucketLifecycle(this));
        getEngine().addLifeCycle(new FluidHeaterRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return FluidHeaterData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileFluidHeater.class, id, this, FluidHeaterData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "fluidHeater"))
    public static Container fluidHeater(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.fluidHeater.readGuiBuffer(extraData);
        TileFluidHeater te = (TileFluidHeater) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileFluidHeater.class, id, te, FluidHeaterData.INSTANCE, inventory.player);
    }
}
