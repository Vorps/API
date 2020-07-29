package net.vorps.api.particles;

import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class ThreadParticle extends Thread {

    private String particle;
    private ModelParticle model;
    private int time;

    private Location location;
    private Player[] player;

    private @Setter  boolean interupt;

    public ThreadParticle(String particle, ModelParticle model, int time){
        this.particle = particle;
        this.model = model;
        this.time = time;
    }

    @Override
    public void run() {
        double i = this.time;
        while (!interupt) {
            this.model.runEffect(this.location, this.particle, this.player);
            try {
                Thread.sleep(this.model.getSpeed());
            } catch (InterruptedException e) {
                //
            }
            i -= this.model.getSpeed()/1000.0;
            if(i <= 0) this.interupt = true;
        }
    }

    public void startParticle(Location location, Player... player){
        this.location = location;
        this.player = player;
        this.start();
    }

    public void stopParticle(){
        this.interupt = true;
    }
}