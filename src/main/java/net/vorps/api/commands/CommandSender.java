package net.vorps.api.commands;

import net.vorps.api.lang.Lang;

import java.util.ArrayList;
import java.util.UUID;

public interface CommandSender {

    void sendMessage(String key, Lang.Args... args);
    boolean hasPermission(String ...permission);
    boolean hasPermissionStartWith(String permission);
    String getName();
    UUID getUUID();
}
