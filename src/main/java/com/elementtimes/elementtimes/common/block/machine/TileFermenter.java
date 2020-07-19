package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.FermenterData;
import com.elementtimes.elementtimes.common.block.lifecycle.FermenterBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.FermenterRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "fermenter"))
public class TileFermenter extends BaseTileEntity implements INamedContainerProvider {

    public TileFermenter(TileEntityType<TileFermenter> type) {
        super(type, Config.formingCapacity.get(), 11, 4, 16000);
        getEnergyHandler().setReceive(Config.formingInput::get);
        getEnergyHandler().setCapacity(Config.formingCapacity::get);
        getEngine().addLifeCycle(new FermenterBucketLifecycle(this));
        getEngine().addLifeCycle(new FermenterRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return FermenterData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileFermenter.class, id, this, FermenterData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "fermenter"))
    public static Container fermenter(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.fermenter.readGuiBuffer(extraData);
        TileFermenter te = (TileFermenter) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileFermenter.class, id, te, FermenterData.INSTANCE, inventory.player);
    }
}
