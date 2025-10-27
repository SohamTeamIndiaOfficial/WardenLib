package com.svteam.wardenlib.handlers;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ActionBarHandler {

    private final Plugin plugin;

    public ActionBarHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    public void send(Player player, Component message) {
        player.sendActionBar(message);
    }

    public void broadcast(Component message) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            p.sendActionBar(message);
        }
    }

    public void sendTimed(Player player, Component message, int durationTicks) {
        send(player, message);
        Bukkit.getScheduler().runTaskLater(plugin, () -> player.sendActionBar(Component.empty()), durationTicks);
    }
}
