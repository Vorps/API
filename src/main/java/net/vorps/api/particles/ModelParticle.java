package net.vorps.api.particles;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public abstract class ModelParticle {

    private @Getter @Setter int speed = 0;

    abstract public void runEffect(Location location, String particle, Player... player);

}