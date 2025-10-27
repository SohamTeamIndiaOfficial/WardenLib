package com.svteam.wardenlib.handlers;

import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AnimationHandler {

    private final Plugin plugin;

    public AnimationHandler(Plugin plugin) {
        this.plugin = plugin;
    }

    /**
     * Cycles through messages as an animation in ActionBar or Title.
     */
    public void playActionBarAnimation(Player player, List<Component> frames, int intervalTicks) {
        AtomicInteger index = new AtomicInteger(0);

        int taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            if (!player.isOnline()) {
                Bukkit.getScheduler().cancelTask(index.get());
                return;
            }

            Component frame = frames.get(index.getAndIncrement() % frames.size());
            player.sendActionBar(frame);
        }, 0L, intervalTicks);
    }

    public void playBroadcastAnimation(List<Component> frames, int intervalTicks) {
        AtomicInteger index = new AtomicInteger(0);

        Bukkit.getScheduler().runTaskTimer(plugin, () -> {
            Component frame = frames.get(index.getAndIncrement() % frames.size());
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendActionBar(frame);
            }
        }, 0L, intervalTicks);
    }
}
