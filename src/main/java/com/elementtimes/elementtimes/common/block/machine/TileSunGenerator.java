package com.elementtimes.elementtimes.common.block.machine;

import com.elementtimes.elementcore.api.annotation.ModContainer;
import com.elementtimes.elementcore.api.annotation.ModTileEntity;
import com.elementtimes.elementcore.api.annotation.part.Getter;
import com.elementtimes.elementcore.api.annotation.part.Method2;
import com.elementtimes.elementcore.api.block.BlockTileBase;
import com.elementtimes.elementcore.api.lifecycle.GeneratorLifecycle;
import com.elementtimes.elementcore.api.lifecycle.Strategies;
import com.elementtimes.elementcore.api.tileentity.BaseTileEntity;
import com.elementtimes.elementtimes.common.block.gui.BaseGuiData;
import com.elementtimes.elementtimes.common.block.gui.SunGeneratorData;
import com.elementtimes.elementtimes.common.block.lifecycle.SunGeneratorLifecycle;
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



@ModTileEntity(blocks = @Getter(value = Industry.class, name = "generatorSun"))
public class TileSunGenerator extends BaseTileEntity implements INamedContainerProvider {

    public TileSunGenerator(TileEntityType<TileSunGenerator> type) {
        super(type, 0, 0);
        getEnergyHandler().setExtract(() -> Config.genSunOutput.get());
        getEnergyHandler().setCapacity(() -> Config.genSunCapacity.get());
        getEngine().addLifeCycle(new GeneratorLifecycle<>(this, Strategies.AVERAGE));
        getEngine().addLifeCycle(new SunGeneratorLifecycle(this));
    }

    @Override
    @Nonnull
    public ITextComponent getDisplayName() {
        return SunGeneratorData.INSTANCE.getDisplayName();
    }

    @Nullable
    @Override
    public Container createMenu(int id, @Nonnull PlayerInventory inventory, @Nonnull PlayerEntity player) {
        return BaseGuiData.createContainer(TileSunGenerator.class, id, this, SunGeneratorData.INSTANCE, player);
    }

    @ModContainer(screen = @Method2(value = "com.elementtimes.elementtimes.client.block.Screens", name = "generatorSun"))
    public static Container generatorSun(int id, PlayerInventory inventory, PacketBuffer extraData) {
        BlockTileBase.GuiNetworkData data = Industry.generatorSun.readGuiBuffer(extraData);
        TileSunGenerator te = (TileSunGenerator) inventory.player.world.getTileEntity(data.hit.getPos());
        return BaseGuiData.createContainer(TileSunGenerator.class, id, te, SunGeneratorData.INSTANCE, inventory.player);
    }
}
