package com.cherrycolagaming.util;

import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Rory on 7/8/2017.
 */
public class CustomConfigUtil {

    public static void loadDefaultConfig(JavaPlugin plugin) {
        plugin.getConfig().options().copyDefaults(true);
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
    }

}
