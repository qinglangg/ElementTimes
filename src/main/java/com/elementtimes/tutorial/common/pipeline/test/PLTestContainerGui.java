package com.elementtimes.tutorial.common.pipeline.test;

import com.elementtimes.tutorial.ElementTimes;
import com.elementtimes.tutorial.common.pipeline.BaseElement;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.List;

@SideOnly(Side.CLIENT)
public class PLTestContainerGui extends GuiContainer {

    public PLTestContainerGui(PLTestContainer container) {
        super(container);
        this.width = 156;
        this.height = 175;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        Minecraft mc = Minecraft.getMinecraft();
        mc.getTextureManager().bindTexture(new ResourceLocation(ElementTimes.MODID, "textures/gui/empty.png"));
        drawTexturedModalRect(0, 0, 0, 0, width, height);
        BlockPos pos = ((PLTestContainer) inventorySlots).pipeline.getPos();
        List<ImmutablePair<PLTestNetwork.TestElementType, BaseElement>> list = PLTestNetwork.data.get(pos);
        int offsetY = 5;
        if (list != null && !list.isEmpty()) {
            int display = 0;
            for (int i = list.size() - 1; i >= 0; i--) {
                ImmutablePair<PLTestNetwork.TestElementType, BaseElement> data = list.get(i);
                mc.fontRenderer.drawString(data.getLeft().toString(data.getRight()), 5, offsetY, 0xFF000000);
                display++;
                if (display >= 15) {
                    break;
                }
            }
        }
    }
}
