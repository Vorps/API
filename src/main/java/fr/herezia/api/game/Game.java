package fr.herezia.api.game;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

/**
 * Project API Created by Vorps on 15/02/2017 at 00:01.
 */
public abstract class Game extends JavaPlugin{

    private @Getter ArrayList<String> player;

    protected Game(){
        this.player = new ArrayList<>();
    }

}
