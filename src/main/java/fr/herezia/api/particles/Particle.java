package fr.herezia.api.particles;

import fr.herezia.api.Exceptions.SqlException;
import fr.herezia.api.databases.Database;
import lombok.Getter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.util.HashMap;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class Particle {

    private String name;
    private String particle;
    private ModelParticle model;
    private int time;
    private ThreadParticle thread;

    public Particle(ResultSet resultSet) throws SqlException {
        this.name = Database.RUSH_VOLCANO.getDatabase().getString(resultSet, 1);
        this.particle = Database.RUSH_VOLCANO.getDatabase().getString(resultSet, 2).toUpperCase();
        try {
            java.lang.reflect.Constructor constructor = Class.forName("net.vorps.api.particles."+Database.RUSH_VOLCANO.getDatabase().getString(resultSet, 3)).getConstructor();
            this.model = (ModelParticle) constructor.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.time = Database.RUSH_VOLCANO.getDatabase().getInt(resultSet, 4);
        Particle.listParticle.put(this.name, this);
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

    }

    public static Particle getParticle(String name){
        return listParticle.get(name);
    }

    public static void clear(){
        Particle.listParticle.clear();
    }
}