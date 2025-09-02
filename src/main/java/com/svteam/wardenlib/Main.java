package com.svteam.wardenlib;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        ScoreboardAPI.loadConfig(getDataFolder());
        getLogger().info("WardenLib loaded with scoreboard.yml support!");
    }
}
