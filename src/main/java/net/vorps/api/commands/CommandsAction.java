package net.vorps.api.commands;

import lombok.AllArgsConstructor;
import net.vorps.api.lang.Lang;
import net.vorps.api.players.PlayerData;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Project Hub Created by Vorps on 02/03/2016 at 21:03.
 */
@AllArgsConstructor
public class CommandsAction {

    private final String name;
    private final CommandSender commandSender;
    private final Consumer<Boolean> booleanConsumer;
    private final Supplier<Boolean> booleanSupplier;


    public void on(){
        booleanConsumer.accept(true);
        this.message_me();
    }

    public void off(){
        booleanConsumer.accept(false);
        this.message_me();
    }

    public void toggle(){
        booleanConsumer.accept(!booleanSupplier.get());
        this.message_me();
    }

    public void on(Player player){
        booleanConsumer.accept(true);
        this.message_player(player);
    }

    public void off(Player player){
        booleanConsumer.accept(false);
        this.message_player(player);
    }

    public void toggle(Player player){
        booleanConsumer.accept(!booleanSupplier.get());
        this.message_player(player);
    }

    private void message_me(){
        this.commandSender.sendMessage("CMD."+this.name+".SENDER.ME", new Lang.Args(Lang.Parameter.STATE, this.booleanSupplier.get() ? "ON" : "OFF"));
    }

    private void message_player(Player player){
        this.commandSender.sendMessage("CMD."+this.name+".SENDER", new Lang.Args(Lang.Parameter.STATE, this.booleanSupplier.get() ? "ON" : "OFF"), new Lang.Args(Lang.Parameter.PLAYER, player.getName()));
        player.sendMessage("CMD."+this.name+".PLAYER", new Lang.Args(Lang.Parameter.STATE, player.getName()), new Lang.Args(Lang.Parameter.AUTHOR, this.commandSender.getName()));
    }

}
