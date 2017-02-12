package fr.herezia.api.nms;

import org.bukkit.entity.Player;

import net.minecraft.server.v1_9_R2.IChatBaseComponent;
import net.minecraft.server.v1_9_R2.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_9_R2.PlayerConnection;

import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;

import java.lang.reflect.Field;

/**
 * Project API Created by Vorps on 04/02/2017 at 13:36.
 */
public class TablistBuilder {
    private String header;
    private String footer;

    public TablistBuilder(String header, String footer){
        this.header = header;
        this.footer = footer;
    }

    public TablistBuilder withHeader(String header) {
        this.header = header;
        return this;
    }

    public TablistBuilder withFooter(String footer) {
        this.footer = footer;
        return this;
    }

    public void sendTo(Player... playerList){
        for(Player player : playerList) this.packet(player, this.header, this.footer);
    }

    private void packet(Player player, String header, String footer) {
        PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
        PacketPlayOutPlayerListHeaderFooter packet = new PacketPlayOutPlayerListHeaderFooter();
        try {
            if (header != null) {
                Field hfield = packet.getClass().getDeclaredField("a");
                hfield.setAccessible(true);
                hfield.set(packet, buildChatComponent(header));
                hfield.setAccessible(false);
            }
            if (footer != null) {
                Field ffield = packet.getClass().getDeclaredField("b");
                ffield.setAccessible(true);
                ffield.set(packet, buildChatComponent(footer));
                ffield.setAccessible(false);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connection.sendPacket(packet);
    }

    private IChatBaseComponent buildChatComponent(String msg) {
        return IChatBaseComponent.ChatSerializer.a("{'text':'" + msg + "'}");
    }

}
