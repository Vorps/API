package net.vorps.api.commands;

import net.vorps.api.data.Data;
import net.vorps.api.utils.StringBuilder;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * Project Bungee Created by Vorps on 10/05/2017 at 02:00.
 */
public enum TabCompletionType {
    PLAYER(() -> {
        return new ArrayList<>(Data.getListPlayerString().keySet());
    });

    private @Getter final TabCompletionList list;

    TabCompletionType(TabCompletionList list) {
        this.list = list;
    }
}
