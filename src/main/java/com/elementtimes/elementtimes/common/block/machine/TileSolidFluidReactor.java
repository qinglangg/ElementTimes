package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.SolidFluidReactorData;
import com.elementtimes.elementtimes.common.block.lifecycle.SolidFluidReactorBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.SolidFluidReactorRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "solidFluidReactor"))
public class TileSolidFluidReactor extends BaseTileEntity implements INamedContainerProvider {

    public TileSolidFluidReactor(TileEntityType<TileSolidFluidReactor> type) {
        super(type, 100000, 9, 3, 16000);
        getEngine().addLifeCycle(new SolidFluidReactorBucketLifecycle(this));
        getEngine().addLifeCycle(new SolidFluidReactorRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return SolidFluidReactorData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileSolidFluidReactor.class, id, this, SolidFluidReactorData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "solidFluidReactor"))
    public static Container solidFluidReactor(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.solidFluidReactor.readGuiBuffer(extraData);
        TileSolidFluidReactor te = (TileSolidFluidReactor) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileSolidFluidReactor.class, id, te, SolidFluidReactorData.INSTANCE, inventory.player);
    }
}
