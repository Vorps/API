package fr.herezia.api.threads;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import lombok.Getter;

/**
 * Project FortycubeBungee Created by Vorps on 10/04/2016 at 18:42.
 */
public class ThreadServer extends ClassThread {

    private long time;
    private Player player;

    public ThreadServer(long time, Player player, String nameServer){
        super(nameServer);
        this.time = time;
        this.player = player;
        ThreadServer.serverStart.add(nameServer);
    }

    @Override
	public void run(){
        ThreadServer.serverStart.add(getNameServer());
        try {
            Thread.sleep(this.time);
        } catch (InterruptedException e){
            e.printStackTrace();
        }
        ThreadServer.serverStart.remove(getNameServer());
        interrupt();
    }

    private static @Getter ArrayList<String> serverStart;

    static {
        serverStart = new ArrayList<>();
    }
}
