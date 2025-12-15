package com.svteam.wardenlib.handlers;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.text.DecimalFormat;
import java.lang.management.ManagementFactory;

/**
 * Performance utilities for TPS, memory, CPU, uptime.
 */
public final class PerformanceHandler {

    private static final DecimalFormat DF = new DecimalFormat("0.00");

    private PerformanceHandler() {
        // Utility class
    }

    /* ================= TPS ================= */

    /**
     * @return TPS array (1m, 5m, 15m) or null if not Paper
     */
    public static double[] getTPS() {
        try {
            return Bukkit.getTPS(); // Paper only
        } catch (NoSuchMethodError e) {
            return null;
        }
    }

    public static String getTPSFormatted() {
        double[] tps = getTPS();
        if (tps == null) return "N/A (Paper required)";
        return DF.format(tps[0]) + ", "
                + DF.format(tps[1]) + ", "
                + DF.format(tps[2]);
    }

    /* ================= MEMORY ================= */

    public static long getUsedMemoryMB() {
        Runtime r = Runtime.getRuntime();
        return (r.totalMemory() - r.freeMemory()) / 1024 / 1024;
    }

    public static long getMaxMemoryMB() {
        return Runtime.getRuntime().maxMemory() / 1024 / 1024;
    }

    public static long getFreeMemoryMB() {
        return Runtime.getRuntime().freeMemory() / 1024 / 1024;
    }

    /* ================= CPU ================= */

    public static double getCpuLoadPercent() {
        OperatingSystemMXBean os = ManagementFactory.getOperatingSystemMXBean();
        try {
            var method = os.getClass().getMethod("getSystemCpuLoad");
            method.setAccessible(true);
            double load = (double) method.invoke(os);
            return load < 0 ? -1 : load * 100;
        } catch (Exception e) {
            return -1;
        }
    }

    /* ================= SERVER ================= */

    public static int getOnlinePlayers() {
        return Bukkit.getOnlinePlayers().size();
    }

    public static int getMaxPlayers() {
        return Bukkit.getMaxPlayers();
    }

    public static long getUptimeSeconds() {
        long startTime = ManagementFactory.getRuntimeMXBean().getStartTime();
        return (System.currentTimeMillis() - startTime) / 1000;
    }

    /* ================= UTIL ================= */

    public static String formatMB(long mb) {
        return mb + " MB";
    }
}
