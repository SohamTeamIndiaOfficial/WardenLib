package com.svteam.wardenlib;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class ScoreboardAPI {

    private static final Map<UUID, Scoreboard> activeBoards = new HashMap<>();
    private static FileConfiguration config;

    public static void loadConfig(File dataFolder) {
        File file = new File(dataFolder, "scoreboard.yml");
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
                Bukkit.getLogger().info("[WardenLib] Created scoreboard.yml");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);

        // Defaults if file is empty
        if (!config.contains("title")) {
            config.set("enabled", true);
            config.set("title", "&b&lWardenEssentials");
            config.set("lines", Arrays.asList(
                    "&7&m------------------",
                    "&ePlayer: &f%player%",
                    "&eHealth: &c%health%",
                    "&eWorld: &a%world%",
                    "&7&m------------------"
            ));
            try {
                config.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void enableScoreboard(Player player) {
        if (!config.getBoolean("enabled", true)) return;

        ScoreboardManager manager = Bukkit.getScoreboardManager();
        if (manager == null) return;

        Scoreboard board = manager.getNewScoreboard();
        String title = ChatColor.translateAlternateColorCodes('&', config.getString("title", "&aScoreboard"));
        Objective obj = board.registerNewObjective("weboard", "dummy", title);
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);

        List<String> lines = config.getStringList("lines");
        int score = lines.size();
        for (String line : lines) {
            String parsed = ChatColor.translateAlternateColorCodes('&', replacePlaceholders(player, line));
            obj.getScore(parsed).setScore(score--);
        }

        player.setScoreboard(board);
        activeBoards.put(player.getUniqueId(), board);
    }

    public static void disableScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        activeBoards.remove(player.getUniqueId());
    }

    public static boolean hasScoreboard(Player player) {
        return activeBoards.containsKey(player.getUniqueId());
    }

    private static String replacePlaceholders(Player player, String text) {
        return text.replace("%player%", player.getName())
                .replace("%health%", String.valueOf((int) player.getHealth()))
                .replace("%world%", player.getWorld().getName());
    }
}
