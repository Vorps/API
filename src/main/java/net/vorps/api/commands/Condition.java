package net.vorps.api.commands;

import lombok.Getter;
import lombok.Setter;

/**
 * Project Bungee Created by Vorps on 10/05/2017 at 01:56.
 */
public class Condition {

    private @Getter @Setter int indexPerm;
    private @Getter boolean player;
    private @Getter boolean statePerm;
    private @Getter CommandCondition condition;

    {
        this.player = false;
        this.statePerm = true;
        this.indexPerm = -1;
    }

    public Condition() {

    }

    public Condition(CommandCondition condition) {
        this(false, condition);
    }

    public Condition(boolean player) {
        this(player, true);
    }

    public Condition(boolean player, boolean statePerm) {
        this(player, statePerm, null);
    }


    public Condition(boolean player, CommandCondition condition) {
        this(player, true, condition);
    }

    public Condition(boolean player, boolean statePerm, CommandCondition condition) {
        this(-1, player, statePerm, condition);
    }

    public Condition(int indexPerm, CommandCondition condition) {
        this(indexPerm, false, condition);
    }

    public Condition(int indexPerm, boolean player) {
        this(indexPerm, player, true);
    }

    public Condition(int indexPerm, boolean player, boolean statePerm) {
        this(indexPerm, player, statePerm, null);
    }


    public Condition(int indexPerm, boolean player, CommandCondition condition) {
        this(indexPerm, player, true, condition);
    }

    public Condition(int indexPerm, boolean player, boolean statePerm, CommandCondition condition) {
        this.player = player;
        this.statePerm = statePerm;
        this.condition = condition;
        this.indexPerm = indexPerm;
    }

}