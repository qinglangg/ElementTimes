package com.elementtimes.tutorial.other;

import net.minecraftforge.fml.common.Loader;

/**
 * 反射用
 * @author luqin2007
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
        return Loader.isModLoaded("antimod") && Loader.isModLoaded("networkmod") && Loader.isModLoaded("friendplaymod");
    }
}
