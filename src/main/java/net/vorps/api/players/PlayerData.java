package net.vorps.api.players;

import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.data.Data;
import net.vorps.api.databases.Database;
import net.vorps.api.databases.DatabaseManager;
import lombok.Getter;
import lombok.Setter;
import net.vorps.api.lang.Lang;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Project EclipseApi Created by Vorps on 10/09/2016 at 20:44.
 */

public abstract class PlayerData {

    protected @Getter final UUID uuid;
    protected @Getter final String name;
    private @Getter String nickName;
    protected  @Getter @Setter String lang;

    protected PlayerData(UUID uuid, String name) {
        this.uuid = uuid;
        this.name = name;
        this.nickName = PlayerData.getNickName(uuid);
        this.lang = PlayerData.getLang(uuid);
        PlayerData.playerDataList.put(name, this);
    }

    public void removePlayerData(){
        try {
            Data.database.updateTable("player", "p_uuid = '" + this.uuid + "'", new DatabaseManager.Values("p_online", false), new DatabaseManager.Values("p_date_last", new java.util.Date(System.currentTimeMillis())));
        } catch (SqlException e) {
            e.printStackTrace();
        }
        PlayerData.playerDataList.remove(this.name);
    }

    private static HashMap<String, PlayerData> playerDataList;

    {
        PlayerData.playerDataList = new HashMap<>();
    }

    public static PlayerData getPlayerDataCore(String name) {
        return PlayerData.playerDataList.get(name);
    }

    public Player getPlayer(){
        return Bukkit.getPlayer(this.uuid);
    }

    private static ResultSet getData(final String table, final String condition) {
        ResultSet resultSet = null;
        try {
            resultSet = Database.BUNGEE.getDatabase().getData(table, condition);
            resultSet.next();
        } catch (SqlException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            //
        }
        return resultSet;
    }

    public static String getBonus(UUID uuid) {
        String bonus = null;
        try {
            bonus = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getString("ps_bonus");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bonus;
    }

    public static String getChannel(UUID uuid) {
        String channel = null;
        try {
            channel = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getString("ps_channel");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return channel;
    }

    public static void setChannel(UUID uuid, String channel) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_channel", channel));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static Date getDateFirst(UUID uuid) {
        Date date = new Date(System.currentTimeMillis());
        try {
            date = PlayerData.getData("player", "p_uuid = '" + uuid + "'").getDate("p_date_first");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static Timestamp getDateLast(UUID uuid) {
        Timestamp date = new Timestamp(System.currentTimeMillis());
        try {
            date = PlayerData.getData("player", "p_uuid = '" + uuid + "'").getTimestamp("p_date_last");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String getIp(UUID uuid) {
        String ip = "";
        try {
            ip = PlayerData.getData("player", "p_uuid = '" + uuid + "'").getString("p_ip");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ip;
    }

    public static boolean isChat(UUID uuid) {
        boolean chat = true;
        try {
            chat = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getBoolean("ps_chat");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chat;
    }

    public static boolean isFly(UUID uuid) {
        boolean fly = true;
        try {
            fly = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getBoolean("ps_fly");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return fly;
    }

    public static void setFriends(UUID uuid, boolean state) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_friends_enable", state));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static boolean isFriends(UUID uuid) {
        boolean friends = false;
        try {
            friends = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getBoolean("ps_friends_enable");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return friends;
    }

    public static void setParty(UUID uuid, boolean state) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_party_enable", state));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static boolean isParty(UUID uuid) {
        boolean party = false;
        try {
            party = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getBoolean("ps_party_enable");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return party;
    }

    public static void setChat(UUID uuid, boolean state) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_chat", state));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static void setFly(UUID uuid, boolean state) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_fly", state));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static String getLang(UUID uuid) {
        String lang = null;
        try {
            lang = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getString("ps_lang");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lang;
    }

    public static void setLang(UUID uuid, String lang) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_lang", lang));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static double getMoney(UUID uuid, String money) {
        double value = 0;
        try {
            value = PlayerData.getData("player_money", "pm_uuid = '" + uuid + "' && pm_money = '" + money + "'").getDouble(3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return value;
    }

    public static void addMoney(UUID uuid, String money, double value) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_money", "pm_uuid = '" + uuid + "' && pm_money = '" + money + "'", "+", new DatabaseManager.Values("pm_value", value));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static void setMoney(UUID uuid, String money, double value) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_money", "pm_uuid = '" + uuid + "' && pm_money = '" + money + "'", new DatabaseManager.Values("pm_value", value));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static void removeMoney(UUID uuid, String money, double value) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_money", "pm_uuid = '" + uuid + "' && pm_money = '" + money + "'", "-", new DatabaseManager.Values("pm_value", value));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static String getRank(UUID uuid) {
        String rank = null;
        try {
            rank = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getString("ps_rank");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rank;
    }

    public static void setRank(UUID uuid, String rank) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_rank", rank));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

   /* public static void addNotification(UUID uuid, String message, Type type) {
        String message1 = "";
        for (int i = 0; i < message.length(); i++) {
            if (message.charAt(i) == '\'') message1 += "\\'";
            else message1 += message.charAt(i);
        }
        try {
            Database.BUNGEE.getDatabase().sendRequest("INSERT INTO `notification`(`n_uuid`, `n_message`, `n_date`, `n_type`) VALUES ('" + uuid + "','" + message1 + "','" + new Timestamp(System.currentTimeMillis()) + "','" + type.label + "')");
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }*/

    public static ArrayList<String> getNotification(UUID uuid) {
        ArrayList<String> notification = new ArrayList<>();
        try {
            ResultSet resultSet = Database.BUNGEE.getDatabase().getData("notification", "n_uuid = '" + uuid.toString() + "'");
            while (resultSet.next()) notification.add(resultSet.getString("n_message"));
            Database.BUNGEE.getDatabase().delete("notification", "n_uuid = '" + uuid.toString() + "'");
        } catch (SQLException e) {
            //
        } catch (SqlException e) {
            e.printStackTrace();
        }
        return notification;
    }

    public static void setNickName(UUID uuid, String nickname) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_nickname", nickname));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static String getNickName(UUID uuid) {
        String nickname = null;
        try {
            nickname = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getString("ps_nickname");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return nickname;
    }

    public static void setShow(UUID uuid, boolean state) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_show", state));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static boolean isShow(UUID uuid) {
        boolean state = false;
        try {
            state = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getBoolean("ps_show");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state;
    }

    /**
     * Send message all playerData (Lang)
     * @param key String
     * @param args Lang.Args
     */
    public static void broadCast(final String key, final Lang.Args... args){
        //PlayerData.playersList.values().forEach((PlayerData playerData) -> playerData.getPlayer().sendMessage(Settings.getTitle()+Lang.getMessage(key, playerData.lang, args)));
    }


    public static void setVanish(UUID uuid, boolean state) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_vanish", state));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static void setServer(UUID uuid, String server) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_server", server));
        } catch (SqlException e) {
            e.printStackTrace();
        }
    }

    public static boolean isVanish(UUID uuid) {
        boolean state = false;
        try {
            state = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getBoolean("ps_vanish");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state;
    }


}
