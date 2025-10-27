package com.svteam.wardenlib.handlers;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class SoundHandler {

    private final Plugin plugin;

    public SoundHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    public void play(Player player, Sound sound, float volume, float pitch) {
        player.playSound(player.getLocation(), sound, volume, pitch);
    }

    public void playAll(Sound sound, float volume, float pitch) {
        for (Player player : plugin.getServer().getOnlinePlayers()) {
            play(player, sound, volume, pitch);
        }
    }

    public void playAt(Location loc, Sound sound, float volume, float pitch) {
        loc.getWorld().playSound(loc, sound, volume, pitch);
    }
}
