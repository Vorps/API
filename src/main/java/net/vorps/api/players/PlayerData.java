package net.vorps.api.players;

import net.vorps.api.commands.Player;
import net.vorps.api.data.Data;
import net.vorps.api.databases.Database;
import net.vorps.api.databases.DatabaseManager;
import lombok.Getter;
import net.vorps.api.lang.Lang;
import net.vorps.api.objects.Money;
import net.vorps.api.objects.Rank;
import net.vorps.api.utils.Settings;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Project EclipseApi Created by Vorps on 10/09/2016 at 20:44.
 */

public abstract class PlayerData implements Player{

    protected @Getter final UUID UUID;
    protected @Getter final String name;
    protected String nickName;
    protected String lang;
    protected Rank rank;
    protected HashMap<String, Double> money;
    protected boolean isVanish;
    protected boolean isFly;
    protected boolean isBuild;
    protected boolean isVisible;

    protected PlayerData(UUID uuid, String name) {
        this.UUID = uuid;
        this.name = name;
        this.nickName = PlayerData.getNickName(uuid);
        this.lang = PlayerData.getLang(uuid);
        this.rank = PlayerData.getRank(uuid);
        this.money = new HashMap<>();
        this.isVanish = PlayerData.isVanish(uuid);
        this.isFly = PlayerData.isFly(uuid);
        this.isBuild = PlayerData.isBuild(uuid);
        this.isVisible = PlayerData.isVisible(uuid);
        for(String money : Money.getMoneys()) this.money.put(money, PlayerData.getMoney(uuid, money));
        PlayerData.playerDataList.put(this.name, this);
        PlayerData.playerDataUUIDList.put(this.UUID, this);
    }

    public void removePlayerData(){
        PlayerData.playerDataUUIDList.remove(this.UUID);
        PlayerData.playerDataList.remove(this.name);
    }

    private static HashMap<String, PlayerData> playerDataList = new HashMap<>();
    private static HashMap<UUID, PlayerData> playerDataUUIDList = new HashMap<>();


    static {
        PlayerData.playerDataList = new HashMap<>();
        PlayerData.playerDataUUIDList = new HashMap<>();
    }

    public static PlayerData getPlayerDataCore(String name) {
        return PlayerData.playerDataList.get(name);
    }
    public static PlayerData getPlayerDataCore(UUID uuid) {
        return PlayerData.playerDataUUIDList.get(uuid);
    }

    public static boolean isPlayerDataCore(String name) {
        return PlayerData.playerDataList.containsKey(name);
    }

    public static boolean isPlayerDataCore(UUID uuid) {
        return PlayerData.playerDataUUIDList.containsKey(uuid);
    }

    public static ArrayList<String> getListPlayerData(){
        return PlayerData.playerDataUUIDList.values().stream().map(PlayerData::getName).collect(Collectors.toCollection(ArrayList::new));
    }

