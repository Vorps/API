package net.vorps.api.commands;

import lombok.AllArgsConstructor;
import net.vorps.api.lang.Lang;

import java.util.List;

/**
 * Project Bungee Created by Vorps on 10/05/2017 at 02:01.
 */
public class TabCompletion {

    public String name;
    public String surname;
    public TabCompletionList tabCompletionList;

    public TabCompletion(String name, String surname, TabCompletionList tabCompletionList){
        this.name = name;
        this.surname = surname;
        this.tabCompletionList = tabCompletionList;
    }

    public TabCompletion(String name, TabCompletionList tabCompletionList){
        this(name, name, tabCompletionList);
    }

    public void error(String nameCommand, CommandSender commandSender, String parameter){
        commandSender.sendMessage("CMD.ERROR."+this.name, new Lang.Args(Lang.Parameter.VAR, parameter));
    }

}