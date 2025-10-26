package com.svteam.wardenlib.ui;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.HashSet;
import java.util.Set;

public class UILoader {
    // Keeps track of plugins that have already registered
    private static final Set<String> registeredPlugins = new HashSet<>();

    public static void registerUI(Plugin plugin) {
        String pluginName = plugin.getName();

        if (registeredPlugins.contains(pluginName)) {
            plugin.getLogger().warning("[WardenLib] UIListener is already registered for plugin '" + pluginName + "'. Skipping duplicate registration.");
            return;
        }

        // Register only once
        new UIListener(plugin, plugin.getConfig());
        registeredPlugins.add(pluginName);
        Bukkit.getLogger().info("[WardenLib] Registered UIListener for " + pluginName);
    }

    public static boolean isRegistered(Plugin plugin) {
        return registeredPlugins.contains(plugin.getName());
    }
}
