package com.elementtimes.elementtimes.common.potion;

import com.elementtimes.elementtimes.common.fluid.Sources;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;



public class SaltEffect extends Effect {

    public SaltEffect() {
        super(EffectType.NEUTRAL, Sources.seaWater.getAttributes().getColor());
    }
}
