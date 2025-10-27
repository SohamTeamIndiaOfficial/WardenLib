package com.svteam.wardenlib.ui;

import org.bukkit.plugin.Plugin;
import org.bukkit.Bukkit;

public class UIStarter {

    private static final java.util.Set<Plugin> registered = new java.util.HashSet<>();

    public static void register(Plugin plugin) {
        if (registered.contains(plugin)) {
            Bukkit.getLogger().info("[WardenLib] UI already registered for " + plugin.getName());
            return;
        }
        registered.add(plugin);
        Bukkit.getLogger().info("[WardenLib] UI registered for " + plugin.getName());
    }

    public static boolean isRegistered(Plugin plugin) {
        return registered.contains(plugin);
    }
}
