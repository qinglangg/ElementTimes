package com.elementtimes.elementtimes.common.init;

import com.elementtimes.elementcore.api.annotation.ModPotion;
import com.elementtimes.elementtimes.common.potion.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Potion;

/**
 * 药水与附魔

 */
public class Magic {

    @ModPotion.Effect
    public static Effect saltedFishEffect = new SaltedFishEffect();

    @ModPotion.Effect
    public static Effect saltEffect = new SaltEffect();

    @ModPotion
    public static Potion salt = new Salt();

    @ModPotion
    public static Potion saltedFish = new SaltedFish();
}
