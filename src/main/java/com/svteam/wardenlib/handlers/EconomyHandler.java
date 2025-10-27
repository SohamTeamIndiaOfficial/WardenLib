package com.svteam.wardenlib.handlers;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class EconomyHandler {
    private static Economy econ = null;

    public static void setupEconomy(JavaPlugin plugin) {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            plugin.getLogger().warning("Vault not found, disabling economy features.");
            return;
        }

        RegisteredServiceProvider<Economy> rsp = plugin.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            plugin.getLogger().warning("No economy provider found!");
            return;
        }

        econ = rsp.getProvider();
        plugin.getLogger().info("Hooked into economy: " + econ.getName());
    }

    public static boolean hasEconomy() {
        return econ != null;
    }

    public static double getBalance(OfflinePlayer player) {
        return hasEconomy() ? econ.getBalance(player) : 0.0;
    }

    public static boolean withdraw(OfflinePlayer player, double amount) {
        if (!hasEconomy()) return false;
        if (econ.getBalance(player) < amount) return false;
        econ.withdrawPlayer(player, amount);
        return true;
    }

    public static void deposit(OfflinePlayer player, double amount) {
        if (hasEconomy()) econ.depositPlayer(player, amount);
    }
}
