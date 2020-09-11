package net.vorps.api.objects;

import net.vorps.api.data.DataCore;
import org.bukkit.Location;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Project Hub Created by Vorps on 05/03/2016 at 03:57.
 */
public class Limite {

    public Limite(ResultSet result) throws SQLException {
        Limite.listLimite.put(result.getString(1), new double[] {result.getDouble( 2), result.getDouble(3), result.getDouble(4), result.getDouble(5), result.getDouble(6), result.getDouble(7)});
    }

    private static HashMap<String, double[]> listLimite;

    static {
        Limite.listLimite = new HashMap<>();
        DataCore.loadLimite();
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
