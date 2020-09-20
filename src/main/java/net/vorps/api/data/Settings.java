package net.vorps.api.data;

import lombok.Getter;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Project Bungee Created by Vorps on 20/02/2017 at 16:11.
 */
public class Settings {

    private @Getter final boolean valueBoolean;
    private @Getter final int valueInt;
    private @Getter final String message;

    public Settings(ResultSet result) throws SQLException {
        this.valueBoolean = result.getBoolean(2);
        this.valueInt = result.getInt(3);
        this.message = result.getString(4);
        Settings.settings.put(result.getString(1), this);
    }

    private static HashMap<String, Settings> settings;

    static {
        Settings.settings = new HashMap<>();
    }

    public static Settings getSettings(String key){
        return Settings.settings.get(key);
    }

    public static void clear(){
        Settings.settings.clear();
    }


}
