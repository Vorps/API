package net.vorps.api.commands;

/**
 * Project Bungee Created by Vorps on 15/05/2017 at 05:03.
 */
@FunctionalInterface
public interface CommandExecute {

    void execute(String[] args);
}
