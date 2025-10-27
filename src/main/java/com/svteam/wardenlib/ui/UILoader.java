package com.svteam.wardenlib.ui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

/**
 * @deprecated Replaced by {@link UIStarter}.
 */
@Deprecated(since = "1.4")
public class UILoader {

    /**
     * @deprecated Use {@link UIStarter#register(Plugin)} instead.
     */
    @Deprecated(forRemoval = true, since = "1.4")
    public static void registerUI(Plugin plugin) {
        Bukkit.getLogger().warning("[WardenLib] ⚠ UILoader is deprecated! Use UIStarter.register(plugin) instead.");
        UIStarter.register(plugin);
    }

    /**
     * @deprecated Use {@link UIStarter#isRegistered(Plugin)} instead.
     */
    @Deprecated(forRemoval = true, since = "1.4")
    public static boolean isRegistered(Plugin plugin) {
        return UIStarter.isRegistered(plugin);
    }
}
