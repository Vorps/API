package net.vorps.api.objects;

import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.data.Data;
import net.vorps.api.data.DataCore;
import net.vorps.api.databases.DatabaseManager;
import net.vorps.api.lang.Lang;
import lombok.AllArgsConstructor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import net.vorps.api.utils.StringBuilder;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

/**
 * Project Bungee Created by Vorps on 26/02/2017 at 18:06.
 */
@AllArgsConstructor
public class InteractMessage {

    private String message;
    private String lore;
    private String command;

    {
        this.message = "";
        this.lore = "";
        this.command = "";
    }

    public InteractMessage(ResultSet result) throws SQLException {
        this.message = result.getString(2);
        this.lore = result.getString(3);
        this.command = result.getString(4);
        InteractMessage.interactMessageList.put(result.getString(1), this);
    }

    public InteractMessage(){

    }

    public InteractMessage withMessage(String message){
        this.message = message;
        return this;
    }

    public InteractMessage withLore(String lore){
        this.lore = lore;
        return this;
    }

    public InteractMessage withCommand(String command){
        this.command = command;
        return this;
    }

    public TextComponent get(String key, String lang){
        TextComponent message = new TextComponent(Lang.getMessage(this.message, lang));
        StringBuilder stringBuilder = new StringBuilder(this.command);
        String[] args = new StringBuilder(key, ";").getArgs();
        for(int i = 1; i < args.length; i++){
            String[] a = new StringBuilder(args[i], ":").getArgs();
            Args args1 = new Args(Parameter.valueOf(a[0].toUpperCase()), a[1]);
            stringBuilder.replace(args1.parameter.label, (Data.isUUID(args1.value) && Data.isPlayer(UUID.fromString(args1.value))) ? Data.getNamePlayer(args1.value) : args1.value);
        }
        message.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, stringBuilder.getString()));
        message.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(Lang.getMessage(this.lore, lang)).create()));
        return message;
    }

    static {
        InteractMessage.interactMessageList = new HashMap<>();
        DataCore.getInstance().loadInteractMessage();
    }

    private static HashMap<String, InteractMessage> interactMessageList;

    public static void clear(){
        InteractMessage.interactMessageList.clear();
    }

    public static boolean isInteractMessage(String key){
        return InteractMessage.interactMessageList.containsKey(new StringBuilder(key, ";").getArgs()[0]);
    }

    public static InteractMessage getInteractMessage(String key){
        return InteractMessage.interactMessageList.get(new StringBuilder(key, ";").getArgs()[0]);
    }

    public enum Parameter{
        PLAYER("<player>");

        private final String label;

        Parameter(String label){
            this.label = label;
        }
    }

    public static class Args{
        private final Parameter parameter;
        private final String value;

        public Args(Parameter parameter, String value){
            this.parameter = parameter;
            this.value = value;
        }
    }
}
