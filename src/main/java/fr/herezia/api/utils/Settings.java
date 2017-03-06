package fr.herezia.api.utils;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.data.Data;
import fr.herezia.api.databases.Database;
import lombok.Getter;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Project Bungee Created by Vorps on 20/02/2017 at 16:11.
 */
public class Settings {

    private @Getter boolean valueBoolean;
    private @Getter int valueInt;
    private @Getter String message;

    public Settings(ResultSet result) throws SqlException {
        this.valueBoolean = Database.SERVER.getDatabase().getBoolean(result, 2);
        this.valueInt = Database.SERVER.getDatabase().getInt(result, 3);
        this.message = Database.SERVER.getDatabase().getString(result, 4);
        Settings.settings.put(Database.SERVER.getDatabase().getString(result, 1), this);
    }

    private static HashMap<String, Settings> settings;

    static {
        Settings.settings = new HashMap<>();
        Data.getInstance().loadSetting();
    }

    public static Settings getSettings(String key){
        return Settings.settings.get(key);
    }
    public static void clear(){
        Settings.settings.clear();
    }

}
