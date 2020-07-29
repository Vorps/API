package net.vorps.api.particles;

import net.vorps.api.Exceptions.SqlException;
import net.vorps.api.data.Data;
import net.vorps.api.data.DataCore;
import net.vorps.api.databases.Database;
import lombok.Getter;
import net.vorps.api.databases.DatabaseManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class Particle {

    private String particle;
    private ModelParticle model;
    private int time;
    private ThreadParticle thread;

    public Particle(ResultSet result) throws SQLException {
        this.particle = result.getString(2).toUpperCase();
        try {
            java.lang.reflect.Constructor constructor = Class.forName("net.vorps.api.particles."+result.getString(3)).getConstructor();
            this.model = (ModelParticle) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.time = result.getInt(4);
        Particle.listParticle.put(result.getString(1), this);
    }

    public void startParticle(Location location, Player... player){
        this.thread = new ThreadParticle(this.particle, this.model, this.time);
        this.thread.startParticle(location, player);
    }

    public void stopParticle(){
        this.thread.setInterupt(true);
    }


    private static @Getter HashMap<String, Particle> listParticle;

    static {
        Particle.listParticle = new HashMap<>();
        DataCore.getInstance().loadParticle();
    }

    public static Particle getParticle(String name){
        return listParticle.get(name);
    }

    public static void clear(){
        Particle.listParticle.clear();
    }
}