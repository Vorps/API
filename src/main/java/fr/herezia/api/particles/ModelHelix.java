package fr.herezia.api.particles;

import net.minecraft.server.v1_9_R2.EnumParticle;
import net.minecraft.server.v1_9_R2.PacketPlayOutWorldParticles;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class ModelHelix extends ModelParticle{

    private static final int SPEED = 1000;

    private static final double RADIUS = 1;

    public ModelHelix(){
        super.setSpeed(ModelHelix.SPEED);
    }

    @Override
    public void runEffect(Location location, String particle, Player... player){
        for(double y = 0; y < 2*Math.PI; y+=0.1){
            double x = RADIUS*Math.cos(y*4);
            double z = RADIUS*Math.sin(y*4);
            PacketPlayOutWorldParticles packetPlayOutWorldParticles = new PacketPlayOutWorldParticles(EnumParticle.valueOf(particle), true, (float) (location.getX()+x), (float) (location.getY()+y), (float) (location.getZ()+z), 0, 0, 0, 0, 1);
            for(Player craftPlayer : player) ((CraftPlayer)craftPlayer).getHandle().playerConnection.sendPacket(packetPlayOutWorldParticles);
        }
    }
}