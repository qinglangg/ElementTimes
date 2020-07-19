package com.elementtimes.elementtimes.misc;

import net.minecraftforge.fml.ModList;

/**
 * 反射用

 */
public class MCPNames {

    public static final MCPName WORLD_WEATHER_EFFECTS = new MCPName("weatherEffects", "field_73007_j");

    public static class MCPName {
        public final String mcp;
        public final String name;
        public MCPName(String mcpName, String name) {
            this.mcp = mcpName;
            this.name = name;
        }

        public boolean match(String name) {
            return name != null && (name.equals(mcp) || name.equals(this.name));
        }
    }

    public static boolean isNetease() {
        ModList modList = ModList.get();
        return modList.isLoaded("antimod") && modList.isLoaded("networkmod") && modList.isLoaded("friendplaymod");
    }
}