    public static String getLang(UUID uuid) {
        String lang = Settings.getConsoleLang();
        if(uuid != null){
            if(PlayerData.isPlayerDataCore(uuid)){
                lang = PlayerData.getPlayerDataCore(uuid).lang;
            } else if(Data.isPlayer(uuid)){
                try {
                    lang = Database.BUNGEE.getDatabase().getDataUnique("player_setting", "ps_uuid = '" + uuid + "'").getString("ps_lang");
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return lang;
    }

    public static boolean isVanish(UUID uuid) {
        boolean isVanish = true;
        if(PlayerData.isPlayerDataCore(uuid)){
            isVanish =  PlayerData.getPlayerDataCore(uuid).isVanish;
        } else if(Data.isPlayer(uuid)){
            try {
                isVanish = Database.BUNGEE.getDatabase().getDataUnique("player_setting", "ps_uuid = '" + uuid + "'").getBoolean("ps_vanish");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isVanish;
    }

    public static boolean isFly(UUID uuid) {
        boolean isFly = false;
        if(PlayerData.isPlayerDataCore(uuid)){
            isFly =  PlayerData.getPlayerDataCore(uuid).isFly;
        } else if(Data.isPlayer(uuid)){
            try {
                isFly = Database.BUNGEE.getDatabase().getDataUnique("player_setting", "ps_uuid = '" + uuid + "'").getBoolean("ps_fly");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isFly;
    }

    public static boolean isBuild(UUID uuid) {
        boolean isBuild = true;
        if(PlayerData.isPlayerDataCore(uuid)){
            isBuild =  PlayerData.getPlayerDataCore(uuid).isBuild;
        } else if(Data.isPlayer(uuid)){
            try {
                isBuild = Database.BUNGEE.getDatabase().getDataUnique("player_setting", "ps_uuid = '" + uuid + "'").getBoolean("ps_build");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isBuild;
    }

    public static boolean isVisible(UUID uuid) {
        boolean isVisible = true;
        if(PlayerData.isPlayerDataCore(uuid)){
            isVisible =  PlayerData.getPlayerDataCore(uuid).isVisible;
        } else if(Data.isPlayer(uuid)){
            try {
                isVisible = Database.BUNGEE.getDatabase().getDataUnique("player_setting", "ps_uuid = '" + uuid + "'").getBoolean("ps_visible");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return isVisible;
    }


    public static String getNickName(UUID uuid) {
        String nickname = null;
        if(uuid != null){
            if(PlayerData.isPlayerDataCore(uuid)){
                nickname = PlayerData.getPlayerDataCore(uuid).nickName;
            } else if(Data.isPlayer(uuid)){
                try {
                    nickname = Database.BUNGEE.getDatabase().getDataUnique("player_setting", "ps_uuid = '" + uuid + "'").getString("ps_nickname");
                } catch (SQLException var3) {
                    var3.printStackTrace();
                }
            }
        }
        return nickname;
    }

    public static Rank getRank(UUID uuid) {
        Rank rank = null;
        if(uuid != null){
            if(PlayerData.isPlayerDataCore(uuid)){
                rank = PlayerData.getPlayerDataCore(uuid).rank;
            } else if(Data.isPlayer(uuid)){
                try {
                    rank = Rank.getRank(Database.BUNGEE.getDatabase().getDataUnique("player_setting", "ps_uuid = '" + uuid + "'").getString("ps_rank"));
                } catch (SQLException var3) {
                    var3.printStackTrace();
                }
            }
        }
        return rank;
    }

    public static double getMoney(UUID uuid, String money) {
        double value = 0;
        if(PlayerData.isPlayerDataCore(uuid)){
            value = PlayerData.getPlayerDataCore(uuid).money.get(money);
        } else {
            try {
                value = Database.BUNGEE.getDatabase().getDataUnique("player_money", "pm_uuid = '" + uuid + "' && pm_money = '" + money + "'").getDouble(3);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return value;
    }

    public static void setMoney(UUID uuid, String money, double value) {
        if(PlayerData.isPlayerDataCore(uuid)){
            PlayerData.getPlayerDataCore(uuid).money.put(money, value);
        }
        try {
            Database.BUNGEE.getDatabase().updateTable("player_money", "pm_uuid = '" + uuid + "' && pm_money = '" + money + "'", new DatabaseManager.Values("pm_value", value));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addNotification(UUID uuid, String key, Lang.Args... args) {
        String message = Lang.getMessage(key, PlayerData.getLang(uuid), args);
        try {
            Database.BUNGEE.getDatabase().insertTable("notification", null, uuid.toString(), message, new Timestamp(System.currentTimeMillis()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void sendMessage(UUID uuid, String key, Lang.Args... args) {
        if(PlayerData.isPlayerDataCore(uuid)) PlayerData.getPlayerDataCore(uuid).sendMessage(key, args);
        else PlayerData.addNotification(uuid, key, args);
    }



    /*    public static String getBonus(UUID uuid) {
        String bonus = null;
        try {
            bonus = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getString("ps_bonus");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bonus;
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



   public static void setFly(UUID uuid, boolean state) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_fly", state));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    /*public static void setShow(UUID uuid, boolean state) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_show", state));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/

    /*public static boolean isShow(UUID uuid) {
        boolean state = false;
        try {
            state = PlayerData.getData("player_setting", "ps_uuid = '" + uuid + "'").getBoolean("ps_show");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return state;
    }*/


    /*public static void setServer(UUID uuid, String server) {
        try {
            Database.BUNGEE.getDatabase().updateTable("player_setting", "ps_uuid = '" + uuid + "'", new DatabaseManager.Values("ps_server", server));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }*/
}
