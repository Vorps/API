package fr.herezia.api.threads;

import org.bukkit.entity.Player;

import fr.herezia.api.chanel.ChanelManagers;

/**
 * Project FortycubeBungee Created by Vorps on 13/04/2016 at 00:34.
 */
public class ThreadFile extends ClassThread {

    private long time;
    private Player player;

    public ThreadFile(String nameServer, long time, Player player){
        super(nameServer);
        this.time = time;
        this.player = player;
    }
    @Override
	public void run(){
        try {
            Thread.sleep(time*1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        ChanelManagers.connectPlayer(this.player, super.nameServer);
        interrupt();
    }
}
