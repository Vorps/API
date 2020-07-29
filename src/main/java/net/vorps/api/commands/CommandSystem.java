package net.vorps.api.commands;

import lombok.Getter;
import lombok.Setter;

/**
 * Project Bungee Created by Vorps on 10/05/2017 at 01:55.
 */
public class CommandSystem {

    private @Getter CommandArg args[];
    private @Getter CommandExecute commandExecute;
    private @Getter Condition condition;
    private @Getter @Setter int indexHelp;

    {
        this.args = new CommandArg[0];
    }

    public CommandSystem(CommandExecute commandExecute) {
        this(commandExecute, new CommandArg[0]);
    }

    public CommandSystem(CommandExecute commandExecute, CommandArg... args) {
        this(new Condition(), commandExecute, args);
    }


    public CommandSystem(Condition condition, CommandExecute commandExecute) {
        this(condition, commandExecute, new CommandArg[0]);
    }

    public CommandSystem(Condition condition, CommandExecute commandExecute, CommandArg... args) {
        this.condition = condition;
        this.commandExecute = commandExecute;
        this.args = args;
    }

}