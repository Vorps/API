package net.vorps.api.channel;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class ChannelManager extends ChannelBuilder<JavaPlugin> {
    @Override
    public void send(JavaPlugin plugin, UUID uuid) {
        if(plugin != null){
            Player player = plugin.getServer().getPlayer(uuid);
            if(player != null){
                player.sendPluginMessage(plugin, this.channel, this.build());
            } else System.out.println("Error player == null for send message");
        } else System.out.println("Error plugin == null for send message");
    }

}
