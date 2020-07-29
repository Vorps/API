package net.vorps.api.nms;

import java.util.ArrayList;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.ArmorStand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;

import lombok.Setter;

/**
 * Project SnoWar Created by Vorps on 03/08/2016 at 20:19.
 */

public class Hologram implements Listener{

    private String player;
    private ArrayList<ArmorStand> armorStand;
    private HashMap<String, String> message;
    private @Setter double space;
    private @Setter Location location;
    private @Setter Location locationTmp;
    private @Setter long time;
    private @Setter double pos;

    @EventHandler
    public void onPlayerMove(final PlayerMoveEvent e){
        if(player != null && this.player.equals(e.getPlayer().getName())){
            Location location = e.getPlayer().getLocation().clone().add(0, this.pos, 0);
            for(ArmorStand armorStand : this.armorStand){
                armorStand.teleport(location.add(0, -this.space, 0));
            }
        }
    }

    public Hologram follow(final String player, Plugin plugin){
        this.player = player;
        Bukkit.getServer().getPluginManager().registerEvents(this, plugin);
        return this;
    }

    /**
     * Object Hologram
     * @param space double
     * @param message Message[]
     */
    public Hologram(final double space, final Message... message){
        this.message = new HashMap<>();
        this.armorStand = new ArrayList<>();
        this.addMessage(message);
        this.space = space;
        this.time = 0;
        Hologram.listHologram.add(this);
    }

    /**
     * Show Hologram
     * @param location Location
     */
    public Hologram show(Location location, double pos){
        this.pos = pos;
        this.location = location.clone().add(0, pos, 0);
        this.locationTmp = location.clone().add(0, pos, 0);
        this.message.values().forEach((String message) -> this.showLine(message));
        return this;
    }

    /**
     * Show Hologram Timer
     * @param location Location
     * @param time int
     * @param plugin Plugin
     */
    public Hologram show(Location location, double pos, long time, Plugin plugin){
        this.show(location, pos);
        this.time = time;
        this.timer(plugin);
        return this;
    }

    private void timer(Plugin plugin){
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
            @Override
            public void run() {
                remove();
            }
        }, (this.time/1000)*20);
    }

    /**
     * Add Message
     * @param message Message[]
     */
    public Hologram addMessage(Message... message){
        for(Message messageList : message){
            this.message.put(messageList.key, messageList.value);
        }
        return this;
    }

    /**
     * Remove Message
     * @param key String[]
     */
    public Hologram removeMessage(String... key){
        for(String keyList : key){
            this.message.remove(keyList);
        }
        return this;
    }

    /**
     * Update Hologram
     * @param location Location
     */
    public Hologram update(Location location, double pos){
        this.remove();
        this.show(location, pos);
        return this;
    }

    /**
     * Update Hologram
     */
    public Hologram update(){
        this.remove();
        this.show(this.location, this.pos);
        return this;
    }

    /**
     * Remove all Message
     */
    public Hologram remove(){
        this.armorStand.forEach((ArmorStand armorStand) -> armorStand.remove());
        return this;
    }


    private void showLine(String message){
        ArmorStand armorStand = Bukkit.getServer().getWorlds().get(0).spawn(this.locationTmp.add(0, -this.space, 0), ArmorStand.class);
        armorStand.setVisible(false);
        armorStand.setCustomName(message);
        armorStand.setCustomNameVisible(true);
        armorStand.setGravity(false);
        this.armorStand.add(armorStand);
    }

    private static ArrayList<Hologram> listHologram;

    public static void removeAll(){
        Hologram.listHologram.forEach((Hologram hologram) -> hologram.remove());
    }

    static {
        Hologram.listHologram = new ArrayList<>();
    }

    /**
     * Factory message
     */
    public static class Message{

        private String key;
        private String value;

        public Message(String key, String value){
            this.key = key;
            this.value = value;
        }
    }
}
