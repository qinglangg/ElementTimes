package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.CondenserData;
import com.elementtimes.elementtimes.common.block.lifecycle.CondenserBucketLifecycle;
import com.elementtimes.elementtimes.common.block.lifecycle.CondenserRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "condenser"))
public class TileCondenser extends BaseTileEntity implements INamedContainerProvider {

    public TileCondenser(TileEntityType<TileCondenser> type) {
        super(type, 100000, 4, 2, 16000);
        getEngine().addLifeCycle(new CondenserBucketLifecycle(this));
        getEngine().addLifeCycle(new CondenserRecipeLifecycle(this));
    }
    
    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return CondenserData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileCondenser.class, id, this, CondenserData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "condenser"))
    public static Container condenser(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.condenser.readGuiBuffer(extraData);
        TileCondenser te = (TileCondenser) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileCondenser.class, id, te, CondenserData.INSTANCE, inventory.player);
    }
}
