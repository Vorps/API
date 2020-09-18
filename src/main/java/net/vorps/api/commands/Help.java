package net.vorps.api.commands;

public class Help {

    @CommandPermission("list")
    public static void help(CommandSender commandSender){
        for(String nameCommand : Command.commands.keySet()){
            if(commandSender.hasPermissionStartWith(nameCommand)){
                commandSender.sendMessage(nameCommand);
            }
        }
    }

    @CommandPermission("command")
    public static void help(CommandSender commandSender, @CommandParameter("command") String commandName){
        Help.help(commandSender, commandName, 1);
    }

    @CommandPermission("command.page")
    public static void help(CommandSender commandSender, @CommandParameter("command") String commandName, @CommandParameter("page") Integer page){
        Command command = Command.commands.get(commandName);
        command.helpFunction(commandSender, page);
    }
}
