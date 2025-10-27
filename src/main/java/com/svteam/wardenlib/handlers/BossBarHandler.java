package com.svteam.wardenlib.handlers;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.bossbar.BossBar;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class BossBarHandler {

    private static BossBar bossBar;
    private static boolean enabled = false;

    public static void init(String title, String color, float progress) {
        BossBar.Color barColor = switch (color.toUpperCase()) {
            case  "BLUE" -> BossBar.Color.BLUE;
            case  "RED" -> BossBar.Color.RED;
            case  "YELLOW" -> BossBar.Color.YELLOW;
            case  "GREEN" -> BossBar.Color.GREEN;
            case "WHITE" -> BossBar.Color.WHITE;
            default -> BossBar.Color.PURPLE;
        };

        bossBar = BossBar.bossBar(Component.text(title, NamedTextColor.AQUA), progress, barColor,BossBar.Overlay.PROGRESS);
        enabled = true;

        for (Player player : Bukkit.getOnlinePlayers()) {
            player.showBossBar(bossBar);
        }
    }

    public static void remove() {
        if (bossBar != null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.hideBossBar(bossBar);
            }
            bossBar = null;
        }
        enabled = false;
    }

    public static void updateTitle(String newTitle) {
        if (bossBar != null) {
            bossBar.name(Component.text(newTitle, NamedTextColor.AQUA));
        }
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
