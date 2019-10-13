package com.elementtimes.tutorial.common.item;

import net.minecraft.item.ItemFood;

/**
 * b=2c/a ??!!
 * @author luqin2007
 */
public class ModFood extends ItemFood {
    public ModFood(int amount, float c) {
        super(amount, 2 * c / amount, false);
    }
}
