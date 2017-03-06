package fr.herezia.api.bungee;

import fr.herezia.api.channel.Action;
import fr.herezia.api.channel.ChanelManager;
import org.bukkit.entity.Player;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

/**
 * Project API Created by Vorps on 05/03/2017 at 16:47.
 */
public class Bungee {

    public static void command(){
        ChanelManager.Channel channel = ChanelManager.Channel.BUNGEE;

        channel.addAction(new Action() {

            @Override
            public void receive(Player player, byte[] message) {
                DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
                boolean state;
                try {
                    in.readUTF();
                    state = in.readUTF().equals("true");
                    player.setAllowFlight(state);
                    player.setFlying(state);
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public String getName() {
                return "FLY";
            }

        });

        channel.addAction(new Action() {

            @Override
            public void receive(Player player, byte[] message) {
                DataInputStream in = new DataInputStream(new ByteArrayInputStream(message));
                try {
                    in.readUTF();
                } catch (IOException e){
                    e.printStackTrace();
                }
            }

            @Override
            public String getName() {
                return "INVSEE";
            }

        });
    }
}
