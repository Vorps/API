package net.vorps.api.commands;

import net.vorps.api.data.Data;
import net.vorps.api.utils.StringBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;

/**
 * Project Bungee Created by Vorps on 10/05/2017 at 02:00.
 */
public enum TabCompletionType {
    PLAYER_ONLINE((String[] args, String sender, String... playerList) -> {
        Set<String> matches = new HashSet<>();
        Bukkit.getOnlinePlayers().forEach((Player player) -> {
            if (!containsPlayer(playerList, player.getName())) matches.add(player.getName());
        });
        return matches;
    }),
    PLAYER((String[] args, String sender, String[] playerList) -> {
        Set<String> matches = new HashSet<>();
        Data.getListPlayerString().keySet().forEach((String player) -> {
            if (!containsPlayer(playerList, player) && !StringBuilder.convert(playerList).contains("CONSOLE"))
                matches.add(player);
        });
        return matches;
    });

    private static boolean containsPlayer(String[] playerList, String player) {
        return StringBuilder.convert(playerList).contains(player);
    }

    private @Getter TabCompletionList list;

    TabCompletionType(TabCompletionList list) {
        this.list = list;
    }
}
