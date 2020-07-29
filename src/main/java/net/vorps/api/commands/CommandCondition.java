package net.vorps.api.commands;

/**
 * Project Bungee Created by Vorps on 10/05/2017 at 01:59.
 */
@FunctionalInterface
public interface CommandCondition {

    boolean condition();
}