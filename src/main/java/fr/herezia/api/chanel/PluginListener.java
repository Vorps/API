package fr.herezia.api.chanel;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import fr.herezia.api.chanel.MessageReceivedEvent;

/**
 * Project API Created by AtomixSoldier on 05/02/2017 at 19:16.
 */
public class PluginListener implements PluginMessageListener{

	@Override
	public void onPluginMessageReceived(String channel, Player player, byte[] message) {
		if(!channel.equals("BungeeMessaging"))
			return;
		
		ByteArrayDataInput in = ByteStreams.newDataInput(message);
		String subChannel = in.readUTF();
		int nbMessage = in.readInt();
		List<String> messageList = new ArrayList<String>();
		for(int i=0; i<nbMessage;i++)
		{
			messageList.add(in.readUTF());
		}
		if(messageList.contains("delayed"))
			messageList.remove("delayed");
		
		Bukkit.getServer().getPluginManager().callEvent(new MessageReceivedEvent(player,subChannel, messageList));
	}

}
