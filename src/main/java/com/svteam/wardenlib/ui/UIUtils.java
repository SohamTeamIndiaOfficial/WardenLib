package com.svteam.wardenlib.ui;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public final class UIUtils {

    private static final Map<Player, BossBar> ACTIVE_BOSSBARS = new ConcurrentHashMap<>();

    private UIUtils() {}

    // ==========================
    // 📢 Chat Utilities
    // ==========================

    public static void send(Player player, String message, NamedTextColor color) {
        player.sendMessage(Component.text(message, color));
    }

    public static void sendClickable(Player player, String text, String hover, String command, NamedTextColor color) {
        Component clickable = Component.text(text, color)
                .hoverEvent(HoverEvent.showText(Component.text(hover, NamedTextColor.GRAY)))
                .clickEvent(ClickEvent.runCommand(command));
        player.sendMessage(clickable);
    }

    public static void broadcast(String prefix, String message, NamedTextColor prefixColor, NamedTextColor msgColor) {
        Component formatted = Component.text(prefix + " ", prefixColor, TextDecoration.BOLD)
                .append(Component.text(message, msgColor));
        Bukkit.broadcast(formatted);
    }

    // ==========================
    // 🎬 Title Utilities
    // ==========================

    public static void sendTitle(Player player, String title, String subtitle, NamedTextColor color) {
        player.showTitle(Title.title(
                Component.text(title, color, TextDecoration.BOLD),
                Component.text(subtitle, NamedTextColor.GRAY),
                Title.Times.times(
                        Duration.ofMillis(500),
                        Duration.ofSeconds(3),
                        Duration.ofMillis(500)
                )
        ));
    }

    public static void sendActionBar(Player player, String message, NamedTextColor color) {
        player.sendActionBar(Component.text(message, color));
    }

    // ==========================
    // 💎 Inventory Utilities
    // ==========================

    public static Inventory createGUI(String title, int size) {
        return Bukkit.createInventory(null, size, Component.text(title, NamedTextColor.DARK_AQUA));
    }

    public static ItemStack createItem(Material material, String name, NamedTextColor color, List<String> lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();

        meta.displayName(Component.text(name, color).decoration(TextDecoration.BOLD, true));

        if (lore != null && !lore.isEmpty()) {
            meta.lore(lore.stream()
                    .map(line -> Component.text(line, NamedTextColor.GRAY))
                    .toList());
        }

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES, ItemFlag.HIDE_ENCHANTS);
        item.setItemMeta(meta);
        return item;
    }

    // ==========================
    // ⚡ BossBar Utilities
    // ==========================

    public static void showBossBar(Player player, String text, BarColor color, double progress) {
        BossBar bar = ACTIVE_BOSSBARS.get(player);

        if (bar == null) {
            bar = Bukkit.createBossBar(text, color, BarStyle.SEGMENTED_10);
            bar.addPlayer(player);
            ACTIVE_BOSSBARS.put(player, bar);
        }

        bar.setTitle(text); // Replaces bar.name(...)
        bar.setProgress(Math.max(0, Math.min(1, progress)));
        bar.setColor(color);
    }

    public static void hideBossBar(Player player) {
        BossBar bar = ACTIVE_BOSSBARS.remove(player);
        if (bar != null) {
            bar.removeAll();
        }
    }

    public static void showTemporaryBossBar(Player player, String text, BarColor color, double progress, int seconds) {
        showBossBar(player, text, color, progress);
        Bukkit.getScheduler().runTaskLater(
                Bukkit.getPluginManager().getPlugin("WardenLib"),
                () -> hideBossBar(player),
                seconds * 20L
        );
    }

    // ==========================
    // ⚙️ Utility Helpers
    // ==========================

    public static Component separator() {
        return Component.text("────────────────────────────", NamedTextColor.DARK_GRAY);
    }

    public static void debug(Player player, String msg) {
        send(player, "[DEBUG] " + msg, NamedTextColor.DARK_GRAY);
    }
}
