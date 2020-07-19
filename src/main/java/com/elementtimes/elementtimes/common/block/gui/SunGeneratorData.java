package com.elementtimes.elementtimes.common.block.gui;

import com.elementtimes.elementcore.api.gui.GuiSize;
import com.elementtimes.elementtimes.ElementTimes;
import com.elementtimes.elementtimes.common.block.machine.TileSunGenerator;
import com.elementtimes.elementtimes.common.init.blocks.Industry;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;



public class SunGeneratorData extends BaseGuiData<TileSunGenerator> {

    public static final SunGeneratorData INSTANCE = new SunGeneratorData();

    public SunGeneratorData() {
        super(Industry.generatorSun);
    }

    @Override
    public ResourceLocation getBackground() {
        return new ResourceLocation(ElementTimes.MODID, "textures/gui/solargenerator.png");
    }

    @Override
    public GuiSize getSize() {
        return Sizes.s176x166x84().withEnergy(43, 46, 0, 166, 90, 4);
    }

    @Override
    public ITextComponent getDisplayName() {
        return Industry.generatorSun.getNameTextComponent();
    }
}
