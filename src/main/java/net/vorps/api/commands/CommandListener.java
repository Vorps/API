package net.vorps.api.commands;

import net.vorps.api.API;
import net.vorps.api.players.PlayerData;
import net.vorps.api.utils.Settings;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandListener extends Command implements TabExecutor {

    private net.vorps.api.commands.Command command;

    public CommandListener(net.vorps.api.commands.Command command) {
        super(command.getName());
        this.command = command;
        API.getPlugin().getCommand(command.getName()).setExecutor(this);
    }

    @Override
    public boolean execute(CommandSender sender, String command ,String[] args) {
        return this.command.execute(new net.vorps.api.commands.CommandSender() {
            @Override
            public void sendMessage(String message) {
                sender.sendMessage(message);
            }

            @Override
            public String getName() {
                return sender.getName();
            }

            @Override
            public boolean hasPermission(String permission) {
                return sender.hasPermission(permission);
            }

            @Override
            public boolean isPlayer() {
                return sender instanceof Player;
            }
        }, args);
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args){
        return this.execute(sender, label, args);
    }

    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args){
        return this.command.onTabComplete(new net.vorps.api.commands.CommandSender() {
            @Override
            public void sendMessage(String message) {
                sender.sendMessage(message);
            }

            @Override
            public String getName() {
                return sender.getName();
            }

            @Override
            public boolean hasPermission(String permission) {
                return sender.hasPermission(permission);
            }

            @Override
            public boolean isPlayer() {
                return sender instanceof Player;
            }
        }, args);
    }
}
