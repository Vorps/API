package fr.herezia.api.players;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;
import fr.herezia.api.databases.DatabaseManager;
import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Project EclipseApi Created by Vorps on 10/09/2016 at 20:44.
 */

public abstract class PlayerData {

    private @Getter UUID uuid;
    private @Getter @Setter String lang;

    protected PlayerData(UUID uuid){
        this.uuid = uuid;
    }

    private static ResultSet getData(final String table, final String condition){
        ResultSet resultSet = null;
        try {
            resultSet = Database.BUNGEE.getDatabase().getData(table, condition);
            resultSet.next();
        } catch (SqlException e){
            e.printStackTrace();
        } catch (SQLException e){
            //
        }
        return resultSet;
    }

    public static String getBonus(UUID uuid){
        String bonus = null;
        try {
            bonus = Database.BUNGEE.getDatabase().getString(PlayerData.getData("player_setting", "ps_uuid = '"+uuid+"'"), "ps_bonus");
        } catch (SqlException e) {
            e.printStackTrace();
        }
        return bonus;
    }

    public static String getChannel(UUID uuid){
        String channel = null;
        try {
            channel = Database.BUNGEE.getDatabase().getString(PlayerData.getData("player_setting", "ps_uuid = '"+uuid+"'"), "ps_channel");
        } catch (SqlException e) {
            e.printStackTrace();
        }
        return channel;
    }

    public static void setChannel(UUID uuid, String channel){
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '"+uuid+"'", new DatabaseManager.Values("ps_channel", channel));
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static Date getDateFirst(UUID uuid){
        Date date = new Date(System.currentTimeMillis());
        try {
            date = Database.BUNGEE.getDatabase().getDateTime(PlayerData.getData("player", "p_uuid = '"+uuid+"'"), "p_date_first");
        } catch (SqlException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Timestamp getDateLast(UUID uuid){
        Timestamp date = new Timestamp(System.currentTimeMillis());
        try {
             date = Database.BUNGEE.getDatabase().getTimestamp(PlayerData.getData("player", "p_uuid = '"+uuid+"'"), "p_date_last");
        } catch (SqlException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getIp(UUID uuid){
        String ip = "";
        try {
            ip = Database.BUNGEE.getDatabase().getString(PlayerData.getData("player", "p_uuid = '"+uuid+"'"), "p_ip");
        } catch (SqlException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public static boolean isChat(UUID uuid){
        boolean chat = true;
        try {
            chat = Database.BUNGEE.getDatabase().getBoolean(PlayerData.getData("player_setting", "ps_uuid = '"+uuid+"'"), "ps_chat");
        } catch (SqlException e) {
            e.printStackTrace();
        }
        return chat;
    }

    public static boolean isFly(UUID uuid){
        boolean fly = true;
        try {
            fly = Database.BUNGEE.getDatabase().getBoolean(PlayerData.getData("player_setting", "ps_uuid = '"+uuid+"'"), "ps_fly");
        } catch (SqlException e) {
            e.printStackTrace();
        }
        return fly;
    }

    public static void setFriends(UUID uuid, boolean state){
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '"+uuid+"'", new DatabaseManager.Values("ps_friends_enable", state));
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static boolean isFriends(UUID uuid){
        boolean friends = false;
        try {
            friends = Database.BUNGEE.getDatabase().getBoolean(PlayerData.getData("player_setting", "ps_uuid = '"+uuid+"'"), "ps_friends_enable");
        } catch (SqlException e){
            e.printStackTrace();
        }
        return friends;
    }

    public static void setParty(UUID uuid, boolean state){
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '"+uuid+"'", new DatabaseManager.Values("ps_party_enable", state));
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static boolean isParty(UUID uuid){
        boolean party = false;
        try {
            party = Database.BUNGEE.getDatabase().getBoolean(PlayerData.getData("player_setting", "ps_uuid = '"+uuid+"'"), "ps_party_enable");
        } catch (SqlException e){
            e.printStackTrace();
        }
        return party;
    }

    public static void setChat(UUID uuid, boolean state){
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '"+uuid+"'", new DatabaseManager.Values("ps_chat", state));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static void setFly(UUID uuid, boolean state){
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '"+uuid+"'", new DatabaseManager.Values("ps_fly", state));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static String getLang(UUID uuid){
        String lang = null;
        try {
            lang = Database.BUNGEE.getDatabase().getString(PlayerData.getData("player_setting", "ps_uuid = '"+uuid+"'"), "ps_lang");
        } catch (SqlException e) {
            e.printStackTrace();
        }
        return lang;
    }

    public static void setLang(UUID uuid, String lang){
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '"+uuid+"'", new DatabaseManager.Values("ps_lang", lang));
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static double getMoney(UUID uuid, String money){
        double value = 0;
        try {
            value = Database.BUNGEE.getDatabase().getDouble(PlayerData.getData("player_money", "pm_uuid = '"+uuid+"' && pm_money = '"+money+"'"), 3);
        } catch (SqlException e){
            e.printStackTrace();
        }
        return value;
    }

    public static void addMoney(UUID uuid, String money, double value){
        try {
            Database.BUNGEE.getDatabase().updateTable("player_money", "pm_uuid = '"+uuid+"' && pm_money = '"+money+"'", "+", new DatabaseManager.Values("pm_value", value));
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static void setMoney(UUID uuid, String money, double value){
        try {
            Database.BUNGEE.getDatabase().updateTable("player_money", "pm_uuid = '"+uuid+"' && pm_money = '"+money+"'", new DatabaseManager.Values("pm_value", value));
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static void removeMoney(UUID uuid, String money, double value){
        try {
            Database.BUNGEE.getDatabase().updateTable("player_money", "pm_uuid = '"+uuid+"' && pm_money = '"+money+"'", "-", new DatabaseManager.Values("pm_value", value));
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static String getRank(UUID uuid){
        String rank = null;
        try {
            rank = Database.BUNGEE.getDatabase().getString(PlayerData.getData("player_setting", "ps_uuid = '"+uuid+"'"), "ps_rank");
        }catch (SqlException e){
            e.printStackTrace();
        }
        return rank;
    }

    public static void setRank(UUID uuid, String rank){
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '"+uuid+"'", new DatabaseManager.Values("ps_rank", rank));
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static void addNotification(UUID uuid, String message, Type type){
        String message1 = "";
        for(int i = 0; i < message.length(); i++){
            if(message.charAt(i) == '\'') message1+="\\'";
            else message1+=message.charAt(i);
        }
        try {
            Database.BUNGEE.getDatabase().sendRequest("INSERT INTO `notification`(`n_uuid`, `n_message`, `n_date`, `n_type`) VALUES ('"+uuid+"','"+message1+"','"+new Timestamp(System.currentTimeMillis())+"','"+type.label+"')");
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static ArrayList<String> getNotification(UUID uuid){
        ArrayList<String> notification = new ArrayList<>();
        try {
            ResultSet resultSet = Database.BUNGEE.getDatabase().getData("notification", "n_uuid = '" + uuid.toString() + "'");
            while (resultSet.next()) notification.add(Database.BUNGEE.getDatabase().getString(resultSet, "n_message"));
            Database.BUNGEE.getDatabase().delete("notification", "n_uuid = '"+uuid.toString()+"'");
        } catch (SQLException e){
            //
        } catch (SqlException e){
            e.printStackTrace();
        }
        return notification;
    }

    public static boolean lobby(UUID uuid){
        return true;
    }

    public enum Type{
        GAME("GAME"),
        BUNGEE("BUNGEE");

        private String label;

        Type(String label){
            this.label = label;
        }
    }
}
