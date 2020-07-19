package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.PulverizerData;
import com.elementtimes.elementtimes.common.block.lifecycle.PulverizerRecipeLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "pulverizer"))
public class TilePulverizer extends BaseTileEntity implements INamedContainerProvider {

    public TilePulverizer(TileEntityType<TilePulverizer> type) {
        super(type, Config.pulverizerCapacity.get(), 2);
        getEnergyHandler().setCapacity(Config.pulverizerCapacity::get);
        getEnergyHandler().setReceive(Config.pulverizerInput::get);
        getEngine().addLifeCycle(new PulverizerRecipeLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return PulverizerData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TilePulverizer.class, id, this, PulverizerData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "pulverizer"))
    public static Container pulverizer(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.pulverizer.readGuiBuffer(extraData);
        TilePulverizer te = (TilePulverizer) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TilePulverizer.class, id, te, PulverizerData.INSTANCE, inventory.player);
    }
}
