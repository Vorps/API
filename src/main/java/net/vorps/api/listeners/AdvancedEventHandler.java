package net.vorps.api.listeners;

import org.bukkit.event.Event;

/**
 * 
 * @author Atomix
 *
 * @param <T> Class extending event
 */
public interface AdvancedEventHandler<T extends Event> {

	/**
	 * Fired when the event was called on a listened item
	 * @param event
	 */
    void onEvent(T event);
	Class<T> getEventClass();
}
