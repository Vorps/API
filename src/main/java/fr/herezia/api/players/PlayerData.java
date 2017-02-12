package fr.herezia.api.players;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;
import fr.herezia.api.databases.DatabaseManager;
import fr.herezia.api.lang.Lang;
import fr.herezia.api.rank.Rank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;

/**
 * Project EclipseApi Created by Vorps on 10/09/2016 at 20:44.
 */
@AllArgsConstructor
public abstract class PlayerData {

    private @Getter String lang;
    private @Getter UUID uuid;

    private static ResultSet getData(final String table, final String condition){
        ResultSet resultSet = null;
        try {
            resultSet = Database.SERVER.getDatabase().getData(table, condition);
            resultSet.next();
        } catch (SqlException e){
            e.printStackTrace();
        } catch (SQLException e){
            //
        }
        return resultSet;
    }

    public static String getLang(UUID uuid) throws SqlException{
        return Database.SERVER.getDatabase().getString(PlayerData.getData("player_setting", "ps_uuid = '"+uuid+"'"), 3);
    }

    public static void setLang(UUID uuid, String lang) throws SqlException{
        Database.SERVER.getDatabase().updateTable("player_setting", "ps_uuid = '"+uuid+"'", new DatabaseManager.Values("ps_lang", lang));
    }

    public static double getMoney(UUID uuid, String money) throws SqlException{
        return Database.SERVER.getDatabase().getDouble(PlayerData.getData("player_money", "pm_uuid = '"+uuid+"' && pm_money = '"+money+"'"), 3);
    }

    public static void addMoney(UUID uuid, String money, double value){
        try {
            Database.SERVER.getDatabase().updateTable("player_money", "pm_uuid = '"+uuid+"' && pm_money = '"+money+"'", "+", new DatabaseManager.Values("pm_value", value));
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static void setMoney(UUID uuid, String money, double value){
        try {
            Database.SERVER.getDatabase().updateTable("player_money", "pm_uuid = '"+uuid+"' && pm_money = '"+money+"'", new DatabaseManager.Values("pm_value", value));
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static void removeMoney(UUID uuid, String money, double value){
        try {
            Database.SERVER.getDatabase().updateTable("player_money", "pm_uuid = '"+uuid+"' && pm_money = '"+money+"'", "-", new DatabaseManager.Values("pm_value", value));
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static String getRank(UUID uuid) throws SqlException {
        return Database.SERVER.getDatabase().getString(PlayerData.getData("player_setting", "ps_uuid = '"+uuid+"'"), 2);
    }

    public static void setHub(UUID uuid, String hub) throws SqlException{
        Database.SERVER.getDatabase().updateTable("player", "p_uuid = '"+uuid+"'", new DatabaseManager.Values("p_hub", hub));
    }

    public static void setRank(UUID uuid, String rank){
        try {
            Database.SERVER.getDatabase().updateTable("player_setting", "ps_uuid = '"+uuid+"'", new DatabaseManager.Values("ps_rank", rank));
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static void addNotification(UUID uuid, String message){
        try {
            Database.SERVER.getDatabase().insertTable("notification", uuid.toString(), message);
        } catch (SqlException e){
            e.printStackTrace();
        }
    }

    public static void broadCast(String key, PlayerData[] playerDataList, Lang.Args... args){
        for(PlayerData playerData : playerDataList) Bukkit.getPlayer(playerData.uuid).sendMessage(Lang.getMessage(key, playerData.lang, args));
    }

    public static boolean lobby(UUID uuid){
        return true;
    }
}
