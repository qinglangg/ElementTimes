package com.elementtimes.tutorial.common.init;

import com.elementtimes.tutorial.annotation.annotations.ModEnchantment;
import com.elementtimes.tutorial.common.chantment.HammerDebug;
import net.minecraft.enchantment.Enchantment;

/**
 * 附魔及药水注册
 * @author luqin2007
 */
public class ElementtimesMagic {

    @ModEnchantment
    public static Enchantment hammerDebugger = new HammerDebug();
}
