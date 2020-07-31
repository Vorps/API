package net.vorps.api.commands;

public interface CommandSender {

    void sendMessage(String message);
    String getName();
    boolean hasPermission(String permission);
    boolean isPlayer();
}
