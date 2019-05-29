package com.elementtimes.tutorial.interface_.tileentity;

import net.minecraft.client.gui.GuiButton;

public interface IButtonProvider {
    default GuiButton[] getButtons() {
        return new GuiButton[0];
    }
}
