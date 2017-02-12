package fr.herezia.api.objects;

import org.bukkit.Location;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;
import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Project Hub Created by Vorps on 05/03/2016 at 03:57.
 */
public class Limite {

    public Limite(ResultSet result) throws SqlException {
        Limite.listLimite.put(Database.RUSH_VOLCANO.getDatabase().getString(result, 1), new double[] {Database.RUSH_VOLCANO.getDatabase().getDouble(result, 2), Database.RUSH_VOLCANO.getDatabase().getDouble(result, 3), Database.RUSH_VOLCANO.getDatabase().getDouble(result, 4), Database.RUSH_VOLCANO.getDatabase().getDouble(result, 5), Database.RUSH_VOLCANO.getDatabase().getDouble(result, 6), Database.RUSH_VOLCANO.getDatabase().getDouble(result, 7)});
    }

    private static HashMap<String, double[]> listLimite;

    static {
        Limite.listLimite = new HashMap<>();
    }

    public static double[] getLimite(String nameLimite){
        return Limite.listLimite.get(nameLimite);
    }

    public static boolean limite(Location loc, double[] limite){
        return loc.getX() > limite[0] || loc.getX() < limite[1] || loc.getY() > limite[2] || loc.getY() < limite[3] || loc.getZ() > limite[4] || loc.getZ() < limite[5];
    }

    public static void clear(){
        Limite.listLimite.clear();
    }
}
