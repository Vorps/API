package fr.herezia.api.commands;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.command.CommandSender;

/**
 * Project API Created by Vorps on 04/03/2016 at 16:25.
 */
public abstract class Commands {
    private @Getter @Setter boolean stateExec;
    private @Getter CommandSender sender;
    private @Getter String permission;

    /**
     * Manage Commandsz
     * @param sender CommandSender
     * @param permission String
     */
    public Commands(CommandSender sender, String permission){
        this.sender = sender;
        this.permission = permission;
    }

    /**
     * Function end commande
     */
    public void onDisable(){
        if(!isStateExec() && this.sender.hasPermission(this.permission)){
            this.help();
        }
    }

    protected abstract void help();
}
