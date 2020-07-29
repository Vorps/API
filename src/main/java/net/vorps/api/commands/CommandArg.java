package net.vorps.api.commands;

import lombok.Getter;

/**
 * Project Bungee Created by Vorps on 10/05/2017 at 01:57.
 */
public class CommandArg {

    private @Getter String arg;
    private @Getter boolean state;
    private @Getter Condition condition;
    private @Getter TabCompletionList list;

    public CommandArg(String arg) {
        this(arg, true);
    }

    public CommandArg(String arg, Condition condition) {
        this(arg, condition, true);
    }

    public CommandArg(String arg, boolean state) {
        this(arg, state, null);
    }

    public CommandArg(String arg, Condition condition, boolean state) {
        this(arg, condition, state, null);
    }

    public CommandArg(String arg, TabCompletionList list) {
        this(arg, true, list);
    }

    public CommandArg(String arg, Condition condition, TabCompletionList list) {
        this(arg, condition, true, list);
    }

    public CommandArg(String arg, boolean state, TabCompletionList list) {
        this(arg, new Condition(), state, list);
    }

    public CommandArg(String arg, Condition condition, boolean state, TabCompletionList list) {
        this.arg = arg;
        this.state = state;
        this.list = list;
        this.condition = condition;
    }

}
