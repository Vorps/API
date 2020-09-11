package net.vorps.api.commands;

import java.util.List;

@FunctionalInterface
public interface TabCompletionList {
    List<String> getList(CommandSender commandSender);
}