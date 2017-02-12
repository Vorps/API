package fr.herezia.api.chanel;

import java.util.List;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Project API Created by AtomixSoldier on 05/02/2017 at 19:16.
 */
public class MessageReceivedEvent extends Event{

	private static HandlerList handlers = new HandlerList();
	private final List<String> message;
	private final Player player;
	private final String subChannel;
	
	@Override
	public HandlerList getHandlers(){
		return MessageReceivedEvent.handlers;
	}  
	
	public static HandlerList getHandlerList(){
		return MessageReceivedEvent.handlers;
	}

	/**
	 * Called when a new message is received
	 * @param player Player
	 * @param message List<String>
	 */
	public MessageReceivedEvent(Player player, String subChannel, List<String> message)
	{
		this.message = message;
		this.player = player;
		this.subChannel = subChannel;
	}

	public List<String> getMessage() {
		return message;
	}

	public Player getPlayer() {
		return player;
	}

	public String getSubChannel() {
		return subChannel;
	}

}
