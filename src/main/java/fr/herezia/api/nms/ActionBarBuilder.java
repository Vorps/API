package fr.herezia.api.nms;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.entity.Player;

import fr.herezia.api.API;
import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.PacketPlayOutChat;
import net.minecraft.server.v1_9_R2.PlayerConnection;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class ActionBarBuilder {
    private String message;
    private int stay;

    public ActionBarBuilder(String message) {
        this.message = message;
    }

    public ActionBarBuilder withStay(int stay) {
        this.stay = stay;
        return this;
    }

    public ActionBarBuilder withMessage(String message) {
        this.message = message;
        return this;
    }

    private IChatBaseComponent buildChatComponent(String msg) {
        return IChatBaseComponent.ChatSerializer.a("{'text':'" + msg + "'}");
    }

    public void sendTo(Player... playerList) {
        for(Player player : playerList){
            PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
            PacketPlayOutChat packet = new PacketPlayOutChat(buildChatComponent(this.message), (byte) 2);
            connection.sendPacket(packet);
            if(this.stay != 0) {
                Bukkit.getScheduler().runTaskLater(API.getInstance(), () -> {
                    PacketPlayOutChat clear = new PacketPlayOutChat(buildChatComponent(""), (byte) 2);
                    connection.sendPacket(clear);
                }, this.stay * 20L);
            }
        }
    }
}
