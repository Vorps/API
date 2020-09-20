package net.vorps.api.listeners;

import net.vorps.api.API;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;

/**
 * Project API Created by Vorps on 02/02/2017 at 14:31.
 */
public class ListenerManager{

    public ListenerManager(Listener... listeners){
        for(Listener listener : listeners) Bukkit.getPluginManager().registerEvents(listener, API.getPlugin());
    }
}
