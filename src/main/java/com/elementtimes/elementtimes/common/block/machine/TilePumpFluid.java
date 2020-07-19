package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.PumpFluidData;
import com.elementtimes.elementtimes.common.block.lifecycle.PumpBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.PumpFluidLifecycle;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "pumpFluid"))
public class TilePumpFluid extends BaseTileEntity implements INamedContainerProvider {

    private final PumpFluidLifecycle mReplacer = new PumpFluidLifecycle(this);

    public TilePumpFluid(TileEntityType<TilePumpFluid> type) {
        super(type, 100000, 2, 1, 16000);
        getEngine().addLifeCycle(mReplacer);
        getEngine().addLifeCycle(new PumpBucketLifecycle(this));
    }

    @Override
    public void read(CompoundNBT nbt) {
        super.read(nbt);
        mReplacer.read();
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return PumpFluidData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TilePumpFluid.class, id, this, PumpFluidData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "pumpFluid"))
    public static Container pumpFluid(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.pumpFluid.readGuiBuffer(extraData);
        TilePumpFluid te = (TilePumpFluid) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TilePumpFluid.class, id, te, PumpFluidData.INSTANCE, inventory.player);
    }
}
