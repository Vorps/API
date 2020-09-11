package net.vorps.api.objects;

import net.vorps.api.data.DataCore;
import org.bukkit.Bukkit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Project SnoWar Created by Vorps on 21/07/2016 at 15:36.
 */
public class Location {

    public Location(final ResultSet result) throws SQLException {
        Location.listLocation.put(result.getString(1), new org.bukkit.Location(Bukkit.getWorlds().get(0), result.getDouble(2), result.getDouble(3), result.getDouble(4), result.getFloat(5), result.getFloat(6)));
    }

    private static HashMap<String, org.bukkit.Location> listLocation;

    static {
        Location.listLocation = new HashMap<>();
        DataCore.loadLocation();
    }

    public static void clear(){
        Location.listLocation.clear();
    }

    public static org.bukkit.Location getLocation(final String name){
        return Location.listLocation.get(name);
    }
}
