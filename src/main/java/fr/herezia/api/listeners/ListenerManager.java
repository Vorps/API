package fr.herezia.api.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;

/**
 * Project API Created by Vorps on 02/02/2017 at 14:31.
 */
public class ListenerManager {

    public ListenerManager(Plugin plugin, Listener... listeners){
        for(Listener listener : listeners) Bukkit.getPluginManager().registerEvents(listener, plugin);
    }
}
