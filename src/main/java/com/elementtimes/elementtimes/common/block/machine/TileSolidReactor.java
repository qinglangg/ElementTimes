package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.SolidReactorData;
import com.elementtimes.elementtimes.common.block.lifecycle.SolidReactorBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.SolidReactorRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "solidReactor"))
public class TileSolidReactor extends BaseTileEntity implements INamedContainerProvider {

    public TileSolidReactor(TileEntityType<TileSolidReactor> type) {
        super(type, 100000, 6, 1, 16000);
        getEngine().addLifeCycle(new SolidReactorBucketLifecycle(this));
        getEngine().addLifeCycle(new SolidReactorRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return SolidReactorData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileSolidReactor.class, id, this, SolidReactorData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "solidReactor"))
    public static Container solidReactor(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.solidReactor.readGuiBuffer(extraData);
        TileSolidReactor te = (TileSolidReactor) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileSolidReactor.class, id, te, SolidReactorData.INSTANCE, inventory.player);
    }
}
