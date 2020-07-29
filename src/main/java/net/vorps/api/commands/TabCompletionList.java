package net.vorps.api.commands;

import java.util.Set;

/**
 * Project Bungee Created by Vorps on 10/05/2017 at 02:01.
 */
@FunctionalInterface
public interface TabCompletionList {

    Set<String> getList(String[] args, String sender, String[] playerList);

}