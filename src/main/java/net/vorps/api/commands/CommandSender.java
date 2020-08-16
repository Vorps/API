package net.vorps.api.commands;

import java.util.ArrayList;
import java.util.UUID;

public interface CommandSender {

    void sendMessage(String message);
    boolean hasPermission(ArrayList<String> permission);
    String getLang();
    boolean hasPermissionStartWith(String permission);
    String getName();
    boolean isPlayer();
}
