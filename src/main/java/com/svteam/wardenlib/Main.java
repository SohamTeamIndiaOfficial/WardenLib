package com.svteam.wardenlib;

import com.svteam.wardenlib.handlers.EconomyHandler;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onEnable() {
        ScoreboardAPI.loadConfig(getDataFolder());
        getLogger().info("WardenLib loaded with scoreboard.yml support!");
        EconomyHandler.setupEconomy(this);
        getLogger().info("WardenLib enabled. Economy: " + (EconomyHandler.hasEconomy() ? "Available" : "Disabled"));
    }

    public void onDisable() {
        getLogger().info("WardenLib disabled!");
    }

    public void logTransaction(String sender, String receiver, double amount) {
        getLogger().info("[WardenLib] " + sender + " → " + receiver + ": " + amount + " coins");
    }

}
