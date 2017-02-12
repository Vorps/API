package fr.herezia.api.chanel;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;

import fr.herezia.api.API;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

/**
 * Project API Created by AtomixSoldier on 05/02/2017 at 19:16.
 */
public class ChanelManagers {


    static {
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(API.getInstance(), "BungeeCord");
        Bukkit.getServer().getMessenger().registerOutgoingPluginChannel(API.getInstance(), "BungeeMessaging");
        Bukkit.getServer().getMessenger().registerIncomingPluginChannel(API.getInstance(), "BungeeMessaging", new PluginListener());
    }
    /**
     * Connect a player to a server
     * @param player The player
     * @param serverName The server name
     */
    public static void connectPlayer(Player player, String serverName)
    {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("Connect");
        out.writeUTF(serverName);
        player.sendPluginMessage(API.getInstance(), "BungeeCord", out.toByteArray());
    }

    /**
     * Send a player to a server with a message that would be received when the player get connected
     * @param player The player you want to connect
     * @param serverName The server
     * @param subChannel Your subchannel name as String
     * @param message The message as a list of String you want to send
     */
    public static void connectPlayer(Player player, String serverName, String subChannel, List<String> message)
    {
        message.add("delayed");
        ChanelManagers.sendMessage(player, serverName, subChannel, message);
        ChanelManagers.connectPlayer(player, serverName);
    }

    /**
     * Send a plugin message to an other server
     * @param player The player you wish to send the message (if you don't care : Player player = Iterables.getFirst(Bukkit.getOnlinePlayers(), null); )
     * @param serverName The server name as String
     * @param subChannel Your subchannel name as String
     * @param message You message as List of String
     */
    public static void sendMessage(Player player, String serverName, String subChannel, List<String> message)
    {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(serverName);
        out.writeUTF(player.getName());
        out.writeUTF(subChannel);
        out.writeInt(message.size());
        for(String s : message){
            out.writeUTF(s);
        }
    player.sendPluginMessage(API.getInstance(), "BungeeMessaging", out.toByteArray());
    }
}
