package net.vorps.api.data;

import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.databases.Database;
import net.vorps.api.databases.DatabaseManager;
import lombok.Getter;
import net.vorps.api.utils.Settings;

import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.UUID;

/**
 * Project Bungee Created by Vorps on 20/02/2017 at 15:55.
 */
public abstract class Data {

    public static final SimpleDateFormat FORMAT_HOUR_MINUTE_SECOND;
    public static final SimpleDateFormat FORMAT_DAY_MONTH_YEAR;
    public static final SimpleDateFormat FORMAT_DAY_MONTH_YEAR_HOUR_MINUTE_SECOND;

    private static @Getter HashMap<String, UUID> listPlayerString;
    private static @Getter HashMap<UUID, String> listPlayerUUID;

    static {
        Data.listPlayerString = new HashMap<>();
        Data.listPlayerUUID = new HashMap<>();
        FORMAT_HOUR_MINUTE_SECOND = new SimpleDateFormat("HH:mm:ss");
        FORMAT_DAY_MONTH_YEAR = new SimpleDateFormat("dd/MM/yyyy");
        FORMAT_DAY_MONTH_YEAR_HOUR_MINUTE_SECOND = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Data.loadListPlayer();
    }

    public static void loadListPlayer(){
        Data.listPlayerString.clear();
        Data.listPlayerUUID.clear();
        try {
            ResultSet result = Database.BUNGEE.getDatabase().getDataColumn("player", "p_uuid, p_name");
            while (result.next()){
                Data.listPlayerString.put(result.getString(2), UUID.fromString(result.getString(1)));
                Data.listPlayerUUID.put(UUID.fromString(result.getString(1)), result.getString(2));
            }
        } catch (SqlException | SQLException e){
            e.printStackTrace();
        }
    }




    public static boolean isUUID(String uuid){
        return uuid.split("-").length == 5;
    }

    public static boolean isPlayer(String name){
        return Data.listPlayerString.containsKey(name);
    }

    public static boolean isPlayer(UUID uuid){
        return Data.listPlayerUUID.containsKey(uuid);
    }

    public static UUID getUUIDPlayer(String namePlayer){
        return Data.listPlayerString.get(namePlayer);
    }

    public static String getNamePlayer(UUID uuid){return Data.listPlayerUUID.get(uuid);}

    public static String getNamePlayer(String uuid){return Data.listPlayerUUID.get(UUID.fromString(uuid));}
}
