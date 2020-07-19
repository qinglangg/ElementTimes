package com.elementtimes.elementtimes.common.item;

import com.elementtimes.elementtimes.common.init.Groups;
import net.minecraft.item.Food;
import net.minecraft.item.Item;

/**
 * b=2c/a ??!!

 */
public class ModFood extends Item {

    private static Properties prop(int amount, float c, boolean alwaysEat) {
        Food.Builder builder = new Food.Builder().hunger(amount).saturation(amount == 0 ? 0 : 2 * c / amount);
        if (alwaysEat) {
            builder.setAlwaysEdible();
        }
        return new Properties().group(Groups.Agriculture).food(builder.build());
    }

    public ModFood(int amount, float c) {
        super(prop(amount, c, false));
    }

    public ModFood(int amount, float c, boolean alwaysEat) {
        super(prop(amount, c, alwaysEat));
    }
}
