package com.svteam.wardenlib.ui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

/**
 * Handles UI-related player events for WardenLib-based plugins.
 * (Requires UIUtils.java in same package)
 */
public class UIListener implements Listener {

    private final Plugin plugin;
    private final FileConfiguration config;

    public UIListener(Plugin plugin, FileConfiguration config) {
        this.plugin = plugin;
        this.config = config;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        boolean bossbarEnabled = config.getBoolean("ui.bossbar.enabled", true);
        if (bossbarEnabled) {
            String title = config.getString("ui.bossbar.title", "Welcome to the Server!");
            String colorName = config.getString("ui.bossbar.color", "BLUE");
            BarColor color = BarColor.valueOf(colorName.toUpperCase());

            UIUtils.showBossBar(player, title, color, 1.0);
        }

        // Optional title message
        if (config.getBoolean("ui.title.enabled", true)) {
            String main = config.getString("ui.title.main", "Welcome!");
            String sub = config.getString("ui.title.sub", "Enjoy your stay!");
            UIUtils.sendTitle(player, main, sub, NamedTextColor.AQUA);
        }

        // Optional chat welcome message
        if (config.getBoolean("ui.welcome.enabled", true)) {
            player.sendMessage(
                    Component.text(config.getString("ui.welcome.message", "Welcome back!"), NamedTextColor.GREEN)
            );
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();

        // Remove bossbar if active
        UIUtils.hideBossBar(player);
    }
}
