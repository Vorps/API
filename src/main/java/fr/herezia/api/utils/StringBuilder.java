package fr.herezia.api.utils;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Project Bungee Created by Vorps on 14/02/2017 at 00:59.
 */
public class StringBuilder {
    private java.lang.StringBuilder message;
    private String[] args;
    private String separator;
    private boolean color;

    {
        this.message = new java.lang.StringBuilder();
        this.separator = " ";
        this.args = null;
        this.color = false;
    }

    public StringBuilder(){
    }

    public StringBuilder(String message, String separator){
        this(message);
        this.separator = separator;
    }

    public StringBuilder(String message){
        this.message = new java.lang.StringBuilder(message);
    }

    public StringBuilder(String args[], String separator, int start, int fin){
        this.args = args;
        this.separator = separator;
        this.createMessage(start, fin);
    }

    public StringBuilder(String args[], String separator, int start){
        this(args, separator, start, args.length);
    }

    public StringBuilder(String separator, int fin, String args[]){
        this(args, separator, 0, fin);
    }

    public StringBuilder withColor(boolean state){
        this.color = state;
        return this;
    }

    public StringBuilder withSeparator(String separator){
        this.separator = separator;
        return this;
    }

    public String getString(){
        return this.color ? ChatColor.colorMessage(this.message).toString() : this.message.toString();
    }

    public String getString(CommandSender sender, String permission){
        return this.color ? ChatColor.chatColor(sender, this.getString(), permission) : this.getString();
    }

    public String[] getArgs(){
        ArrayList<String> loreTab = new ArrayList<>();
        int y = 0;
        this.args = new String[0];
        if(this.message != null){
            for(int i = 0; i < this.message.length(); i++)
                if(message.charAt(i) == this.separator.charAt(0)){
                    loreTab.add(this.message.substring(y, i));
                    y = i+1;
                }
            if(y < message.length()) loreTab.add(this.message.substring(y, this.message.length()));
            this.args = loreTab.toArray(new String[loreTab.size()]);
        }
        return this.args;
    }

    public void replace(int start, String wordNew){
        this.args[start] = wordNew;
        this.createMessage(0, args.length);
    }

    public void replace(String wordLast, String wordNew){
        if(args == null) this.getArgs();
        for(int i = 0; i < this.args.length; i++) if(this.args[i].equalsIgnoreCase(wordLast)) this.replace(i, wordNew);
    }

    public void createMessage(int start, int fin){
        this.message = new java.lang.StringBuilder();
        for(int i = start; i < fin; i++) this.message.append(this.args[i]).append(i+1 == fin ? "" : this.separator);
    }

    public static <T> T[] convert(ArrayList<T> value, T[] instance){
        return value.toArray(value.toArray(instance));
    }

    public static <T> ArrayList<T> convert(T[] value){
        return new ArrayList<>(Arrays.asList(value));
    }
}
