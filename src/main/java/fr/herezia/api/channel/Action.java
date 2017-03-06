package fr.herezia.api.channel;


import org.bukkit.entity.Player;

/**
 * Project Bungee Created by Vorps on 28/02/2017 at 19:46.
 */
public interface Action {

    void receive(Player player, byte[] message);

    String getName();
}
