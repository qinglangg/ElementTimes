package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.FluidReactorData;
import com.elementtimes.elementtimes.common.block.lifecycle.FluidReactorBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.FluidReactorRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "fluidReactor"))
public class TileFluidReactor extends BaseTileEntity implements INamedContainerProvider {

	public TileFluidReactor(TileEntityType<TileFluidReactor> type) {
		super(type, 100000, 11, 5, 16000);
        getEngine().addLifeCycle(new FluidReactorBucketLifecycle(this));
        getEngine().addLifeCycle(new FluidReactorRecipeLifecycle(this));
	}

	@Override
	@Nonnull
	public ITextComponent getDisplayName() {
		return FluidReactorData.INSTANCE.getDisplayName();
	}

	@Nullable
	@Override
	public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
		return BaseGuiData.createContainer(TileFluidReactor.class, id, this, FluidReactorData.INSTANCE, player);
	}

	@ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "fluidReactor"))
	public static Container fluidReactor(int id, PlayerInventory inventory, PacketBuffer extraData) {
		BlockTileBase.GuiNetworkData data = Industry.fluidReactor.readGuiBuffer(extraData);
		TileFluidReactor te = (TileFluidReactor) inventory.player.world.getTileEntity(data.hit.getPos());
		return BaseGuiData.createContainer(TileFluidReactor.class, id, te, FluidReactorData.INSTANCE, inventory.player);
	}
}
