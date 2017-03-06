package fr.herezia.api.objects;

import fr.herezia.api.data.Data;
import org.bukkit.Bukkit;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class Location {

    public Location(final ResultSet result) throws SqlException {
        Location.listLocation.put(Database.SERVER.getDatabase().getString(result, 1), new org.bukkit.Location(Bukkit.getWorlds().get(0), Database.SERVER.getDatabase().getDouble(result, 2), Database.SERVER.getDatabase().getDouble(result, 3), Database.SERVER.getDatabase().getDouble(result, 4), Database.SERVER.getDatabase().getFloat(result, 5), Database.SERVER.getDatabase().getFloat(result, 6)));
    }

    private static HashMap<String, org.bukkit.Location> listLocation;

    static {
        Location.listLocation = new HashMap<>();
        Data.getInstance().loadLocation();
    }

    public static void clear(){
        Location.listLocation.clear();
    }

    public static org.bukkit.Location getLocation(final String name){
        return Location.listLocation.get(name);
    }
}
