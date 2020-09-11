package net.vorps.api.commands;

import net.vorps.api.data.Data;
import net.vorps.api.lang.Lang;

public class Reload {

    public static void bungee(CommandSender commandSender){
        Data.reload();
        commandSender.sendMessage("CMD.RELOAD.BUNGEE");
    }

    @CommandPermission("module")
    public static void bungee(CommandSender commandSender, @CommandParameter("module") String module){
        Data.reload(module);
        commandSender.sendMessage("CMD.RELOAD.BUNGEE.METHOD", new Lang.Args(Lang.Parameter.VAR, module));
    }
}
