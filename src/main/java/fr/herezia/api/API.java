package fr.herezia.api;

import fr.herezia.api.bungee.Bungee;
import org.bukkit.plugin.java.JavaPlugin;

import fr.herezia.api.databases.Database;
import lombok.Getter;

/**
 * Project EclipseApi Created by Vorps on 08/11/2016 at 19:00.
 */
public class API extends JavaPlugin {

    private static @Getter JavaPlugin instance;

    @Override
    public void onEnable(){
        API.instance = this;
        Bungee.command();
    }

    @Override
    public void onDisable(){
        Database.closeAllDataBases();
    }

}
