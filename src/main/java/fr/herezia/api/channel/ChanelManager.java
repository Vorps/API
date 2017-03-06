package fr.herezia.api.channel;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import fr.herezia.api.API;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.util.HashMap;

/**
 * Project Bungee Created by Vorps on 28/02/2017 at 19:28.
 */
public class ChanelManager implements PluginMessageListener {

    public enum Channel{
        BUNGEE;

        Channel(){
            this.actionList = new HashMap<>();
            API.getInstance().getServer().getMessenger().registerOutgoingPluginChannel(API.getInstance(), this.name());
            API.getInstance().getServer().getMessenger().registerIncomingPluginChannel(API.getInstance(), this.name(), ChanelManager.getIntance());
        }
        private HashMap<String, Action> actionList;

        public boolean isAction(String name){
            return this.actionList.containsKey(name);
        }

        public Action getAction(String name){
            return this.actionList.get(name);
        }

        public void removeAction(String name){
            if(this.isAction(name)) this.actionList.remove(name);
        }

        public void addAction(Action action){
            if(!this.isAction(action.getName())) this.actionList.put(action.getName(), action);
        }

        public static boolean isChannel(String label){
            return Channel.valueOf(label) != null;
        }
    }

    @Override
    public synchronized void onPluginMessageReceived(String channel, Player player, byte[] message) {
        String action = ByteStreams.newDataInput(message).readUTF();
        if(Channel.isChannel(channel) && Channel.valueOf(channel).isAction(action)) Channel.valueOf(channel).getAction(action).receive(player, message);
    }

    public static void send(Channel channel, Action action, Player player, String... message){
        if(channel.isAction(action.getName())) channel.actionList.put(action.getName(), action);
        ByteArrayDataOutput output = ByteStreams.newDataOutput();
        output.writeUTF(action.getName());
        for(String s : message) output.writeUTF(s);
        player.sendPluginMessage(API.getInstance(), channel.name(), output.toByteArray());
    }

    private static ChanelManager instance;

    private static ChanelManager getIntance(){
        if(ChanelManager.instance == null){
            ChanelManager.instance = new ChanelManager();
        }
        return ChanelManager.instance;
    }
}
