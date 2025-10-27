package com.svteam.wardenlib.handlers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class ConfigHandler {

    private final Plugin plugin;
    private final File configFile;
    private FileConfiguration config;

    public ConfigHandler(Plugin plugin, String name) {
        this.plugin = plugin;
        this.configFile = new File(plugin.getDataFolder(), name);
        reload();
    }

    public void reload() {
        if (!configFile.exists()) {
            plugin.saveResource(configFile.getName(), false);
        }
        config = YamlConfiguration.loadConfiguration(configFile);
    }

    public FileConfiguration get() {
        return config;
    }

    public void save() {
        try {
            config.save(configFile);
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save config: " + configFile.getName());
            e.printStackTrace();
        }
    }

    public void saveDefault() {
        if (!configFile.exists()) {
            plugin.saveResource(configFile.getName(), false);
        }
    }
}
