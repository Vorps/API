package net.vorps.api.commands;

import net.vorps.api.API;
import net.vorps.api.data.Data;
import net.vorps.api.players.PlayerData;
import net.vorps.api.utils.Settings;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CommandListener extends Command implements TabExecutor {

    private final net.vorps.api.commands.Command command;

    public CommandListener(net.vorps.api.commands.Command command) {
        super(command.getName());
        this.command = command;
        API.getPlugin().getCommand(command.getName()).setExecutor(this);
    }

    @Override
    public boolean execute(CommandSender sender, String command ,String[] args) {
        return this.command.execute(CommandListener.getCommandSender(sender), args);
    }

    private static net.vorps.api.commands.CommandSender getCommandSender(CommandSender sender){
        return new net.vorps.api.commands.CommandSender() {
            @Override
            public void sendMessage(String message) {
                sender.sendMessage(message);
            }

            @Override
            public boolean hasPermission(ArrayList<String> permission) {
                return permission.stream().map(sender::hasPermission).reduce(true, (last, next) -> last && next);
            }

            @Override
            public String getLang() {
                if(isPlayer() && PlayerData.isPlayerDataCore(sender.getName())) return  PlayerData.getPlayerDataCore(sender.getName()).getLang();
                return Settings.getConsoleLang();
            }

            @Override
            public boolean hasPermissionStartWith(String permission) {
                if(!(sender instanceof Player)) return true;
                for(Object perm : sender.getEffectivePermissions().toArray()){
                    if(perm.toString().startsWith(permission)) return true;
                }
                return false;
            }

            @Override
            public String getName() {
                return sender.getName();
            }

            @Override
            public boolean isPlayer() {
                return sender instanceof Player;
            }
        };
    }

    public boolean onCommand(CommandSender sender, org.bukkit.command.Command command, String label, String[] args){
        return this.execute(sender, label, args);
    }

    public List<String> onTabComplete(CommandSender sender, org.bukkit.command.Command command, String label, String[] args){
        return this.command.onTabComplete(CommandListener.getCommandSender(sender), args);
    }
}
