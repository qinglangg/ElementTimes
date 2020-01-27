package com.elementtimes.tutorial.common.init;

import com.elementtimes.elementcore.api.annotation.old.ModPotion;
import com.elementtimes.tutorial.common.potion.*;
import net.minecraft.potion.Potion;

/**
 * 药水与附魔
 * @author  luqin2007
 */
public class ElementtimesMagic {

    @ModPotion
    public static Potion salt = new Salt();

    @ModPotion
    public static Potion saltedFish = new SaltedFish();
}
