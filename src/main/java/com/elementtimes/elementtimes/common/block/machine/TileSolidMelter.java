package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.SolidMelterData;
import com.elementtimes.elementtimes.common.block.lifecycle.SolidMelterBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.SolidMelterRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "solidMelter"))
public class TileSolidMelter extends BaseTileEntity implements INamedContainerProvider {

    public TileSolidMelter(TileEntityType<TileSolidMelter> type) {
        super(type, 100000, 1, 1, 16000);
        getEngine().addLifeCycle(new SolidMelterBucketLifecycle(this));
        getEngine().addLifeCycle(new SolidMelterRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return SolidMelterData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileSolidMelter.class, id, this, SolidMelterData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "solidMelter"))
    public static Container solidMelter(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.solidMelter.readGuiBuffer(extraData);
        TileSolidMelter te = (TileSolidMelter) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileSolidMelter.class, id, te, SolidMelterData.INSTANCE, inventory.player);
    }
}
