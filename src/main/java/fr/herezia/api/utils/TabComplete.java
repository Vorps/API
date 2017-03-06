package fr.herezia.api.utils;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Project Bungee Created by Vorps on 04/03/2017 at 19:10.
 */
public class TabComplete {

    public static Set<String> searchPlayer(String[] args, String... playerList){
        Set<String> matches = new HashSet<>();
        String search = args[args.length-1];
        Bukkit.getOnlinePlayers().forEach((Player player) -> {
            if(!StringBuilder.convert(playerList).contains(player.getName())){
                if(player.getName().toLowerCase().startsWith(search)) matches.add(player.getName());
            }
        });
        return matches;
    }

    public static Set<String> searchPlayer(String[] args, Set<String> list, String... playerList){
        Set<String> matches = new HashSet<>();
        String search = args[args.length-1];
        list.forEach((String player) -> {
            if(!StringBuilder.convert(playerList).contains(player)){
                if(player.toLowerCase().startsWith(search)) matches.add(player);
            }
        });
        return matches;
    }

    public static Set<String> searchPlayer(String[] args, ArrayList<String> list, String... valueList){
        Set<String> matches = new HashSet<>();
        String search = args[args.length-1];
        list.forEach((String value) -> {
            if(!StringBuilder.convert(valueList).contains(value.toLowerCase())){
                if(value.toLowerCase().startsWith(search.toLowerCase())) matches.add(value);
            }
        });
        return matches;
    }
}
