package net.vorps.api.commands;

public class Help {

    public static void list(CommandSender commandSender){
        for(Command command : Command.commands){
            commandSender.sendMessage(command.getName());
        }
    }

    public static void help(CommandSender commandSender, @CommandParameter("command") String commandName){

        for(Command command : Command.commands){
            commandSender.sendMessage(command.getName());
        }
    }
}
