package net.vorps.api.data;

import net.vorps.api.databases.Database;
import lombok.Getter;
import net.vorps.api.players.PlayerData;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Project Bungee Created by Vorps on 20/02/2017 at 15:55.
 */
public abstract class Data {

    public static final SimpleDateFormat FORMAT_HOUR_MINUTE_SECOND;
    public static final SimpleDateFormat FORMAT_DAY_MONTH_YEAR;
    public static final SimpleDateFormat FORMAT_DAY_MONTH_YEAR_HOUR_MINUTE_SECOND;
    public static final String SERVER_NAME;
    public static Class<? extends Data> dataClass;
    private static HashMap<String, UUID> listPlayerString;
    private static HashMap<UUID, String> listPlayerUUID;

    static {
        Data.listPlayerString = new HashMap<>();
        Data.listPlayerUUID = new HashMap<>();
        FORMAT_HOUR_MINUTE_SECOND = new SimpleDateFormat("HH:mm:ss");
        FORMAT_DAY_MONTH_YEAR = new SimpleDateFormat("dd/MM/yyyy");
        FORMAT_DAY_MONTH_YEAR_HOUR_MINUTE_SECOND = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        SERVER_NAME = System.getProperty("user.dir");
        Data.loadListPlayer();
    }

    public static void reload() {
        for(Method method : dataClass.getMethods()){
            if(method.getAnnotationsByType(DataReload.class).length == 1){
                try {
                    method.invoke(null);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void reload(String method) {
        try {
            dataClass.getMethod(method).invoke(null);
        } catch (IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @DataReload
    public static void loadListPlayer(){
        Data.listPlayerString.clear();
        Data.listPlayerUUID.clear();
        try {
            ResultSet result = Database.BUNGEE.getDatabase().getDataColumn("player", "p_uuid, p_name");
            while (result.next()){
                Data.listPlayerString.put(result.getString(2), UUID.fromString(result.getString(1)));
                Data.listPlayerUUID.put(UUID.fromString(result.getString(1)), result.getString(2));
            }
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean isPlayer(String name){
        return Data.listPlayerString.containsKey(name);
    }

    public static boolean isPlayer(UUID uuid){
        return Data.listPlayerUUID.containsKey(uuid);
    }

    public static boolean isPlayerOnline(String name){
        return PlayerData.isPlayerDataCore(name);
    }

    public static boolean isPlayerPlayerOnline(UUID uuid){
        return PlayerData.isPlayerDataCore(uuid);
    }

    public static UUID getUUIDPlayer(String namePlayer){
        return Data.listPlayerString.get(namePlayer);
    }

    public static String getNamePlayer(UUID uuid){return Data.listPlayerUUID.get(uuid);}

    public static String getNamePlayer(String uuid){return Data.listPlayerUUID.get(UUID.fromString(uuid));}

    public static List<String> getNamePlayers(){
        return new ArrayList<>(Data.listPlayerString.keySet());
    }

    public static void addPlayer(String namePlayer, UUID uuidPlayer){
        Data.listPlayerString.put(namePlayer, uuidPlayer);
        Data.listPlayerUUID.put(uuidPlayer, namePlayer);
    }

    public static List<String> getOnlineNamePlayers(){
        return PlayerData.getListPlayerData();
    }
}
