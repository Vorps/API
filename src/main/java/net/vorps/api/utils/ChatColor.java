package net.vorps.api.utils;

import org.bukkit.command.CommandSender;

import java.lang.StringBuilder;

/**
 * Project FortycubeBungee Created by Vorps on 10/03/2016 at 02:40.
 */
public class ChatColor {

    public static String chatColor(boolean permission, String message){
        java.lang.StringBuilder messageBuild = new java.lang.StringBuilder(message);
        if(permission){
            for(int i = 0; i < messageBuild.length(); i++){
                if(messageBuild.charAt(i) == '&'){
                    messageBuild.replace(i, i+1, "§");
                }
            }
        }
        return messageBuild.toString().trim();
    }

    public static String chatColor(String message){
        java.lang.StringBuilder messageBuild = new java.lang.StringBuilder(message);
        for(int i = 0; i < messageBuild.length(); i++){
            if(messageBuild.charAt(i) == '&'){
                messageBuild.replace(i, i+1, "§");
            }
        }
        return messageBuild.toString().trim();
    }

    public static java.lang.StringBuilder colorMessage(StringBuilder message){
        for(int i = 0; i < message.length(); i++) if(message.charAt(i) == '&') message.replace(i, i+1, "§");
        return message;
    }
}
