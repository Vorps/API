package net.vorps.api.commands;

import net.vorps.api.lang.Lang;

import java.util.UUID;

public interface Player {
    void sendMessage(String key, Lang.Args... args);
    String getName();
    UUID getUUID();
}
